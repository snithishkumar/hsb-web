package com.archide.hsb.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.ReservedTableEntity;
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
	
	
	public TableList getTableList(String tableNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TableList.class);
		criteria.add(Restrictions.eq(TableList.TABLE_NUMBER, tableNumber));
		List<TableList> tableList = criteria.list();
		if(tableList.size() > 0){
			return tableList.get(0);
		}
		return null;
	}
	
	
	public List<String> getAvailableTableNumbers(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TableList.class);
		List<String> reservedTableList = getReservedTableList();
		if(reservedTableList.size() > 0){
			criteria.add(Restrictions.not(
					Restrictions.in(TableList.TABLE_NUMBER, reservedTableList)));
		}
		criteria.setProjection(Projections.property(TableList.TABLE_NUMBER));
		criteria.addOrder(Order.asc(TableList.TABLE_NUMBER));
		return criteria.list();
	}
	
	
	public boolean isReserved(String tableNumber){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReservedTableEntity.class);
		criteria.add(Restrictions.eq(ReservedTableEntity.TABLE_NUMBER, tableNumber));
		return  criteria.list().size() > 0;
	}
	
	public ReservedTableEntity getReservedTable(String tableNumber){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReservedTableEntity.class);
		criteria.add(Restrictions.eq(ReservedTableEntity.TABLE_NUMBER, tableNumber));
		List<ReservedTableEntity>  reservedTableEntities = criteria.list();
		if(reservedTableEntities.size() > 0){
			return reservedTableEntities.get(0);
		}
		return null;
	}
	
	public ReservedTableEntity getReservedTableByMobile(String mobileNumber,String tableNumber){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReservedTableEntity.class);
		if(tableNumber != null){
			criteria.add(Restrictions.eq(ReservedTableEntity.TABLE_NUMBER, tableNumber));
		}else if(mobileNumber != null){
			criteria.add(Restrictions.eq(ReservedTableEntity.MOBILE_NUMBER, mobileNumber));
			criteria.add(Restrictions.eq(ReservedTableEntity.IS_WAITING, true));
		}
		
		List<ReservedTableEntity>  reservedTableEntities = criteria.list();
		if(reservedTableEntities.size() > 0){
			return reservedTableEntities.get(0);
		}
		return null;
	}
	
	
	public void createReservedTableEntity(ReservedTableEntity reservedTableEntity){
		sessionFactory.getCurrentSession().save(reservedTableEntity);
	}
	
	public void updateReservedTableEntity(ReservedTableEntity reservedTableEntity){
		sessionFactory.getCurrentSession().update(reservedTableEntity);
	}
	
	
	public List<String> getReservedTableList(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReservedTableEntity.class);
		criteria.setProjection(Projections.property(ReservedTableEntity.TABLE_NUMBER));
		return criteria.list();
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
	
	
	public void deleteReservedTable(String tableNumber){
		Query query = sessionFactory.getCurrentSession().createQuery("delete ReservedTableEntity where tableNumber <= :tableNumber and orderId is null");
		query.setParameter("tableNumber", tableNumber);
		int result = query.executeUpdate();
	}
	

}
