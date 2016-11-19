package com.archide.hsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
