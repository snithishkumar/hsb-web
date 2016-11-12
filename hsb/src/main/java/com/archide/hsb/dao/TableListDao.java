package com.archide.hsb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.archide.hsb.model.TableList;

@Repository
public interface TableListDao {
	
	List<TableList> getTableList();
	
	TableList getTables(String tableNumber);
	

}
