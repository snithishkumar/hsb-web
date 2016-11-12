package com.archide.hsb.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.TableList;

@Repository
public class TableListDaoImpl extends BaseDAOImpl implements TableListDao{

	/**
	 * Returns list of tables
	 */
	public List<TableList> getTableList() {
		CriteriaQuery<TableList> criteriaQuery = getCriteriaQuery(TableList.class);
		Root<TableList> root = criteriaQuery.from(TableList.class);
		criteriaQuery.select(root);
		Query<TableList> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		return q.getResultList();
	}

	/**
	 * Get Tables based on tableNumber
	 */
	public TableList getTables(String tableNumber) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<TableList> criteriaQuery = builder.createQuery(TableList.class);
		Root<TableList> root = criteriaQuery.from(TableList.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(TableList.TABLE_NUMBER), tableNumber));
		Query<TableList> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<TableList> tableLists = q.getResultList();
		return tableLists.size() > 0 ? tableLists.get(0) : null;
	}

}
