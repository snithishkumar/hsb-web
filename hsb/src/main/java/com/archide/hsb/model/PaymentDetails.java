package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.archide.mobilepay.enumeration.DeliveryOptions;
import com.archide.mobilepay.enumeration.OrderStatus;
import com.archide.mobilepay.json.HistoryPurchaseData;

@Entity
@Table(name = "paymentdetails")
public class PaymentDetails {
	
	public static final String PAYMENT_DETAILS_ID = "paymentDetailsId";
	public static final String PAYMENT_DETAILS_UUID = "paymentDetailsUUID";
	public static final String PURCHASE_UUID = "purchaseUUID";
	public static final String BILL_NUMBER = "billNumber";
	public static final String ORDER_STATUS = "orderStatus";
	public static final String PAYMENT_DATE_TIME = "paymentDateTime";
	public static final String PURCHASE_ITEMS = "purchaseItems";
	public static final String USER_DELIVERY_OPTIONS = "userDeliveryOptions";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentDetailsId")
	private int paymentDetailsId;
	@Column(name = "PaymentDetailsUuid")
	private String paymentDetailsUUID;
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
	@Column(name = "CalculatedAmounts")
	private String calculatedAmounts;
	public PaymentDetails(){
		
	}
	
	public PaymentDetails(HistoryPurchaseData historyPurchaseData){
		this.purchaseUUID = historyPurchaseData.getPurchaseUUID();
		this.billNumber = historyPurchaseData.getBillNumber();
		this.orderStatus = historyPurchaseData.getOrderStatus();
		this.paymentDateTime = historyPurchaseData.getLastModifiedDate();
		this.userDeliveryOptions = historyPurchaseData.getUserDeliveryOptions();
		this.payedAmount = historyPurchaseData.getTotalAmount();
		
	}
	
	public int getPaymentDetailsId() {
		return paymentDetailsId;
	}
	public void setPaymentDetailsId(int paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}
	public String getPaymentDetailsUUID() {
		return paymentDetailsUUID;
	}
	public void setPaymentDetailsUUID(String paymentDetailsUUID) {
		this.paymentDetailsUUID = paymentDetailsUUID;
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
	@Override
	public String toString() {
		return "PaymentDetails [paymentDetailsId=" + paymentDetailsId + ", paymentDetailsUUID=" + paymentDetailsUUID
				+ ", purchaseUUID=" + purchaseUUID + ", billNumber=" + billNumber + ", orderStatus=" + orderStatus
				+ ", paymentDateTime=" + paymentDateTime + ", purchaseItems=" + purchaseItems + ", userDeliveryOptions="
				+ userDeliveryOptions + ", payedAmount=" + payedAmount + ", calculatedAmounts=" + calculatedAmounts
				+ "]";
	}
	
	
	
	
	

}
