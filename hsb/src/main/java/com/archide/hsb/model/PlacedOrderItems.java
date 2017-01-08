package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.archide.hsb.enumeration.OrderStatus;

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
	public static final String ORDER_STATUS = "orderStatus";
	public static final String UN_AVAILABLE_COUNT = "unAvailableCount";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PlacedOrderItemsId")
	private int placedOrderItemsId;
	@Column(name = "PlacedOrderItemsUuid")
	private String placedOrderItemsUUID;
	@ManyToOne
	@JoinColumn(name = "MenuId",referencedColumnName = "MenuId")
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
	@Column(name = "UnAvailableCount")
	private int unAvailableCount;
	@Column(name = "MenuCourseName")
	private String menuCourseName;
	@Column(name = "FoodCategoryName")
	private String foodCategoryName;
	@ManyToOne
	@JoinColumn(name = "PlacedOrdersId")
	private PlacedOrdersEntity placedOrders;
	@Column(name = "OrderStatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
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
	
	
	
	public String getMenuCourseName() {
		return menuCourseName;
	}
	public void setMenuCourseName(String menuCourseName) {
		this.menuCourseName = menuCourseName;
	}
	public String getFoodCategoryName() {
		return foodCategoryName;
	}
	public void setFoodCategoryName(String foodCategoryName) {
		this.foodCategoryName = foodCategoryName;
	}
	/*public long getServerLastUpdatedTime() {
		return serverLastUpdatedTime;
	}
	public void setServerLastUpdatedTime(long serverLastUpdatedTime) {
		this.serverLastUpdatedTime = serverLastUpdatedTime;
	}*/
	public int getUnAvailableCount() {
		return unAvailableCount;
	}
	public void setUnAvailableCount(int unAvailableCount) {
		this.unAvailableCount = unAvailableCount;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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
