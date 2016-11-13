package com.archide.hsb.jsonmodel;

import com.archide.hsb.model.PlacedOrderItems;

public class OrderedMenuItems {
	
	private String name;
	private float quantity;
	private String placedOrderItemsUUID;
	private String menuUuid;
	private String itemCode;
	
	public OrderedMenuItems(){
		
	}
	
	public OrderedMenuItems(PlacedOrderItems placedOrderItems){
		this.name = placedOrderItems.getMenuItem().getName();
		this.quantity = placedOrderItems.getQuantity();
		this.placedOrderItemsUUID = placedOrderItems.getPlacedOrderItemsUUID();
		this.itemCode = placedOrderItems.getMenuItem().getMenuItemCode();
		this.setMenuUuid(placedOrderItems.getMenuItem().getMenuUUID());
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
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
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
