package com.archide.hsb.jsonmodel;

import com.archide.hsb.model.MenuEntity;

public class KitchenMenuItems {
	private String menuUUID;
	private String menuItemCode;
	private String name;
	private String foodCategory;
	private String menuCourse;
	private int currentCount;
	private int maxCount;
	
	public KitchenMenuItems(MenuEntity menuEntity){
		this.menuUUID = menuEntity.getMenuUUID();
		this.menuItemCode = menuEntity.getMenuItemCode();
		this.name = menuEntity.getName();
		this.foodCategory = menuEntity.getFoodCategory().getCategoryName();
		this.currentCount = menuEntity.getCurrentCount();
		this.maxCount = menuEntity.getMaxCount();
	}

	
	public String getMenuUUID() {
		return menuUUID;
	}

	public void setMenuUUID(String menuUUID) {
		this.menuUUID = menuUUID;
	}

	public String getMenuItemCode() {
		return menuItemCode;
	}

	public void setMenuItemCode(String menuItemCode) {
		this.menuItemCode = menuItemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public String getMenuCourse() {
		return menuCourse;
	}

	public void setMenuCourse(String menuCourse) {
		this.menuCourse = menuCourse;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	public String toString() {
		return "KitchenMenuItems [ menuUUID=" + menuUUID + ", menuItemCode=" + menuItemCode
				+ ", name=" + name + ", foodCategory=" + foodCategory + ", menuCourse=" + menuCourse + ", currentCount="
				+ currentCount + ", maxCount=" + maxCount + "]";
	}

}
