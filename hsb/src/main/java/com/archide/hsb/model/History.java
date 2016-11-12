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
@Table(name="history")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HistoryId")
	private int historyId;
	@Column(name = "HistoryUuid")
	private String historyUUID;
	@Column(name = "OrderId")
	private String orderId;
	@ManyToOne
	@JoinColumn(name = "TableListId", referencedColumnName = "TableListId")
	private TableList tableNumber;
	@Column(name = "DateTime")
	private double dateTime;
	@Column(name = "Price")
	private double price;
	@Column(name = "TaxAmount")
	private double taxAmount;
	@Column(name = "Discount")
	private double discount;
	@Column(name = "TotalPrice")
	private double totalPrice;
	@Column(name = "Items")
	private String items;
	
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
	public TableList getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(TableList tableNumber) {
		this.tableNumber = tableNumber;
	}
	public double getDateTime() {
		return dateTime;
	}
	public void setDateTime(double dateTime) {
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
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "History [historyId=" + historyId + ", historyUUID=" + historyUUID + ", orderId=" + orderId
				+ ", tableNumber=" + tableNumber + ", dateTime=" + dateTime + ", price=" + price + ", taxAmount="
				+ taxAmount + ", discount=" + discount + ", totalPrice=" + totalPrice + ", items=" + items + "]";
	}
	
	

}
