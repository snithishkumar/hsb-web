package com.archide.hsb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.archide.hsb.dao.MenuListDao;
import com.archide.hsb.dao.OrdersDao;
import com.archide.hsb.dao.TableListDao;
import com.archide.hsb.jsonmodel.AmountDetailsJson;
import com.archide.hsb.jsonmodel.GetKitchenOrders;
import com.archide.hsb.jsonmodel.HistoryMenuItem;
import com.archide.hsb.jsonmodel.KitchenOrderListResponse;
import com.archide.hsb.jsonmodel.OrderedMenuItems;
import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.jsonmodel.PurchaseDetailsJson;
import com.archide.hsb.jsonmodel.PurchaseJson;
import com.archide.hsb.jsonmodel.ResponseData;
import com.archide.hsb.model.History;
import com.archide.hsb.model.MenuEntity;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.TableList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


@Service
public class OrdersService {
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private TableListDao tableListDao;
	
	@Autowired
	private MenuListDao menuListDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Gson gson;
	
	private static final Logger logger = Logger.getLogger(OrdersService.class);
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> placeAnOrder(String requestData){
		try{
			PlaceOrdersJson placeOrdersJson = serviceUtil.fromJson(requestData, PlaceOrdersJson.class);
			if(placeOrdersJson != null){
				TableList tableList =	tableListDao.getTables(placeOrdersJson.getTableNumber());
				if(tableList == null){
					// return Invalid TableNumber
					return serviceUtil.getRestResponse(true, "Invalid table number.",404);
				}
				PlacedOrdersEntity placedOrders = ordersDao.getPlacedOrders(placeOrdersJson.getPlaceOrderUuid());
				if(placedOrders == null){
					 placedOrders = new PlacedOrdersEntity(placeOrdersJson);
					 placedOrders.setTableNumber(tableList);
					 ordersDao.placeAnOrders(placedOrders);
				}else{
					placedOrders.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					placedOrders.setLastUpdatedDateTime(placedOrders.getServerDateTime());
				}
				
				
				
				List<OrderedMenuItems> menuItemsList = placeOrdersJson.getMenuItems();
				for(OrderedMenuItems menuItems : menuItemsList){
					MenuEntity menuEntity = menuListDao.getMenuEntity(menuItems.getMenuUuid());
					if(menuEntity != null){
						PlacedOrderItems placedOrderItems = ordersDao.getPlacedOrderItems(menuItems.getMenuUuid());
						if(placedOrderItems == null){
							placedOrderItems = new PlacedOrderItems();
							placedOrderItems.setQuantity(menuItems.getQuantity());
							placedOrderItems.setMenuItem(menuEntity);
							placedOrderItems.setName(menuItems.getName());
							placedOrderItems.setItemCode(menuItems.getItemCode());
							placedOrderItems.setPlacedOrderItemsUUID(ServiceUtil.uuid());
							placedOrderItems.setPlacedOrders(placedOrders);
							placedOrderItems.setLastUpdatedTime(placedOrders.getServerDateTime());
							placedOrderItems.setOrderDateTime(placedOrders.getServerDateTime());
							placedOrderItems.setServerSyncTime(placedOrders.getServerDateTime());
							ordersDao.placeOrdersItems(placedOrderItems);
						}
						
					}
				}
				
				return serviceUtil.getRestResponse(true,placedOrders.getServerDateTime(),200);
			}
			return serviceUtil.getRestResponse(true, "Invalid data",500);
		}catch(Exception e){
			logger.error("Error in placeAnOrder", e);
		}
		return null;
	}
	
	public ResponseEntity<String> billing(String tableNumber){
		try{
			TableList tableList = tableListDao.getTables(tableNumber);
			if(tableList == null){
				//return 
			}
			PlacedOrdersEntity placedOrders = ordersDao.getPlacedOrders(tableList);
			List<PlacedOrderItems> orderItems  = ordersDao.getPlacedOrderItems(placedOrders);
			PurchaseJson purchaseJson = new PurchaseJson(placedOrders);
			AmountDetailsJson amountDetailsJson = new AmountDetailsJson(placedOrders);
			List<HistoryMenuItem> historyMenuItems = new ArrayList<>();
			for(PlacedOrderItems placedOrderItems : orderItems){
				PurchaseDetailsJson purchaseDetailsJson = new PurchaseDetailsJson(placedOrderItems);
				HistoryMenuItem historyMenuItem = new HistoryMenuItem(placedOrderItems);
				historyMenuItems.add(historyMenuItem);
				purchaseJson.getPurchaseDetails().add(purchaseDetailsJson);
			}
			purchaseJson.setAmountDetails(amountDetailsJson);
			// itemcode,name,quantity,desc,menu course,food category,price
			History history = new History(placedOrders);
			history.setHistoryUUID(ServiceUtil.uuid());
			history.setItems(gson.toJson(historyMenuItems));
			ordersDao.saveHistory(history);
			String responseDataJson = restTemplate.postForObject("", placedOrders, String.class);
			ResponseData responseData = gson.fromJson(responseDataJson, ResponseData.class);
			return serviceUtil.getRestResponse(true,responseData.getData(),responseData.getStatusCode());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getRestResponse(true, "Internal Server Error.",500);
	}
	
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getKitchenOrderDetails(String requestData){
		try {
			List<GetKitchenOrders> getKitchenOrderList = gson.fromJson(requestData,
					new TypeToken<List<GetKitchenOrders>>() {}.getType());
			List<String> orderIds = new ArrayList<>();
			KitchenOrderListResponse kitchenOrderListResponse = new KitchenOrderListResponse();
			//List<PlaceOrdersJson> placeOrdersJsons = new ArrayList<>();
			for (GetKitchenOrders getKitchenOrders : getKitchenOrderList) {
				orderIds.add(getKitchenOrders.getOrderId());
				PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(getKitchenOrders.getPlacedOrdersUuid());
				if (placedOrdersEntity != null) {
					processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), getKitchenOrders.getServerDateTime());
				} else {
					kitchenOrderListResponse.getClosedOrders().add(getKitchenOrders.getOrderId());
				}
			}

			int size = orderIds.size();
			if(size > 0){
				int quo = size / 25;
				int rem = size % 25;
				if (quo > 0) {
					for (int i = 0; i < quo; i++) {
						List<String> orderIdsTemp = orderIds.subList(i * 25, (i + 1) * 25);
						List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPlacedOrders(orderIdsTemp);
						for (PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities) {
							processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), 0);
						}
					}
				}

				if (rem > 0) {
					List<String> orderIdsTemp = orderIds.subList(quo * 25, size);
					List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPlacedOrders(orderIdsTemp);
					for (PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities) {
						processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), 0);
					}
				}
			}else{
				List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPlacedOrders();
				for (PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities) {
					processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), 0);
				}
			}
			
			String data = gson.toJson(kitchenOrderListResponse);
			return serviceUtil.getRestResponse(true, data);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	private void processKitchenData(PlacedOrdersEntity placedOrdersEntity,List<PlaceOrdersJson> placeOrdersJsons,long lastServerTime){
		 PlaceOrdersJson placeOrdersJson = new PlaceOrdersJson(placedOrdersEntity, true);
			placeOrdersJsons.add(placeOrdersJson);
			List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrdersEntity, lastServerTime);
			for(PlacedOrderItems placedOrderItems : placedOrderItemsList){
				OrderedMenuItems orderedMenuItems = new OrderedMenuItems(placedOrderItems,true);
				placeOrdersJson.getMenuItems().add(orderedMenuItems);
			}
	}
	
	

}
