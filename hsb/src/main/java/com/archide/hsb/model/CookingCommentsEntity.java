package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cookingcomments")
public class CookingCommentsEntity {
	
	public static final String COOKING_COMMENTS_ID = "cookingCommentsId";
	public static final String COOKING_COMMENTS = "cookingComments";
	public static final String DATE_TIME = "dateTime";
	public static final String COOKING_COMMENTS_UUID = "cookingCommentsUUID";
	public static final String PLACED_ORDERS = "placedOrders";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CookingCommentsId")
	private int cookingCommentsId;
	@Column(name = "CookingComments",columnDefinition="TEXT")
	private String cookingComments;
	@Column(name = "DateTime")
	private long dateTime;
	@Column(name = "CookingCommentsUUID")
	private String cookingCommentsUUID;
	
	@ManyToOne
	@JoinColumn(name = "PlacedOrdersId")
	private PlacedOrdersEntity placedOrders;
	
	public int getCookingCommentsId() {
		return cookingCommentsId;
	}
	public void setCookingCommentsId(int cookingCommentsId) {
		this.cookingCommentsId = cookingCommentsId;
	}
	public String getCookingComments() {
		return cookingComments;
	}
	public void setCookingComments(String cookingComments) {
		this.cookingComments = cookingComments;
	}
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	public String getCookingCommentsUUID() {
		return cookingCommentsUUID;
	}
	public void setCookingCommentsUUID(String cookingCommentsUUID) {
		this.cookingCommentsUUID = cookingCommentsUUID;
	}
	
	
	public PlacedOrdersEntity getPlacedOrders() {
		return placedOrders;
	}
	public void setPlacedOrders(PlacedOrdersEntity placedOrders) {
		this.placedOrders = placedOrders;
	}
	@Override
	public String toString() {
		return "CookingCommentsEntity [cookingCommentsId=" + cookingCommentsId + ", cookingComments=" + cookingComments
				+ ", dateTime=" + dateTime + ", cookingCommentsUUID=" + cookingCommentsUUID + "]";
	}
	
	

}
