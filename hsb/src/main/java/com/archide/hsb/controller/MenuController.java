package com.archide.hsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archide.hsb.service.MenuService;

@RestController
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/mobile/getMenuItems")
	public ResponseEntity<String> getMenuLists(@RequestParam String lastServerSyncTime,@RequestParam String tableNumber){
		return menuService.getMenuDetails(lastServerSyncTime,tableNumber);
	}
	
	
	@RequestMapping(value="/mobile/getUnAvailableMenuItems")
	public ResponseEntity<String> getUnAvailableMenuItems(@RequestParam String lastServerSyncTime){
		return menuService.getUnAvailableMenuItems(Long.valueOf(lastServerSyncTime));
	}

	//getUnAvailableMenuItems

}
