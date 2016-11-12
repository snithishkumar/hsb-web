package com.archide.hsb.jsonmodel;

import com.archide.hsb.enumeration.Status;
import com.archide.hsb.model.MenuEntity;

public class MenuItemJson {
	
	private String menuUuid;
	private String menuItemCode;
	private String name;
	private double price;
	private Status status;
	private long dateTime;
	
	public MenuItemJson(MenuEntity menuEntity){
		this.menuUuid = menuEntity.getMenuUUID();
		this.menuItemCode = menuEntity.getMenuItemCode();
		this.name = menuEntity.getName();
		this.price = menuEntity.getPrice();
		this.status = menuEntity.getStatus();
		this.dateTime = menuEntity.getDateTime();
	}
	
	
	public String getMenuUuid() {
		return menuUuid;
	}


	public void setMenuUuid(String menuUuid) {
		this.menuUuid = menuUuid;
	}


	public String getMenuItemCode() {
		return menuItemCode;
	}
	public void setMenuItemCode(String menuItemCode) {
		this.menuItemCode = menuItemCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "MenuItemJson [menuUuid=" + menuUuid + ", menuItemCode=" + menuItemCode + ", name=" + name + ", price="
				+ price + ", status=" + status + ", dateTime=" + dateTime + "]";
	}
	
	

}