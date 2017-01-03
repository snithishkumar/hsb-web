package com.archide.mobilepay.json;

public class CreatePurchaseResponse {

	private int statusCode;
	private String billNumber;
	private String purchaseUUID;
	private String message;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getPurchaseUUID() {
		return purchaseUUID;
	}
	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "CreatePurchaseResponse [statusCode=" + statusCode + ", billNumber=" + billNumber + ", purchaseUUID="
				+ purchaseUUID + ", message=" + message + "]";
	}
	
	
	
}
