package com.archide.mobilepay.json;

import com.archide.hsb.model.PlacedOrderItems;

public class PurchaseItem {
	private int itemNo;
	private String name;
	private int quantity;
	private double unitPrice;
	private Double amount;
	private float rating;
	
	private String itemCode;

	public PurchaseItem(PlacedOrderItems orderItems){
		this.itemNo = Integer.valueOf(orderItems.getItemCode());
		this.name = orderItems.getName();
		double price = orderItems.getMenuItem().getPrice();
		this.unitPrice = price;
		this.quantity = orderItems.getQuantity();
		this.amount = quantity * price;
		this.rating = 0;
		this.itemCode = orderItems.getMenuItem().getMenuItemCode();
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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}


	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseItem other = (PurchaseItem) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PurchaseItem [itemNo=" + itemNo + ", name=" + name + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + ", amount=" + amount + ", rating=" + rating + "]";
	}

	

}
