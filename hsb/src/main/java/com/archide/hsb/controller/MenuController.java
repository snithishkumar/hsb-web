package com.archide.hsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archide.hsb.enumeration.AppType;
import com.archide.hsb.enumeration.OrderType;
import com.archide.hsb.service.MenuService;

@RestController
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/mobile/getMenuItems")
	public ResponseEntity<String> getMenuLists(@RequestBody String requestData){
		return menuService.getMenuDetails(requestData);
	}
	
	
	@RequestMapping(value="/mobile/kitchen/getMenuItems")
	public ResponseEntity<String> getMenuLists(){
		return menuService.getMenuDetails();
	}
	
	@RequestMapping(value="/mobile/kitchen/sendKitchenMenuUpdates")
	public ResponseEntity<String> sendKitchenMenuUpdates(@RequestBody String data){
		return menuService.processKitchenMenuUpdates(data);
	}
	
	
	@RequestMapping(value="/mobile/getUnAvailableMenuItems")
	public ResponseEntity<String> getUnAvailableMenuItems(@RequestParam String lastServerSyncTime){
		return menuService.getUnAvailableMenuItems(Long.valueOf(lastServerSyncTime));
	}

	//getUnAvailableMenuItems

}
