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
	public static final String SERVER_SYNC_TIME = "serverSyncTime";
	public static final String ORDER_DATE_TIME = "orderDateTime";
	public static final String LAST_UPDTED_TIME = "lastUpdatedTime";
	
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
	private int quantity;
	@Column(name = "Name")
	private String name;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "ServerSyncTime")
	private long serverSyncTime;
	@Column(name = "OrderDateTime")
	private long orderDateTime;
	@Column(name = "LastUpdatedTime")
	private long lastUpdatedTime;
	
	@ManyToOne
	@JoinColumn(name = "PlacedOrdersId", referencedColumnName = "PlacedOrdersId")
	private PlacedOrdersEntity placedOrders;
	
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	public PlacedOrdersEntity getPlacedOrders() {
		return placedOrders;
	}
	public void setPlacedOrders(PlacedOrdersEntity placedOrders) {
		this.placedOrders = placedOrders;
	}
	@Override
	public String toString() {
		return "PlacedOrderItems [placedOrderItemsId=" + placedOrderItemsId + ", placedOrderItemsUUID="
				+ placedOrderItemsUUID + ", menuItem=" + menuItem + ", quantity=" + quantity + ", name=" + name
				+ ", itemCode=" + itemCode + ", placedOrders=" + placedOrders + "]";
	}
	
	
	

}
