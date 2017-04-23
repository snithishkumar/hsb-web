package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.archide.hsb.enumeration.FoodType;
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
	public static final String DISPLAY_ORDER = "displayOrder";
	public static final String TASTE_TYPE = "tasteType";
	public static final String DESCRIPTION = "description";
	public static final String MAX_COUNT = "maxCount";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MenuId")
	private int menuId;
	@Column(name = "MenuUuid")
	private String menuUUID;
	@Column(name = "MenuItemCode")
	private String menuItemCode;
	@Column(name = "Name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "FoodCategoryId",referencedColumnName="FoodCategoryId")
	private FoodCategory foodCategory;
	@ManyToOne
	@JoinColumn(name = "MenuCourseId",referencedColumnName = "MenuCourseId")
	private MenuCourse menuCourse;
	@Column(name = "Price")
	private double price;
	@Column(name = "Status")
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(name = "FoodType")
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
	
	@Column(name = "DateTime")
	private long dateTime;
	
	@Column(name = "ServerTime")
	private long serverTime;
	
	@Column(name = "DisplayOrder")
	private int displayOrder;
	
	@Column(name = "Description",columnDefinition="TEXT")
	private String description;
	
	@Column(name = "TasteType")
	private String tasteType;
	
	@Column(name = "MaxCount")
	private int maxCount;
	
	@Column(name = "CurrentCount")
	private int currentCount;
	
	@Column(name = "ShowingFrom")
	private int showingFrom;
	
	@Column(name = "ShowingTo")
	private int showingTo;

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

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

	public long getServerTime() {
		return serverTime;
	}

	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTasteType() {
		return tasteType;
	}

	public void setTasteType(String tasteType) {
		this.tasteType = tasteType;
	}
	
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getShowingFrom() {
		return showingFrom;
	}

	public void setShowingFrom(int showingFrom) {
		this.showingFrom = showingFrom;
	}

	public int getShowingTo() {
		return showingTo;
	}

	public void setShowingTo(int showingTo) {
		this.showingTo = showingTo;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	@Override
	public String toString() {
		return "MenuEntity [menuId=" + menuId + ", menuUUID=" + menuUUID + ", menuItemCode=" + menuItemCode + ", name="
				+ name + ", foodCategory=" + foodCategory + ", menuCourse=" + menuCourse + ", price=" + price
				+ ", status=" + status + ", foodType=" + foodType + ", dateTime=" + dateTime + ", serverTime="
				+ serverTime + ", displayOrder=" + displayOrder + ", description=" + description + ", tasteType="
				+ tasteType + ", maxCount=" + maxCount + ", currentCount=" + currentCount + ", showingFrom="
				+ showingFrom + ", showingTo=" + showingTo + "]";
	}

	
	
	

}
