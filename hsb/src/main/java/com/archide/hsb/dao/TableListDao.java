package com.archide.hsb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.archide.hsb.model.ReservedTableEntity;
import com.archide.hsb.model.TableList;

@Repository
public interface TableListDao {
	
	List<TableList> getTableList();
	
	TableList getTables(String tableNumber);
	
	List<String> getReservedTableList();
	
	List<String> getAvailableTableNumbers();
	
	boolean isReserved(String tableNumber);
	
	void createReservedTableEntity(ReservedTableEntity reservedTableEntity);
	
	ReservedTableEntity getReservedTable(String tableNumber);
	
	ReservedTableEntity getReservedTableByMobile(String mobileNumber,String tableNumber);
	
	void updateReservedTableEntity(ReservedTableEntity reservedTableEntity);
	
	TableList getTableList(String tableNumber);
	
	void deleteReservedTable(String tableNumber);
	

}
