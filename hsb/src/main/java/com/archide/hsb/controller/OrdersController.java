package com.archide.hsb.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archide.hsb.service.OrdersService;
import com.archide.hsb.service.ServiceUtil;
import com.archide.mobilepay.exception.ValidationException;

@RestController
public class OrdersController {
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger logger = Logger.getLogger(OrdersService.class);
	
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
	public ResponseEntity<String> closeAnOrder(@RequestParam String tableNumber, @RequestParam String mobileNumber,
			@RequestParam String placedOrderUUid) {

		try {

			return orderService.closeAnOrder(tableNumber, mobileNumber, placedOrderUUid);

		} catch (ValidationException e) {
			return serviceUtil.getRestResponse(true, e.getMessage(), e.getCode());
		} catch (Exception e) {
			logger.error("Error in closeAnOrder,Params[" + tableNumber + "" + mobileNumber + "" + placedOrderUUid + "]",
					e);
		}
		return serviceUtil.getRestResponse(false, "Invalid data", 500);
	}
	

	@RequestMapping(value="/mobile/logout")
	public ResponseEntity<String> removeLoginedUser(@RequestParam String tableNumber, @RequestParam String mobileNumber){
		return orderService.removeLoginedUser(mobileNumber,tableNumber);
	}
	
	
	@RequestMapping(value="/mobile/reSentBillDetails")
	public ResponseEntity<String> reSentBillDetails(@RequestParam String placedOrderUUid){
		return orderService.reSentBillDetails(placedOrderUUid);
	}
	

}
