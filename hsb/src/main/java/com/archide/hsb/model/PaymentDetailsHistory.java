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

import com.archide.hsb.service.ServiceUtil;
import com.archide.mobilepay.enumeration.DeliveryOptions;
import com.archide.mobilepay.enumeration.OrderStatus;

@Entity
@Table(name = "paymentdetailshistory")
public class PaymentDetailsHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentDetailsHistoryId")
	private int paymentDetailsHistoryId;
	@Column(name = "PaymentDetailsUuid")
	private String paymentDetailsHistoryUUID;
	@Column(name = "PurchaseUuid")
	private String purchaseUUID;
	@Column(name = "BillNumber")
	private String billNumber;
	@Column(name = "OrderStatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@Column(name = "PaymentDateTime")
	private long paymentDateTime;
	@Column(name = "PurchaseItems",columnDefinition = "TEXT")
	private String purchaseItems;
	@Column(name = "UserDeliveryOptions")
	@Enumerated(EnumType.STRING)
	private DeliveryOptions userDeliveryOptions;
	@Column(name = "PayedAmount")
	private double payedAmount;
	@Column(name = "CalculatedAmounts",columnDefinition = "TEXT")
	private String calculatedAmounts;
	@Column(name = "DiscardDetails",columnDefinition = "TEXT")
	private String discardDetails;

	@ManyToOne
	@JoinColumn(name = "HistoryId",referencedColumnName = "HistoryId")
	private HistoryEntity historyEntity;
	
	public PaymentDetailsHistory(PaymentDetails paymentDetails){
		this.paymentDetailsHistoryUUID = ServiceUtil.uuid();
		this.purchaseUUID = paymentDetails.getPurchaseUUID();
		this.billNumber = paymentDetails.getBillNumber();
		this.orderStatus = paymentDetails.getOrderStatus();
		this.paymentDateTime = paymentDetails.getPaymentDateTime();
		this.purchaseItems = paymentDetails.getPurchaseItems();
		this.userDeliveryOptions = paymentDetails.getUserDeliveryOptions();
		this.payedAmount = paymentDetails.getPayedAmount();
		this.calculatedAmounts = paymentDetails.getCalculatedAmounts();
	}
	
	public String getDiscardDetails() {
		return discardDetails;
	}

	public void setDiscardDetails(String discardDetails) {
		this.discardDetails = discardDetails;
	}

	public int getPaymentDetailsHistoryId() {
		return paymentDetailsHistoryId;
	}
	public void setPaymentDetailsHistoryId(int paymentDetailsHistoryId) {
		this.paymentDetailsHistoryId = paymentDetailsHistoryId;
	}
	public String getPaymentDetailsHistoryUUID() {
		return paymentDetailsHistoryUUID;
	}
	public void setPaymentDetailsHistoryUUID(String paymentDetailsHistoryUUID) {
		this.paymentDetailsHistoryUUID = paymentDetailsHistoryUUID;
	}
	public String getPurchaseUUID() {
		return purchaseUUID;
	}
	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public long getPaymentDateTime() {
		return paymentDateTime;
	}
	public void setPaymentDateTime(long paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}
	public String getPurchaseItems() {
		return purchaseItems;
	}
	public void setPurchaseItems(String purchaseItems) {
		this.purchaseItems = purchaseItems;
	}
	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}
	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}
	public double getPayedAmount() {
		return payedAmount;
	}
	public void setPayedAmount(double payedAmount) {
		this.payedAmount = payedAmount;
	}
	public String getCalculatedAmounts() {
		return calculatedAmounts;
	}
	public void setCalculatedAmounts(String calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
	}

	public HistoryEntity getHistoryEntity() {
		return historyEntity;
	}

	public void setHistoryEntity(HistoryEntity historyEntity) {
		this.historyEntity = historyEntity;
	}
	
	
	

}
