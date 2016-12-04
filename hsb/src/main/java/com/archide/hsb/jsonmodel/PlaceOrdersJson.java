package com.archide.hsb.jsonmodel;

import java.util.ArrayList;
import java.util.List;

import com.archide.hsb.model.PlacedOrdersEntity;

public class PlaceOrdersJson {
	
	private String tableNumber;
	private String userMobileNumber;
	private List<OrderedMenuItems> menuItems = new ArrayList<OrderedMenuItems>();
	private double price;
	private double taxAmount;
	private double discount;
	private double totalPrice;
	private String placeOrderUuid;
	private String orderId;
	private long serverDateTime;
	private long orderDateTime;
	private long lastUpdatedDateTime;
	
	public PlaceOrdersJson(){
		
	}
	
   public PlaceOrdersJson(PlacedOrdersEntity placedOrders){
		this.price = placedOrders.getPrice();
		this.taxAmount = placedOrders.getTaxAmount();
		this.discount = placedOrders.getDiscount();
		this.totalPrice = placedOrders.getTotalPrice();
		this.placeOrderUuid = placedOrders.getPlaceOrdersUUID();
		this.orderId = placedOrders.getOrderId();
		this.serverDateTime = placedOrders.getServerDateTime();
		this.orderDateTime = placedOrders.getOrderDateTime();
		this.lastUpdatedDateTime = placedOrders.getLastUpdatedDateTime();
		this.tableNumber = placedOrders.getTableNumber().getTableNumber();
		this.userMobileNumber = placedOrders.getUserMobileNumber();
	}
   
   public PlaceOrdersJson(PlacedOrdersEntity placedOrders,boolean isPrice){
 		this.placeOrderUuid = placedOrders.getPlaceOrdersUUID();
 		this.orderId = placedOrders.getOrderId();
 		this.serverDateTime = placedOrders.getServerDateTime();
 		this.orderDateTime = placedOrders.getOrderDateTime();
 		this.lastUpdatedDateTime = placedOrders.getLastUpdatedDateTime();
 		this.tableNumber = placedOrders.getTableNumber().getTableNumber();
 	}
	
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public List<OrderedMenuItems> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<OrderedMenuItems> menuItems) {
		this.menuItems = menuItems;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	

	public long getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(long serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public long getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(long orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public long getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(long lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public String getPlaceOrderUuid() {
		return placeOrderUuid;
	}
	public void setPlaceOrderUuid(String placeOrderUuid) {
		this.placeOrderUuid = placeOrderUuid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	

}
