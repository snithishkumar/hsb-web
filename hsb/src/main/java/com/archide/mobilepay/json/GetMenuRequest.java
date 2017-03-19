package com.archide.mobilepay.json;

import com.archide.hsb.enumeration.AppType;
import com.archide.hsb.enumeration.OrderType;

public class GetMenuRequest {
	private long lastServerSyncTime;
	private String tableNumber;
	private AppType appType;
	private String mobileNumber;
	private OrderType orderType;
	public long getLastServerSyncTime() {
		return lastServerSyncTime;
	}
	public void setLastServerSyncTime(long lastServerSyncTime) {
		this.lastServerSyncTime = lastServerSyncTime;
	}
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	public AppType getAppType() {
		return appType;
	}
	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	@Override
	public String toString() {
		return "GetMenuRequest [lastServerSyncTime=" + lastServerSyncTime + ", tableNumber=" + tableNumber
				+ ", appType=" + appType + ", mobileNumber=" + mobileNumber + ", orderType=" + orderType + "]";
	}
	
	

}
