package com.archide.hsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archide.hsb.service.TableListService;

@RestController
public class TableListController {
	
	@Autowired
	private TableListService tableListService;
	
	@RequestMapping(value="/mobile/getTableList")
	public ResponseEntity<String> getListOfTables(){
		return tableListService.getListOfTables();
	}
	
	@RequestMapping(value="/mobile/removeReservedTable")
	public ResponseEntity<String> removeReservedTable(String tableNumber){
		return tableListService.removeReservedTable(tableNumber);
	}
	
	
	

}
