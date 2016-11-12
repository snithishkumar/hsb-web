package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.archide.hsb.enumeration.Status;


@Entity
@Table(name="menuentity")
public class MenuEntity {
	
	public static final String MENU_ID = "menuId";
	public static final String MENU_UUID= "menuUUID";
	public static final String MENU_ITEM_CODE= "menuItemCode";
	public static final String NAME= "name";
	public static final String FOOD_CATEGORY= "foodCategory";
	public static final String MENU_COURSE= "menuCourse";
	public static final String PRICE= "price";
	public static final String STATUS= "status";
	public static final String DATE_TIME= "dateTime";
	public static final String SERVER_TIME = "serverTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MenuId")
	private int menuId;
	@Column(name = "MenuUuid")
	private String menuUUID;
	@Column(name = "MenuItemCode")
	private String menuItemCode;
	@Column(name = "Name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "FoodCategoryId", referencedColumnName = "FoodCategoryId")
	private FoodCategory foodCategory;
	@ManyToOne
	@JoinColumn(name = "MenuCourseId", referencedColumnName = "MenuCourseId")
	private MenuCourse menuCourse;
	@Column(name = "Price")
	private double price;
	@Column(name = "Status")
	@Enumerated
	private Status status;
	
	@Column(name = "DateTime")
	private long dateTime;
	
	@Column(name = "ServerTime")
	private long serverTime;
	
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
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
	public FoodCategory getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(FoodCategory foodCategory) {
		this.foodCategory = foodCategory;
	}
	public MenuCourse getMenuCourse() {
		return menuCourse;
	}
	public void setMenuCourse(MenuCourse menuCourse) {
		this.menuCourse = menuCourse;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "MenuEntity [menuId=" + menuId + ", menuUUID=" + menuUUID + ", menuItemCode=" + menuItemCode + ", name="
				+ name + ", foodCategory=" + foodCategory + ", menuCourse=" + menuCourse + ", price=" + price
				+ ", status=" + status + "]";
	}
	
	

}
