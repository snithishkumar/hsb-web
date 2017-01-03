package com.archide.mobilepay.json;

import java.util.List;

import com.archide.hsb.model.DiscardEntity;
import com.archide.mobilepay.enumeration.DeliveryOptions;



public class HistoryPurchaseData extends CommonPurchaseData{
	
	private List<PurchaseItem> purchaseItem;
	private DeliveryOptions userDeliveryOptions;
	private double totalAmount;
	
	private DiscardEntity discardDetails;
	private CalculatedAmounts calculatedAmounts;
	
	
	
	public List<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}
	public void setPurchaseItem(List<PurchaseItem> purchaseItem) {
		this.purchaseItem = purchaseItem;
	}
	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}
	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public DiscardEntity getDiscardDetails() {
		return discardDetails;
	}
	public void setDiscardDetails(DiscardEntity discardDetails) {
		this.discardDetails = discardDetails;
	}
	public CalculatedAmounts getCalculatedAmounts() {
		return calculatedAmounts;
	}
	public void setCalculatedAmounts(CalculatedAmounts calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
	}
	@Override
	public String toString() {
		return "HistoryPurchaseData [purchaseItem=" + purchaseItem + ", userDeliveryOptions=" + userDeliveryOptions
				+ ", totalAmount=" + totalAmount + ", discardDetails=" + discardDetails + ", calculatedAmounts="
				+ calculatedAmounts + "]";
	}
	
	
	
	
	


}
