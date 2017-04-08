package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.archide.hsb.enumeration.AppType;

@Entity
@Table(name = "ReservedTable")
public class ReservedTableEntity {
	
	public static final String TABLE_NUMBER = "tableNumber";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String IS_WAITING = "isWaiting";
	public static final String CREATED_DATE_TIME = "createdTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReservedTableId")
	private int reservedTableId;
	@Column(name = "TableNumber",unique = true)
	private String tableNumber;
	@Column(name = "MobileNumber")
	private String mobileNumber;
	@Column(name = "OrderId", unique = true)
	private String orderId;
	@Column(name = "CreatedTime")
	private long createdTime;
	@Column(name = "LastUpdatedTime")
	private long lastUpdatedTime;
	@Column(name = "IsWaiting")
	private boolean isWaiting;
	@Column(name = "AppType")
	@Enumerated(EnumType.STRING)
	private AppType appType;
	
	
	
	public AppType getAppType() {
		return appType;
	}
	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	public boolean isWaiting() {
		return isWaiting;
	}
	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}
	public int getReservedTableId() {
		return reservedTableId;
	}
	public void setReservedTableId(int reservedTableId) {
		this.reservedTableId = reservedTableId;
	}
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public long getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
	public long getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	@Override
	public String toString() {
		return "ReservedTableEntity [reservedTableId=" + reservedTableId + ", tableNumber=" + tableNumber
				+ ", mobileNumber=" + mobileNumber + ", orderId=" + orderId + ", createdTime=" + createdTime
				+ ", lastUpdatedTime=" + lastUpdatedTime + "]";
	}
	
	

}
