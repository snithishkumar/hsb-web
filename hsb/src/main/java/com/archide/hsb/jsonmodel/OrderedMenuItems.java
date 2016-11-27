package com.archide.hsb.jsonmodel;

import com.archide.hsb.enumeration.FoodType;
import com.archide.hsb.enumeration.OrderStatus;
import com.archide.hsb.model.PlacedOrderItems;

public class OrderedMenuItems {
	
	private String name;
	private int quantity;
	private String placedOrderItemsUUID;
	private String menuUuid;
	private String itemCode;
	private String categoryName;
	private String categoryUuid;
	private FoodType foodType;
	private OrderStatus orderStatus;
	
	public OrderedMenuItems(){
		
	}
	
	public OrderedMenuItems(PlacedOrderItems placedOrderItems){
		this.name = placedOrderItems.getMenuItem().getName();
		this.quantity = placedOrderItems.getQuantity();
		this.placedOrderItemsUUID = placedOrderItems.getPlacedOrderItemsUUID();
		this.itemCode = placedOrderItems.getMenuItem().getMenuItemCode();
		this.setMenuUuid(placedOrderItems.getMenuItem().getMenuUUID());
		this.orderStatus = placedOrderItems.getOrderStatus();
	}
	
	public OrderedMenuItems(PlacedOrderItems placedOrderItems,boolean isKitchen){
		this.name = placedOrderItems.getMenuItem().getName();
		this.quantity = placedOrderItems.getQuantity();
		this.placedOrderItemsUUID = placedOrderItems.getPlacedOrderItemsUUID();
		this.itemCode = placedOrderItems.getMenuItem().getMenuItemCode();
		this.setMenuUuid(placedOrderItems.getMenuItem().getMenuUUID());
		this.foodType = placedOrderItems.getMenuItem().getFoodType();
		this.orderStatus = placedOrderItems.getOrderStatus();
		this.categoryUuid = placedOrderItems.getMenuItem().getFoodCategory().getFoodCategoryUUID();
		this.categoryName = placedOrderItems.getMenuItem().getFoodCategory().getCategoryName()+"-"+placedOrderItems.getMenuItem().getMenuCourse().getCategoryName();
	}
	
	
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryUuid() {
		return categoryUuid;
	}

	public void setCategoryUuid(String categoryUuid) {
		this.categoryUuid = categoryUuid;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getPlacedOrderItemsUUID() {
		return placedOrderItemsUUID;
	}
	public void setPlacedOrderItemsUUID(String placedOrderItemsUUID) {
		this.placedOrderItemsUUID = placedOrderItemsUUID;
	}
	@Override
	public String toString() {
		return "OrderedMenuItems [name=" + name + ", quantity=" + quantity + ", placedOrderItemsUUID="
				+ placedOrderItemsUUID + "]";
	}

	public String getMenuUuid() {
		return menuUuid;
	}

	public void setMenuUuid(String menuUuid) {
		this.menuUuid = menuUuid;
	}
	
	

}
