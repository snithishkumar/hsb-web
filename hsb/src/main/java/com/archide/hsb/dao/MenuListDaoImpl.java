package com.archide.hsb.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.archide.hsb.enumeration.Status;
import com.archide.hsb.jsonmodel.MenuItemJson;
import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.MenuCourse;
import com.archide.hsb.model.MenuEntity;

@Repository
public class MenuListDaoImpl extends BaseDAOImpl implements MenuListDao{

	/**
	 * Get List of Menu
	 */
	public List<MenuCourse> getMenuCourse() {
		CriteriaQuery<MenuCourse> criteriaQuery = getCriteriaQuery(MenuCourse.class);
		Root<MenuCourse> root = criteriaQuery.from(MenuCourse.class);
		criteriaQuery.select(root);
		Query<MenuCourse> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		return q.getResultList();
	}

	/**
	 * Get List of Food Category based on Menu Course
	 */
	public List<FoodCategory> getFoodCategory(MenuCourse menuCourse) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<FoodCategory> criteriaQuery = builder.createQuery(FoodCategory.class);
		Root<FoodCategory> root = criteriaQuery.from(FoodCategory.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(FoodCategory.MENU_COURSE), menuCourse));
		Query<FoodCategory> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		return q.getResultList();
	}

	/**
	 * Get List of Menu items based on Menu course and Food Category
	 */
	public List<MenuEntity> getMenuEntity(MenuCourse menuCourse, FoodCategory foodCategory) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MenuEntity> criteriaQuery = builder.createQuery(MenuEntity.class);
		Root<MenuEntity> root = criteriaQuery.from(MenuEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.and(builder.equal(root.get(MenuEntity.MENU_COURSE), menuCourse),
				builder.equal(root.get(MenuEntity.FOOD_CATEGORY), foodCategory)));
		Query<MenuEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		return q.getResultList();
	}

	/**
	 * Get List of Menu items based on Menu course , Food Category and last updated date
	 */
	public List<MenuEntity> getMenuEntity(MenuCourse menuCourse, FoodCategory foodCategory, long lastUpdatedTime) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuEntity.class);
		criteria.add(Restrictions.eq(MenuEntity.FOOD_CATEGORY, foodCategory));
		criteria.add(Restrictions.eq(MenuEntity.MENU_COURSE, menuCourse));
		criteria.add(Restrictions.gt(MenuEntity.SERVER_TIME, lastUpdatedTime));
		return criteria.list();
	}

	/**
	 * Get MenuEntity based on menu Guid
	 */
	public MenuEntity getMenuEntity(String menuUuid) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MenuEntity> criteriaQuery = builder.createQuery(MenuEntity.class);
		Root<MenuEntity> root = criteriaQuery.from(MenuEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(MenuEntity.MENU_UUID), menuUuid));
		Query<MenuEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<MenuEntity> menuEntities =  q.getResultList();
		return menuEntities.size() > 0 ? menuEntities.get(0) : null;
	}
	
	@Override
	public List getUnAvailableMenus(long serverDateTime){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuEntity.class);
		criteria.add(Restrictions.eq(MenuEntity.STATUS, Status.UN_AVAILABLE));
		if(serverDateTime > 0){
			criteria.add(Restrictions.ge(MenuEntity.SERVER_TIME, serverDateTime));
		}
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property(MenuEntity.SERVER_TIME), MenuItemJson.SERVER_DATE_TIME);
		projectionList.add(Projections.property(MenuEntity.MENU_UUID), MenuItemJson.MENU_UUDI);
		
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return criteria.list();
	}

	@Override
	public void udpateMenuEntity(MenuEntity menuEntity) {
		updateObject(menuEntity);
	}

}
