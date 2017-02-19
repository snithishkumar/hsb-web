package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.archide.hsb.service.ServiceUtil;
import com.archide.mobilepay.enumeration.PaymentStatus;

@Entity
@Table(name = "history")
public class HistoryEntity {
	public static final String HISTORY_ID = "historyId";
	public static final String HISTORY_UUID = "historyUUID";
	public static final String ORDER_ID = "orderId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HistoryId")
	private int historyId;
	@Column(name = "HistoryUuid")
	private String historyUUID;
	@Column(name = "PlaceOrdersUUID")
	private String placeOrdersUUID;
	@Column(name = "PurchaseUuid")
	private String purchaseUUID;
	@Column(name = "OrderId")
	private String orderId;
	@Column(name = "TableListId")
	private String tableNumber;
	@Column(name = "ServerDateTime")
	private long serverDateTime;
	@Column(name = "OrderDateTime")
	private long orderDateTime;
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
	@Column(name = "UserMobileNumber")
	private String userMobileNumber;
	@Column(name = "CookingComments",columnDefinition = "TEXT")
	private String cookingComments;
	@Column(name = "IsMerged")
	private boolean isMerged;
	@Column(name = "AmountPaid")
	private double amountPaid;
	@Column(name = "PaymentStatus")
	private PaymentStatus paymentStatus;

	public HistoryEntity(PlacedOrdersEntity placedOrdersEntity) {
		this.historyUUID = ServiceUtil.uuid();
		this.placeOrdersUUID = placedOrdersEntity.getPlaceOrdersUUID();
		this.orderId = placedOrdersEntity.getOrderId();
		this.tableNumber = placedOrdersEntity.getTableNumber().getTableNumber();
		this.purchaseUUID = placedOrdersEntity.getPurchaseUUID();
		this.serverDateTime = placedOrdersEntity.getServerDateTime();
		this.orderDateTime = placedOrdersEntity.getOrderDateTime();
		this.dateTime = placedOrdersEntity.getLastUpdatedDateTime();
		this.price = placedOrdersEntity.getPrice();
		this.taxAmount = placedOrdersEntity.getTaxAmount();
		this.discount = placedOrdersEntity.getDiscount();
		this.totalPrice = placedOrdersEntity.getTotalPrice();
		this.userMobileNumber = placedOrdersEntity.getUserMobileNumber();
		this.isMerged = placedOrdersEntity.isMerged();
		this.amountPaid = placedOrdersEntity.getAmountPaid();
		this.paymentStatus = placedOrdersEntity.getPaymentStatus();
	}

	

	public String getCookingComments() {
		return cookingComments;
	}



	public void setCookingComments(String cookingComments) {
		this.cookingComments = cookingComments;
	}



	public void setTableNumber(String tableNumber) {
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

	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public String getHistoryUUID() {
		return historyUUID;
	}

	public void setHistoryUUID(String historyUUID) {
		this.historyUUID = historyUUID;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
		return "HistoryEntity [historyId=" + historyId + ", historyUUID=" + historyUUID + ", placeOrdersUUID="
				+ placeOrdersUUID + ", purchaseUUID=" + purchaseUUID + ", orderId=" + orderId + ", tableNumber="
				+ tableNumber + ", serverDateTime=" + serverDateTime + ", orderDateTime=" + orderDateTime
				+ ", dateTime=" + dateTime + ", price=" + price + ", taxAmount=" + taxAmount + ", discount=" + discount
				+ ", totalPrice=" + totalPrice + ", userMobileNumber=" + userMobileNumber + "]";
	}

	

}
