package com.archide.hsb.jsonmodel;

import com.archide.hsb.model.PlacedOrderItems;

public class HistoryMenuItem {
	
	private String itemCode;
	private String name;
	private int  quantity;
	private String desc;
	private String menuCourse;
	private String foodCategory;
	private double cost;
	
	public HistoryMenuItem(PlacedOrderItems placedOrderItems){
		this.itemCode = placedOrderItems.getItemCode();
		this.name = placedOrderItems.getName();
		this.quantity = placedOrderItems.getQuantity();
		//this.desc = placedOrderItems.getMenuItem().get
		this.menuCourse = placedOrderItems.getMenuItem().getMenuCourse().getCategoryName();
		this.foodCategory = placedOrderItems.getMenuItem().getFoodCategory().getCategoryName();
		this.cost = placedOrderItems.getMenuItem().getPrice();
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMenuCourse() {
		return menuCourse;
	}
	public void setMenuCourse(String menuCourse) {
		this.menuCourse = menuCourse;
	}
	public String getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "HistoryMenuItem [itemCode=" + itemCode + ", name=" + name + ", quantity=" + quantity + ", desc=" + desc
				+ ", menuCourse=" + menuCourse + ", foodCategory=" + foodCategory + ", cost=" + cost + "]";
	}
	
	

}
