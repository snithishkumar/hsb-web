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
public class PlacedOrders {
	
	public static final String PLACED_ORDERS_ID = "placeOrdersId";
	public static final String PLACED_ORDERS_UUID = "placeOrdersUUID";
	public static final String ORDER_ID = "orderId";
	public static final String TABLE_NUMBER = "tableNumber";
	public static final String DATE_TIME = "dateTime";
	public static final String PRICE = "price";
	public static final String TAX_AMOUNT = "taxAmount";
	public static final String DISCOUNT = "discount";
	public static final String TOTAL_PRICE = "totalPrice";
	
	
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
	@Column(name = "DateTime")
	private long dateTime;
	@Column(name = "Price")
	private double price;
	@Column(name = "TaxAmount")
	private double taxAmount;
	@Column(name = "Discount")
	private double discount;
	@Column(name = "TotalPrice")
	private double totalPrice;
	
	public PlacedOrders(){
		
	}
	
	
public PlacedOrders(PlaceOrdersJson placeOrdersJson){
		this.placeOrdersUUID = placeOrdersJson.getPlaceOrderUuid();
		this.dateTime = ServiceUtil.getCurrentGmtTime();
		this.price = placeOrdersJson.getPrice();
		this.discount = placeOrdersJson.getDiscount();
		this.totalPrice = placeOrdersJson.getTotalPrice();
		this.taxAmount = placeOrdersJson.getTaxAmount();
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
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
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
	@Override
	public String toString() {
		return "PlacedOrders [placeOrdersId=" + placeOrdersId + ", placeOrdersUUID=" + placeOrdersUUID + ", orderId="
				+ orderId + ", tableNumber=" + tableNumber + ", dateTime=" + dateTime + ", price=" + price
				+ ", taxAmount=" + taxAmount + ", discount=" + discount + ", totalPrice=" + totalPrice + "]";
	}
	
	

}
