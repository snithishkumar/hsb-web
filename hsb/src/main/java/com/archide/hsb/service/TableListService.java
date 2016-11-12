package com.archide.hsb.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.archide.hsb.dao.TableListDao;
import com.archide.hsb.model.TableList;
import com.google.gson.Gson;

@Service("tableListService")
public class TableListService {
	
	private static final Logger logger = Logger.getLogger(TableListService.class);
	@Autowired
	private TableListDao tableListDao;
	@Autowired
	private ServiceUtil serviceUtil;
	@Autowired
	private Gson gson;
	
	/**
	 * Get Table List
	 * @return
	 */
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getListOfTables(){
		try{
			List<TableList> tableList =  tableListDao.getTableList();
			return serviceUtil.getRestResponse(true, gson.toJson(tableList));
		}catch(Exception e){
			logger.error("Error in getListOfTables",e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}

}
