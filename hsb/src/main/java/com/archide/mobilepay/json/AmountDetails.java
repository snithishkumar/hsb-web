package com.archide.mobilepay.json;

import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.mobilepay.enumeration.DiscountType;

/**
 * @author Nithishkumar
 *
 */
public class AmountDetails {

	private double taxAmount;
	private DiscountType discountType;
	private double discountAmount;
	private double minimumAmount;
	private double deliveryAmount;
	
	public AmountDetails(PlacedOrdersEntity placedOrdersEntity){
		this.taxAmount = placedOrdersEntity.getTaxAmount();
		this.discountAmount = 0;
		this.minimumAmount = 0;
		this.deliveryAmount = 0;
		this.discountType = DiscountType.NONE;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	
	

	public double getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	
	
	@Override
	public String toString() {
		return "AmountDetails [taxAmount=" + taxAmount + ", discountType=" + discountType + ", discountAmount="
				+ discountAmount + ", minimumAmount=" + minimumAmount + "]";
	}

}

