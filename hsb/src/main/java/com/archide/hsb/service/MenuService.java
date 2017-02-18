package com.archide.hsb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.archide.hsb.dao.MenuListDao;
import com.archide.hsb.dao.OrdersDao;
import com.archide.hsb.dao.TableListDao;
import com.archide.hsb.enumeration.UserType;
import com.archide.hsb.jsonmodel.FoodCategoryJson;
import com.archide.hsb.jsonmodel.GetMenuDetails;
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
import com.archide.hsb.model.TableList;
import com.google.gson.Gson;

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
	
	
	
	/**
	 * Get Table List
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getMenuDetails(String lastServerSyncTimeTemp,String tableNumber,String mobileNumber,String userType){
		try{
			if(userType != null){
				LoginUsersEntity loginUsersEntity = menuListDao.getLoginUsers(mobileNumber);
				if(loginUsersEntity != null && 
						loginUsersEntity.getUserType().toString().equals(UserType.INDIVIDUAL.toString()) &&
						!loginUsersEntity.getTableNumber().equals(tableNumber)){
					return serviceUtil.getRestResponse(true, loginUsersEntity.getTableNumber(),404);
				}
				else if(loginUsersEntity == null){
				    loginUsersEntity = new LoginUsersEntity();
					loginUsersEntity.setMobileNumber(mobileNumber);
					loginUsersEntity.setTableNumber(tableNumber);
					loginUsersEntity.setUserType(UserType.valueOf(userType));
					menuListDao.createLoginUsers(loginUsersEntity);
				}
			}
			
			GetMenuDetails getMenuDetails = new GetMenuDetails();
			long lastServerSyncTime = 0;
			if(lastServerSyncTimeTemp != null){
				lastServerSyncTime = Long.valueOf(lastServerSyncTimeTemp);
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
			TableList tableList =	tableListDao.getTables(tableNumber);
			PlacedOrdersEntity placedOrders = ordersDao.getPlacedOrders(tableList,mobileNumber);
			if(placedOrders != null && !placedOrders.isClosed()){
				PlaceOrdersJson placeOrdersJson = new PlaceOrdersJson(placedOrders);
				List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrders);
				for(PlacedOrderItems orderItems : placedOrderItemsList){
					OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
					placeOrdersJson.getMenuItems().add(orderedMenuItems);
				}
				getMenuDetails.setPreviousOrder(placeOrdersJson);
			}
			String data = gson.toJson(getMenuDetails);
			return serviceUtil.getRestResponse(true, data);
		}catch(Exception e){
			logger.error("Error in getMenuDetails, Params["+lastServerSyncTimeTemp+","+tableNumber+","+mobileNumber+"]",e);
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
