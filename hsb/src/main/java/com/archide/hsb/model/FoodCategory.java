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
@Table(name="foodcategory")
public class FoodCategory {
	
	public static final String FOOD_CATEGORY_ID = "foodCategoryId";
	public static final String FOOD_CATEGORY_UUID = "foodCategoryUUID";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String DATE_TIME = "dateTime";
	public static final String MENU_COURSE = "menuCourse";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FoodCategoryId")
	private int foodCategoryId;
	@Column(name = "FoodCategoryUuid")
	private String foodCategoryUUID;
	@Column(name = "CategoryName")
	private String categoryName;
	@Column(name = "DateTime")
	private long dateTime;
	
	@ManyToOne
	@JoinColumn(name = "MenuCourseId", referencedColumnName = "MenuCourseId")
	private MenuCourse menuCourse;
	
	public int getFoodCategoryId() {
		return foodCategoryId;
	}
	public void setFoodCategoryId(int foodCategoryId) {
		this.foodCategoryId = foodCategoryId;
	}
	public String getFoodCategoryUUID() {
		return foodCategoryUUID;
	}
	public void setFoodCategoryUUID(String foodCategoryUUID) {
		this.foodCategoryUUID = foodCategoryUUID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	
	
	
	public MenuCourse getMenuCourse() {
		return menuCourse;
	}
	public void setMenuCourse(MenuCourse menuCourse) {
		this.menuCourse = menuCourse;
	}
	@Override
	public String toString() {
		return "FoodCategory [foodCategoryId=" + foodCategoryId + ", foodCategoryUUID=" + foodCategoryUUID
				+ ", categoryName=" + categoryName + ", dateTime=" + dateTime + "]";
	}
	
	

}
