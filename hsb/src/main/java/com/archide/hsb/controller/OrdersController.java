package com.archide.hsb.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.jsonmodel.UnAvailableMenuDetails;
import com.archide.hsb.service.OrdersService;
import com.archide.hsb.service.ServiceUtil;
import com.archide.mobilepay.exception.ValidationException;
import com.google.gson.Gson;

@RestController
public class OrdersController {
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private Gson gson;
	
	
	
	private static final Logger logger = Logger.getLogger(OrdersService.class);
	
	@RequestMapping(value="/mobile/placeAnOrder")
	public ResponseEntity<String> placeAnOrder(@RequestBody String requestData){
		logger.info("-----PlaceAnOrder Data-------");
		logger.info(requestData);
		logger.info("-----Raw Data End-------");
		UnAvailableMenuDetails unAvailableMenuDetails = new UnAvailableMenuDetails();
		try{
			long serverDateTime = orderService.placeAnOrder(requestData,unAvailableMenuDetails);
			return serviceUtil.getRestResponse(true,serverDateTime,200);
			
		}catch(ValidationException e){
			return serviceUtil.getRestResponse(true, e.getMessage(),e.getCode());
		}catch(Exception e){
			if(unAvailableMenuDetails.getUnAvailableMenuDetails().size() > 0){
				return serviceUtil.getRestResponse(true, gson.toJson(unAvailableMenuDetails),405);
			}
			logger.error("Error in placeAnOrder,Params["+requestData+"]", e);
		}
		return serviceUtil.getRestResponse(true, "Internal Server Error",500);
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
	
	
	@RequestMapping(value="/mobile/discardData")
	public ResponseEntity<String> discardData(@RequestParam String placedOrderUUid){
		return orderService.discardData(placedOrderUUid);
	}
	

}
