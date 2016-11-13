package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "placedorderitems")
public class PlacedOrderItems {
	
	public static final String PLACED_ORDER_ITEMS_ID = "placedOrderItemsId";
	public static final String PLACED_ORDER_ITEMS_UUID = "placedOrderItemsUUID";
	public static final String MENU_ITEM = "menuItem";
	public static final String QUANTITY = "quantity";
	public static final String PLACED_ORDERS = "placedOrders";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PlacedOrderItemsId")
	private int placedOrderItemsId;
	@Column(name = "PlacedOrderItemsUuid")
	private String placedOrderItemsUUID;
	@ManyToOne
	@JoinColumn(name = "MenuId", referencedColumnName = "MenuId")
	private MenuEntity menuItem;
	@Column(name = "Quantity")
	private float quantity;
	@Column(name = "Name")
	private String name;
	@Column(name = "ItemCode")
	private String itemCode;
	
	@ManyToOne
	@JoinColumn(name = "PlacedOrdersId", referencedColumnName = "PlacedOrdersId")
	private PlacedOrders placedOrders;
	
	public int getPlacedOrderItemsId() {
		return placedOrderItemsId;
	}
	public void setPlacedOrderItemsId(int placedOrderItemsId) {
		this.placedOrderItemsId = placedOrderItemsId;
	}
	public String getPlacedOrderItemsUUID() {
		return placedOrderItemsUUID;
	}
	public void setPlacedOrderItemsUUID(String placedOrderItemsUUID) {
		this.placedOrderItemsUUID = placedOrderItemsUUID;
	}
	public MenuEntity getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuEntity menuItem) {
		this.menuItem = menuItem;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public PlacedOrders getPlacedOrders() {
		return placedOrders;
	}
	public void setPlacedOrders(PlacedOrders placedOrders) {
		this.placedOrders = placedOrders;
	}
	@Override
	public String toString() {
		return "PlacedOrderItems [placedOrderItemsId=" + placedOrderItemsId + ", placedOrderItemsUUID="
				+ placedOrderItemsUUID + ", menuItem=" + menuItem + ", quantity=" + quantity + ", name=" + name
				+ ", itemCode=" + itemCode + ", placedOrders=" + placedOrders + "]";
	}
	
	
	

}
