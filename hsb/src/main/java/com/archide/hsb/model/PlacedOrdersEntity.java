package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.service.ServiceUtil;

@Entity
@Table(name = "placedorders")
public class PlacedOrdersEntity {
	
	public static final String PLACED_ORDERS_ID = "placeOrdersId";
	public static final String PLACED_ORDERS_UUID = "placeOrdersUUID";
	public static final String ORDER_ID = "orderId";
	public static final String TABLE_NUMBER = "tableNumber";
	public static final String DATE_TIME = "dateTime";
	public static final String PRICE = "price";
	public static final String TAX_AMOUNT = "taxAmount";
	public static final String DISCOUNT = "discount";
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String SERVER_DATE_TIME = "serverDateTime";
	public static final String USER_MOBILE_NUMBER = "userMobileNumber";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PlacedOrdersId")
	private int placeOrdersId;
	@Column(name = "PlacedOrdersUuid")
	private String placeOrdersUUID;
	@Column(name = "OrderId")
	private String orderId;
	@ManyToOne
	@JoinColumn(name = "TableListId", referencedColumnName = "TableListId")
	private TableList tableNumber;
	@Column(name = "ServerDateTime")
	private long serverDateTime;
	@Column(name = "OrderDateTime")
	private long orderDateTime;
	@Column(name = "LastUpdatedDateTime")
	private long lastUpdatedDateTime;
	@Column(name = "Price")
	private double price;
	@Column(name = "TaxAmount")
	private double taxAmount;
	@Column(name = "Discount")
	private double discount;
	@Column(name = "TotalPrice")
	private double totalPrice;
	@Column(name = "UserMobileNumber")
	private String userMobileNumber;
	
	public PlacedOrdersEntity(){
		
	}
	
	
	public PlacedOrdersEntity(PlaceOrdersJson placeOrdersJson){
		this.placeOrdersUUID = placeOrdersJson.getPlaceOrderUuid();
		this.serverDateTime = ServiceUtil.getCurrentGmtTime();
		this.orderDateTime = placeOrdersJson.getOrderDateTime();
		this.lastUpdatedDateTime = serverDateTime;
		this.price = placeOrdersJson.getPrice();
		this.discount = placeOrdersJson.getDiscount();
		this.totalPrice = placeOrdersJson.getTotalPrice();
		this.taxAmount = placeOrdersJson.getTaxAmount();
		this.orderId = placeOrdersJson.getOrderId();
		this.userMobileNumber = placeOrdersJson.getUserMobileNumber();
	}
	
	
	public int getPlaceOrdersId() {
		return placeOrdersId;
	}
	public void setPlaceOrdersId(int placeOrdersId) {
		this.placeOrdersId = placeOrdersId;
	}
	public String getPlaceOrdersUUID() {
		return placeOrdersUUID;
	}
	public void setPlaceOrdersUUID(String placeOrdersUUID) {
		this.placeOrdersUUID = placeOrdersUUID;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public TableList getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(TableList tableNumber) {
		this.tableNumber = tableNumber;
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
	
	


	public String getUserMobileNumber() {
		return userMobileNumber;
	}


	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}


	@Override
	public String toString() {
		return "PlacedOrders [placeOrdersId=" + placeOrdersId + ", placeOrdersUUID=" + placeOrdersUUID + ", orderId="
				+ orderId + ", tableNumber=" + tableNumber + ", serverDateTime=" + serverDateTime + ", orderDateTime="
				+ orderDateTime + ", lastUpdatedDateTime=" + lastUpdatedDateTime + ", price=" + price + ", taxAmount="
				+ taxAmount + ", discount=" + discount + ", totalPrice=" + totalPrice + "]";
	}
	
	
	

}
