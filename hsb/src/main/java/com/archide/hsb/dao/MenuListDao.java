package com.archide.hsb.dao;

import java.util.List;

import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.LoginUsersEntity;
import com.archide.hsb.model.MenuCourse;
import com.archide.hsb.model.MenuEntity;

public interface MenuListDao {
	
	List<MenuCourse> getMenuCourse();
	List<FoodCategory> getFoodCategory(MenuCourse menuCourse);
	List<MenuEntity> getMenuEntity(MenuCourse menuCourse,FoodCategory foodCategory);
	List<MenuEntity> getMenuEntity(MenuCourse menuCourse,FoodCategory foodCategory,long lastUpdatedTime);
    MenuEntity getMenuEntity(String menuUuid);
    
    void udpateMenuEntity(MenuEntity menuEntity);
    
    List<String> getUnAvailableMenus(long serverDateTime);
    
    boolean isLoggedIn(String mobileNumber);
    
    void createLoginUsers(LoginUsersEntity loginUsersEntity);
    
    LoginUsersEntity getLoginUsers(String mobileNumber);
    
    void deleteLoginUser(String mobileNumber);
    
    void deleteLoginUser(String mobileNumber,String tableNumber);
}
