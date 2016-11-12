package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tablelist")
public class TableList {
	
	public static final String TABLE_LIST_ID = "tableListId";
	public static final String TABLE_NUMBER = "tableNumber";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TableListId")
	private int tableListId;
	@Column(name = "TableNumber")
	private String tableNumber;
	
	public int getTableListId() {
		return tableListId;
	}
	public void setTableListId(int tableListId) {
		this.tableListId = tableListId;
	}
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	@Override
	public String toString() {
		return "TableList [tableListId=" + tableListId + ", tableNumber=" + tableNumber + "]";
	}
	
	

}
