package com.archide.mobilepay.json;

import com.archide.hsb.model.PlacedOrderItems;

public class PurchaseDetailsJson {
	
	private int itemNo;

	private String name;
	private int quantity;
	private String amount;
	private String unitPrice;
	private float rating = 0;
	
	public PurchaseDetailsJson(PlacedOrderItems orderItems){
		this.itemNo = Integer.valueOf(orderItems.getItemCode());
		this.name = orderItems.getName();
		double price = orderItems.getMenuItem().getPrice();
		this.unitPrice = String.valueOf(price);
		this.quantity = orderItems.getQuantity();
		this.amount = String.valueOf(quantity * price);
	}
	public int getItemNo() {
		return itemNo;
	}
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "PurchaseDetailsJson [itemNo=" + itemNo + ", name=" + name + ", quantity=" + quantity + ", amount="
				+ amount + ", unitPrice=" + unitPrice + ", rating=" + rating + "]";
	}
	
	

}
