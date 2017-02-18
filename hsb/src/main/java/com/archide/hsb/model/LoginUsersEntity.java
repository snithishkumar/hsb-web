package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.archide.hsb.enumeration.UserType;



@Entity
@Table(name = "loginusers")
public class LoginUsersEntity {
	
	public static final String LOGIN_USER_ID = "loginUserId";
	public static final String TABLE_NUMBER = "tableNumber";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String CREATED_DATE_TIME = "createdDateTime";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LoginUserId")
	private int loginUserId;
	@Column(name = "TableNumber")
	private String tableNumber;
	@Column(name = "MobileNumber")
	private String mobileNumber;
	
	@Column(name = "CreatedDateTime")
	private long createdDateTime;
	
	@Column(name = "UserType")
	private UserType userType;
	
	public int getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
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
	
	
	public long getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "LoginUsersEntity [loginUserId=" + loginUserId + ", tableNumber=" + tableNumber + ", mobileNumber="
				+ mobileNumber + "]";
	}
	
	

}
