package com.archide.hsb.service;

import java.time.LocalDateTime;
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
import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.jsonmodel.PlaceOrdersJson.MenuItems;
import com.archide.hsb.model.MenuEntity;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrders;
import com.archide.hsb.model.TableList;


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
				
				List<MenuItems> menuItemsList = placeOrdersJson.getMenuItems();
				for(MenuItems menuItems : menuItemsList){
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
				return serviceUtil.getRestResponse(true, "success",200);
			}
			return serviceUtil.getRestResponse(true, "Invalid data",500);
		}catch(Exception e){
			logger.error("Error in placeAnOrder", e);
		}
		return null;
	}
	
	private String getOrderId(String tableNumber) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("FOD-");
		LocalDateTime dateTime = LocalDateTime.now();
		stringBuilder.append(dateTime.getDayOfMonth());
		stringBuilder.append(dateTime.getMonthValue());
		stringBuilder.append(dateTime.getHour());
		stringBuilder.append(dateTime.getMinute());
		stringBuilder.append(tableNumber);
		return stringBuilder.toString();
	}

}
