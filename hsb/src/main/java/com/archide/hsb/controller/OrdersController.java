package com.archide.hsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archide.hsb.service.OrdersService;

@RestController
public class OrdersController {
	
	@Autowired
	private OrdersService orderService;
	
	@RequestMapping(value="/mobile/placeAnOrder")
	public ResponseEntity<String> placeAnOrder(@RequestBody String requestData){
		return orderService.placeAnOrder(requestData);
	}


	@RequestMapping(value="/mobile/kitchen/getKitchenOrders")
	public ResponseEntity<String> getKitchenOrderDetails(@RequestBody String requestData){
		return orderService.getKitchenOrderDetails(requestData);
	}
	
	@RequestMapping(value="/mobile/kitchen/sendUnSyncedKitchenOrders")
	public ResponseEntity<String> sendUnSyncedKitchenOrders(@RequestBody String requestData){
		return orderService.updateKitchenOrderStatus(requestData);
	}
	
	
	@RequestMapping(value="/mobile/getPreviousOrder")
	public ResponseEntity<String> getPreviousOrder(@RequestBody String requestData){
		return orderService.getPreviousOrder(requestData);
	}
	

	@RequestMapping(value="/mobile/closeAnOrder")
	public ResponseEntity<String> closeAnOrder(@RequestParam String tableNumber,@RequestParam String mobileNumber,@RequestParam String placedOrderUUid){
		return orderService.closeAnOrder(tableNumber,mobileNumber,placedOrderUUid);
	}

}
