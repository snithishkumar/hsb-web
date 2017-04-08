package com.archide.hsb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.archide.hsb.dao.MenuListDao;
import com.archide.hsb.dao.OrdersDao;
import com.archide.hsb.dao.TableListDao;
import com.archide.hsb.enumeration.AppType;
import com.archide.hsb.enumeration.OrderType;
import com.archide.hsb.enumeration.Status;
import com.archide.hsb.enumeration.UserType;
import com.archide.hsb.jsonmodel.FoodCategoryJson;
import com.archide.hsb.jsonmodel.GetMenuDetails;
import com.archide.hsb.jsonmodel.KitchenMenuItems;
import com.archide.hsb.jsonmodel.MenuItemJson;
import com.archide.hsb.jsonmodel.MenuListJson;
import com.archide.hsb.jsonmodel.OrderedMenuItems;
import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.LoginUsersEntity;
import com.archide.hsb.model.MenuCourse;
import com.archide.hsb.model.MenuEntity;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.ReservedTableEntity;
import com.archide.hsb.model.TableList;
import com.archide.mobilepay.exception.ValidationException;
import com.archide.mobilepay.json.GetMenuRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class MenuService {
	
	private static final Logger logger = Logger.getLogger(MenuService.class);
	
	@Autowired
	private MenuListDao menuListDao;
	@Autowired
	private TableListDao tableListDao;
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private Gson gson;
	
	private ReservedTableEntity reserveTableNumber(String tableNumber,String mobileNumber,AppType appType)throws ValidationException{
		try{
			List<String> availableTableNumbers = tableListDao.getAvailableTableNumbers();
			if(availableTableNumbers.size() > 0){
				ReservedTableEntity reservedTableEntity = new ReservedTableEntity();
				reservedTableEntity.setCreatedTime(ServiceUtil.getCurrentGmtTime());
				reservedTableEntity.setMobileNumber(mobileNumber);
				reservedTableEntity.setTableNumber(availableTableNumbers.get(0));
				reservedTableEntity.setAppType(appType);
				tableListDao.createReservedTableEntity(reservedTableEntity);
				return reservedTableEntity;
			}else{
				throw new ValidationException(404, "No more tables");
				// Not Available
			}
		}catch(ConstraintViolationException e){
			reserveTableNumber(tableNumber, mobileNumber, appType);
		}
		return null;
	}
	
	/**
	 * Get Table List
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getMenuDetails(String requestData){
		try{
			GetMenuRequest getMenuRequest = gson.fromJson(requestData, GetMenuRequest.class);
			ReservedTableEntity reservedTableEntity = null;
			if(getMenuRequest.getOrderType().toString().equals(OrderType.Dinning.toString())){
				if(getMenuRequest.getAppType().toString().equals(AppType.Captain.toString())){
					boolean isReserved = tableListDao.isReserved(getMenuRequest.getTableNumber());
					if(!isReserved){
						reservedTableEntity = reserveTableNumber(getMenuRequest.getTableNumber(), getMenuRequest.getMobileNumber(), getMenuRequest.getAppType());
					}else{
						reservedTableEntity = tableListDao.getReservedTable(getMenuRequest.getTableNumber());
					}
				}else{
					reservedTableEntity = reserveTableNumber(getMenuRequest.getTableNumber(), getMenuRequest.getMobileNumber(), getMenuRequest.getAppType());
				}
			}
			
			GetMenuDetails getMenuDetails = new GetMenuDetails();
			long lastServerSyncTime = 0;
			if(getMenuRequest.getLastServerSyncTime() > 0 ){
				lastServerSyncTime = Long.valueOf(getMenuRequest.getLastServerSyncTime());
			}
			
			if(reservedTableEntity != null){
				getMenuDetails.setTableNumber(reservedTableEntity.getTableNumber());
				getMenuDetails.setMobileNumber(reservedTableEntity.getMobileNumber());
			}
			List<MenuCourse> menuCourses = menuListDao.getMenuCourse();
			List<MenuListJson> menuListJsonList = new ArrayList<MenuListJson>();
			for(MenuCourse menuCourse : menuCourses){
				MenuListJson menuListJson = new MenuListJson(menuCourse);
				menuListJsonList.add(menuListJson);
				List<FoodCategory> foodCategories = menuListDao.getFoodCategory(menuCourse);
				for(FoodCategory foodCategory : foodCategories){
					FoodCategoryJson categoryJson = new FoodCategoryJson(foodCategory);
					menuListJson.getCategoryJsons().add(categoryJson);
					List<MenuEntity> menuEntities = menuListDao.getMenuEntity(menuCourse, foodCategory,lastServerSyncTime);
					for(MenuEntity menuEntity : menuEntities){
						MenuItemJson menuItemJson = new MenuItemJson(menuEntity);
						categoryJson.getMenuItems().add(menuItemJson);
					}
				}
			}
			getMenuDetails.setMenuListJsonList(menuListJsonList);
			if(getMenuRequest.getAppType().toString().equals(AppType.Captain.toString())){
				TableList tableList =	tableListDao.getTables(getMenuRequest.getTableNumber());
				PlacedOrdersEntity placedOrders = ordersDao.getPlacedOrders(tableList,getMenuRequest.getMobileNumber());
				if(placedOrders != null && !placedOrders.isClosed()){
					PlaceOrdersJson placeOrdersJson = new PlaceOrdersJson(placedOrders);
					List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrders);
					for(PlacedOrderItems orderItems : placedOrderItemsList){
						OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
						placeOrdersJson.getMenuItems().add(orderedMenuItems);
					}
					getMenuDetails.setPreviousOrder(placeOrdersJson);
				}
			}
			
			String data = gson.toJson(getMenuDetails);
			return serviceUtil.getRestResponse(true, data, 200);
		}catch(ValidationException e){
			return serviceUtil.getRestResponse(true, e.getMessage(), e.getCode());
			
		}catch(Exception e){
			logger.error("Error in getMenuDetails, Params["+requestData+"]",e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> processKitchenMenuUpdates(String requestData){
		try{
			 List<KitchenMenuItems> menuItemList = gson.fromJson(requestData,
                     new TypeToken<List<KitchenMenuItems>>() {}.getType());
			 for(KitchenMenuItems kitchenMenuItems : menuItemList){
				 MenuEntity menuEntity = menuListDao.getMenuEntity(kitchenMenuItems.getMenuUUID());
				 menuEntity.setMaxCount(kitchenMenuItems.getMaxCount());
				 menuEntity.setServerTime(ServiceUtil.getCurrentGmtTime());
				 if(menuEntity.getCurrentCount() >= menuEntity.getMaxCount()){
					 menuEntity.setStatus(Status.UN_AVAILABLE);
				 }else{
					 menuEntity.setStatus(Status.AVAILABLE);
				 }
				 menuListDao.udpateMenuEntity(menuEntity);
			 }
				return serviceUtil.getRestResponse(true, "Success");
		}catch(Exception e){
			logger.error("Error in processKitchenMenuUpdates", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getMenuDetails(){
		try{
			List<KitchenMenuItems> kitchenMenuItemList = new ArrayList<>();
			List<MenuCourse> menuCourses = menuListDao.getMenuCourse();
			for(MenuCourse menuCourse : menuCourses){
				List<FoodCategory> foodCategories = menuListDao.getFoodCategory(menuCourse);
				for(FoodCategory foodCategory : foodCategories){
					List<MenuEntity> menuEntities = menuListDao.getMenuEntity(menuCourse, foodCategory,0);
					for(MenuEntity menuEntity : menuEntities){
						KitchenMenuItems kitchenMenuItems = new KitchenMenuItems(menuEntity);
						kitchenMenuItems.setFoodCategory(foodCategory.getCategoryName());
						kitchenMenuItems.setMenuCourse(menuCourse.getCategoryName());
						kitchenMenuItemList.add(kitchenMenuItems);
					}
				}
			}
			String data = gson.toJson(kitchenMenuItemList);
			return serviceUtil.getRestResponse(true, data);
		}catch(Exception e){
			logger.error("Error in getMenuDetails", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getUnAvailableMenuItems(long serverDateTime){
		try{
			List datas = menuListDao.getUnAvailableMenus(serverDateTime);
			if(datas.size() > 0){
				String data = gson.toJson(datas);
				return serviceUtil.getRestResponse(true, data,200);
			}else{
				return serviceUtil.getRestResponse(true, "",404);
			}
		}catch(Exception e){
			logger.error("Error in getUnAvailableMenuItems, Params["+serverDateTime+"]",e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}

}
