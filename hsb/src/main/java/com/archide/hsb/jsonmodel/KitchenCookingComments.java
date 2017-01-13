package com.archide.hsb.jsonmodel;

import com.archide.hsb.model.CookingCommentsEntity;

public class KitchenCookingComments {
	
	private String kitchenCookingCommentsUUID;
    private long dateTime;
    private String comments;
    
    public KitchenCookingComments(CookingCommentsEntity cookingCommentsEntity){
    	this.kitchenCookingCommentsUUID = cookingCommentsEntity.getCookingCommentsUUID();
    	this.dateTime = cookingCommentsEntity.getDateTime();
    	this.comments = cookingCommentsEntity.getCookingComments();
    }
    
	public String getKitchenCookingCommentsUUID() {
		return kitchenCookingCommentsUUID;
	}
	public void setKitchenCookingCommentsUUID(String kitchenCookingCommentsUUID) {
		this.kitchenCookingCommentsUUID = kitchenCookingCommentsUUID;
	}
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "KitchenCookingComments [kitchenCookingCommentsUUID=" + kitchenCookingCommentsUUID + ", dateTime="
				+ dateTime + ", comments=" + comments + "]";
	}
    
    

}
