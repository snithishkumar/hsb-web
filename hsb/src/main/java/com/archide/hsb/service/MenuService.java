package com.archide.hsb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.archide.hsb.dao.MenuListDao;
import com.archide.hsb.jsonmodel.FoodCategoryJson;
import com.archide.hsb.jsonmodel.MenuItemJson;
import com.archide.hsb.jsonmodel.MenuListJson;
import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.MenuCourse;
import com.archide.hsb.model.MenuEntity;

@Service
public class MenuService {
	
	private static final Logger logger = Logger.getLogger(MenuService.class);
	
	@Autowired
	private MenuListDao menuListDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	
	/**
	 * Get Table List
	 * @return
	 */
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getMenuDetails(String lastServerSyncTimeTemp){
		try{
			long lastServerSyncTime = 0;
			if(lastServerSyncTimeTemp != null){
				lastServerSyncTime = Long.valueOf(lastServerSyncTimeTemp);
			}
			List<MenuCourse> menuCourses = menuListDao.getMenuCourse();
			List<MenuListJson> menuListJsonList = new ArrayList<MenuListJson>();
			for(MenuCourse menuCourse : menuCourses){
				MenuListJson menuListJson = new MenuListJson(menuCourse);
				menuListJsonList.add(menuListJson);
				List<FoodCategory> foodCategories = menuListDao.getFoodCategory(menuCourse);
				for(FoodCategory foodCategory : foodCategories){
					FoodCategoryJson categoryJson = new FoodCategoryJson(foodCategory);
					List<MenuEntity> menuEntities = menuListDao.getMenuEntity(menuCourse, foodCategory,lastServerSyncTime);
					for(MenuEntity menuEntity : menuEntities){
						MenuItemJson menuItemJson = new MenuItemJson(menuEntity);
						categoryJson.getMenuItems().add(menuItemJson);
					}
				}
			}
			
			return serviceUtil.getRestResponse(true, menuListJsonList);
		}catch(Exception e){
			logger.error("Error in getMenuDetails",e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}

}
