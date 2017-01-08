package com.archide.hsb.jsonmodel;

import com.archide.hsb.enumeration.FoodType;
import com.archide.hsb.enumeration.Status;

public class HistoryMenuItem {
	
	private String menuItemCode;
	private String name;
	private double price;
	private Status status;
	private FoodType foodType;
	private long dateTime;
	private long serverTime;
	private String description;
	private String tasteType;
	
	public HistoryMenuItem(OrderedMenuItems orderedMenuItems){
		this.menuItemCode = orderedMenuItems.getItemCode();
		this.name = orderedMenuItems.getName();
		//this.price = orderedMenuItems.get
		//this.status = orderedMenuItems.getOrderStatus().toString();
		//this.foodType = orderedMenuItems.getFoodType();
		//this.dateTime = orderedMenuItems.getOrderDateTime();
		//this.serverTime = orderedMenuItems.getLastUpdatedDateTime();
		//this.description = orderedMenuItems.get
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
	public FoodType getFoodType() {
		return foodType;
	}
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	public long getServerTime() {
		return serverTime;
	}
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTasteType() {
		return tasteType;
	}
	public void setTasteType(String tasteType) {
		this.tasteType = tasteType;
	}
	@Override
	public String toString() {
		return "HistoryMenuItem [menuItemCode=" + menuItemCode + ", name=" + name + ", price=" + price + ", status="
				+ status + ", foodType=" + foodType + ", dateTime=" + dateTime + ", serverTime=" + serverTime
				+ ", description=" + description + ", tasteType=" + tasteType + "]";
	}
	

	

}
