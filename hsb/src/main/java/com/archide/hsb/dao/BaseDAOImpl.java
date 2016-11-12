package com.archide.hsb.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDAOImpl {

	@Autowired
	protected SessionFactory sessionFactory;
	
	
	protected void saveObject(Object object){
		sessionFactory.getCurrentSession().save(object);
	}
	
	protected void updateObject(Object object){
		sessionFactory.getCurrentSession().update(object);
	}
	
	protected void batchInsert(List<Object> objects){
		for(Object object : objects){
			saveObject(object);
		}
	}
	
	protected <T> CriteriaQuery<T> getCriteriaQuery(Class<T> className) {
		return sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(className);
	}
	
	protected String appendAlias(String name1,String name2){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name1);
		stringBuilder.append(".");
		stringBuilder.append(name2);
		return stringBuilder.toString();
	}
}
