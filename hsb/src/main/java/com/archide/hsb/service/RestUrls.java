package com.archide.hsb.service;

import org.springframework.beans.factory.annotation.Value;

public class RestUrls {
	
	@Value("${webservice.url}")
	private String serverUrl;
	@Value("${webservice.token.access}")
	private String accessToken;
	@Value("${webservice.token.server}")
	private String serverToken;
	@Value("${webservice.url.createpurchase}")
	private String createPurchase;
	@Value("${webservice.url.getorderstatus}")
	private String orderStatus;
	@Value("${webservice.url.updateorderstatus}")
	private String updateOrderStatus;
	
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getServerToken() {
		return serverToken;
	}
	public void setServerToken(String serverToken) {
		this.serverToken = serverToken;
	}
	public String getCreatePurchase() {
		return createPurchase;
	}
	public void setCreatePurchase(String createPurchase) {
		this.createPurchase = createPurchase;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getUpdateOrderStatus() {
		return updateOrderStatus;
	}
	public void setUpdateOrderStatus(String updateOrderStatus) {
		this.updateOrderStatus = updateOrderStatus;
	}
	
	
	

}
