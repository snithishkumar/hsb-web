package com.archide.hsb.jsonmodel;

import com.archide.hsb.enumeration.OrderStatus;
import com.archide.hsb.model.PlacedOrderItems;

public class HistoryMenuItem {
	
	private String placedOrderItemsUUID;
	private String menuItemCode;
	private String name;
	private double price;
	private String foodCategory;
	private String menuCourse;
	private String description;
	private long serverSyncTime;
	private long orderDateTime;
	private long lastUpdatedTime;
	private int unAvailableCount;
	private int quantity;
	private String tableNumber;
	private OrderStatus orderStatus;
	
	public HistoryMenuItem(PlacedOrderItems placedOrderItems){
		this.placedOrderItemsUUID = placedOrderItems.getPlacedOrderItemsUUID();
		this.menuItemCode = placedOrderItems.getItemCode();
		this.name = placedOrderItems.getName();
		this.price = placedOrderItems.getMenuItem().getPrice();
		this.foodCategory = placedOrderItems.getFoodCategoryName();
		this.menuCourse = placedOrderItems.getMenuCourseName();
		this.description = placedOrderItems.getMenuItem().getDescription();
		this.serverSyncTime = placedOrderItems.getServerSyncTime();
		this.orderDateTime = placedOrderItems.getOrderDateTime();
		this.lastUpdatedTime = placedOrderItems.getLastUpdatedTime();
		this.unAvailableCount = placedOrderItems.getUnAvailableCount();
		this.quantity = placedOrderItems.getQuantity();
		this.tableNumber = placedOrderItems.getTableNumber();
	}
	
	public String getPlacedOrderItemsUUID() {
		return placedOrderItemsUUID;
	}
	public void setPlacedOrderItemsUUID(String placedOrderItemsUUID) {
		this.placedOrderItemsUUID = placedOrderItemsUUID;
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
	public String getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}
	public String getMenuCourse() {
		return menuCourse;
	}
	public void setMenuCourse(String menuCourse) {
		this.menuCourse = menuCourse;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getServerSyncTime() {
		return serverSyncTime;
	}
	public void setServerSyncTime(long serverSyncTime) {
		this.serverSyncTime = serverSyncTime;
	}
	public long getOrderDateTime() {
		return orderDateTime;
	}
	public void setOrderDateTime(long orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	public long getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public int getUnAvailableCount() {
		return unAvailableCount;
	}
	public void setUnAvailableCount(int unAvailableCount) {
		this.unAvailableCount = unAvailableCount;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "HistoryMenuItem [placedOrderItemsUUID=" + placedOrderItemsUUID + ", menuItemCode=" + menuItemCode
				+ ", name=" + name + ", price=" + price + ", foodCategory=" + foodCategory + ", menuCourse="
				+ menuCourse + ", description=" + description + ", serverSyncTime=" + serverSyncTime
				+ ", orderDateTime=" + orderDateTime + ", lastUpdatedTime=" + lastUpdatedTime + ", unAvailableCount="
				+ unAvailableCount + ", quantity=" + quantity + ", tableNumber=" + tableNumber + ", orderStatus="
				+ orderStatus + "]";
	}
	
	

	

}
