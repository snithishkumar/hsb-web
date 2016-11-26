package com.archide.hsb.jsonmodel;

import java.util.ArrayList;
import java.util.List;

import com.archide.hsb.enumeration.DeliveryOptions;
import com.archide.hsb.model.PlacedOrdersEntity;



public class PurchaseJson {
	
	private String billNumber;
	private String purchaseUuid;
	private String purchaseDateTime;
	private String userMobile;
	private DeliveryOptions deliveryOptions;
	private Boolean isEditable;
	private List<PurchaseDetailsJson> purchaseDetails = new ArrayList<PurchaseDetailsJson>();
	private AmountDetailsJson amountDetails;
	private String totalAmount;
	
	public PurchaseJson(PlacedOrdersEntity placedOrders){
		this.billNumber = placedOrders.getOrderId();
		this.purchaseUuid = placedOrders.getPlaceOrdersUUID();
		this.purchaseDateTime = String.valueOf(placedOrders.getOrderDateTime());
		//this.userMobile =
		this.deliveryOptions = DeliveryOptions.NONE;
		this.isEditable  = false;
		this.totalAmount = String.valueOf(placedOrders.getTotalPrice());
	}
	
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getPurchaseUuid() {
		return purchaseUuid;
	}
	public void setPurchaseUuid(String purchaseUuid) {
		this.purchaseUuid = purchaseUuid;
	}
	public String getPurchaseDateTime() {
		return purchaseDateTime;
	}
	public void setPurchaseDateTime(String purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}
	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}
	public Boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	public List<PurchaseDetailsJson> getPurchaseDetails() {
		return purchaseDetails;
	}
	public void setPurchaseDetails(List<PurchaseDetailsJson> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}
	public AmountDetailsJson getAmountDetails() {
		return amountDetails;
	}
	public void setAmountDetails(AmountDetailsJson amountDetails) {
		this.amountDetails = amountDetails;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "PurchaseJson [billNumber=" + billNumber + ", purchaseUuid=" + purchaseUuid + ", purchaseDateTime="
				+ purchaseDateTime + ", userMobile=" + userMobile + ", deliveryOptions=" + deliveryOptions
				+ ", isEditable=" + isEditable + ", purchaseDetails=" + purchaseDetails + ", amountDetails="
				+ amountDetails + ", totalAmount=" + totalAmount + "]";
	}
	
	

}
