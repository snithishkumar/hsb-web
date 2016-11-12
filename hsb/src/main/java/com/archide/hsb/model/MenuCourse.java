package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="menucourse")
public class MenuCourse {
	public static final String MENU_COURSE_ID = "menuCourseId";
	public static final String MENU_COURSE_UUID = "menuCourseUUID";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String DATE_TIME = "dateTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MenuCourseId")
	private int menuCourseId;
	@Column(name = "MenuCourseUuid")
	private String menuCourseUUID;
	@Column(name = "CategoryName")
	private String categoryName;
	@Column(name = "DateTime")
	private long dateTime;
	public int getMenuCourseId() {
		return menuCourseId;
	}
	public void setMenuCourseId(int menuCourseId) {
		this.menuCourseId = menuCourseId;
	}
	public String getMenuCourseUUID() {
		return menuCourseUUID;
	}
	public void setMenuCourseUUID(String menuCourseUUID) {
		this.menuCourseUUID = menuCourseUUID;
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
	@Override
	public String toString() {
		return "MenuCourse [menuCourseId=" + menuCourseId + ", menuCourseUUID=" + menuCourseUUID + ", categoryName="
				+ categoryName + ", dateTime=" + dateTime + "]";
	}
	
	

}
