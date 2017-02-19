package com.archide.hsb.jsonmodel;

import com.archide.hsb.model.CookingCommentsEntity;

public class CookingCommentsJson {

	private String cookingComments;
	private long dateTime;
	private String cookingCommentsUUID;
	
	public CookingCommentsJson(CookingCommentsEntity commentsEntity){
		this.cookingComments = commentsEntity.getCookingComments();
		this.dateTime = commentsEntity.getDateTime();
		this.cookingCommentsUUID = commentsEntity.getCookingCommentsUUID();
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
	@Override
	public String toString() {
		return "CookingCommentsJson [cookingComments=" + cookingComments + ", dateTime=" + dateTime
				+ ", cookingCommentsUUID=" + cookingCommentsUUID + "]";
	}
	
	
}
