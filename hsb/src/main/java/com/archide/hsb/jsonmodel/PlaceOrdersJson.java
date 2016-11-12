package com.archide.hsb.jsonmodel;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrdersJson {
	
	private String tableNumber;
	private String userMobileNumber;
	private List<MenuItems> menuItems = new ArrayList<PlaceOrdersJson.MenuItems>();
	private double price;
	private double taxAmount;
	private double discount;
	private double totalPrice;
	private String placeOrderUuid;
	private String orderId;
	
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

	public List<MenuItems> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItems> menuItems) {
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



	public class MenuItems{
		private float quantity;
		private String menuUuid;
		public float getQuantity() {
			return quantity;
		}
		public void setQuantity(float quantity) {
			this.quantity = quantity;
		}
		public String getMenuUuid() {
			return menuUuid;
		}
		public void setMenuUuid(String menuUuid) {
			this.menuUuid = menuUuid;
		}
		@Override
		public String toString() {
			return "MenuItems [quantity=" + quantity + ", menuUuid=" + menuUuid + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "PlaceOrdersJson [tableNumber=" + tableNumber + ", userMobileNumber=" + userMobileNumber + ", menuItems="
				+ menuItems + ", price=" + price + ", taxAmount=" + taxAmount + ", discount=" + discount
				+ ", totalPrice=" + totalPrice + "]";
	}
	
	

}
