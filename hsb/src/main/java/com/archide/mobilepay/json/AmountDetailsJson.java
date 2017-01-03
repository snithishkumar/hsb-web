/*package com.archide.mobilepay.json;

import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson {
	
	private float taxAmount;
	private String discountAmount;
	private DiscountType discountType;
	private String minimumAmount;
	private String deliveryAmount;
	
	public AmountDetailsJson(PlacedOrdersEntity placedOrdersEntity){
		this.taxAmount = (float) placedOrdersEntity.getTaxAmount();
		this.discountAmount = "0";
		this.minimumAmount = "0";
		this.deliveryAmount = "0";
		this.discountType = DiscountType.NONE;
	}
	
	public float getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public DiscountType getDiscountType() {
		return discountType;
	}
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}
	public String getMinimumAmount() {
		return minimumAmount;
	}
	public void setMinimumAmount(String minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	public String getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	@Override
	public String toString() {
		return "AmountDetailsJson [taxAmount=" + taxAmount + ", discountAmount=" + discountAmount + ", discountType="
				+ discountType + ", minimumAmount=" + minimumAmount + ", deliveryAmount=" + deliveryAmount + "]";
	}
	
	

}
*/