package com.archide.mobilepay.json;

import java.util.ArrayList;
import java.util.List;

import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.mobilepay.enumeration.DeliveryOptions;

public class MerchantPurchaseData {

	private String customerName;
	private String customerMobileNo;
	private DeliveryOptions deliveryOptions;
	private boolean isRemovable;
	private long purchaseDate;
	private double totalAmount;
	private String billNumber;
	private List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
	private AmountDetails amountDetails;
	
	public MerchantPurchaseData(PlacedOrdersEntity placedOrdersEntity){
		this.billNumber = placedOrdersEntity.getOrderId();
		this.purchaseDate = placedOrdersEntity.getOrderDateTime();
		this.customerMobileNo = placedOrdersEntity.getUserMobileNumber();
		this.deliveryOptions = DeliveryOptions.NONE;
		this.isRemovable = false;
		this.totalAmount = placedOrdersEntity.getTotalPrice();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobileNo() {
		return customerMobileNo;
	}

	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}

	

	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public Boolean getIsRemovable() {
		return isRemovable;
	}

	public void setIsRemovable(Boolean isRemovable) {
		this.isRemovable = isRemovable;
	}

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public AmountDetails getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(AmountDetails amountDetails) {
		this.amountDetails = amountDetails;
	}
	
	
	

	@Override
	public String toString() {
		return "MerchantPurchaseData [customerName=" + customerName + ", customerMobileNo=" + customerMobileNo
				+ ", deliveryOptions=" + deliveryOptions + ", isRemovable=" + isRemovable + ", purchaseDate="
				+ purchaseDate + ", totalAmount=" + totalAmount + ", billNumber=" + billNumber + ", purchaseItems="
				+ purchaseItems + ", amountDetails=" + amountDetails + "]";
	}

	



}
