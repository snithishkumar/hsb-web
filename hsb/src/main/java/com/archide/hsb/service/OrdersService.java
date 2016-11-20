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
import com.archide.hsb.jsonmodel.HistoryMenuItem;
import com.archide.hsb.jsonmodel.OrderedMenuItems;
import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.jsonmodel.PurchaseDetailsJson;
import com.archide.hsb.jsonmodel.PurchaseJson;
import com.archide.hsb.jsonmodel.ResponseData;
import com.archide.hsb.model.History;
import com.archide.hsb.model.MenuEntity;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrders;
import com.archide.hsb.model.TableList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


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
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> placeAnOrder(String requestData){
		try{
			PlaceOrdersJson placeOrdersJson = serviceUtil.fromJson(requestData, PlaceOrdersJson.class);
			if(placeOrdersJson != null){
				TableList tableList =	tableListDao.getTables(placeOrdersJson.getTableNumber());
				if(tableList == null){
					// return Invalid TableNumber
					return serviceUtil.getRestResponse(true, "Invalid table number.",404);
				}
				PlacedOrders placedOrders = ordersDao.getPlacedOrders(placeOrdersJson.getPlaceOrderUuid());
				if(placedOrders == null){
					 placedOrders = new PlacedOrders(placeOrdersJson);
				}
				
				ordersDao.placeAnOrders(placedOrders);
				
				List<OrderedMenuItems> menuItemsList = placeOrdersJson.getMenuItems();
				for(OrderedMenuItems menuItems : menuItemsList){
					MenuEntity menuEntity = menuListDao.getMenuEntity(menuItems.getMenuUuid());
					if(menuEntity != null){
						PlacedOrderItems placedOrderItems = ordersDao.getPlacedOrderItems(menuItems.getMenuUuid());
						if(placedOrderItems == null){
							placedOrderItems = new PlacedOrderItems();
							placedOrderItems.setQuantity(menuItems.getQuantity());
							placedOrderItems.setMenuItem(menuEntity);
							placedOrderItems.setPlacedOrderItemsUUID(ServiceUtil.uuid());
							placedOrderItems.setPlacedOrders(placedOrders);
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
			PlacedOrders placedOrders = ordersDao.getPlacedOrders(tableList);
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
	
	

}
