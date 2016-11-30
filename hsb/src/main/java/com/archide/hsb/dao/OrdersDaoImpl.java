package com.archide.hsb.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.archide.hsb.enumeration.OrderStatus;
import com.archide.hsb.jsonmodel.GetKitchenOrders;
import com.archide.hsb.model.History;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.TableList;

@Repository
public class OrdersDaoImpl extends BaseDAOImpl implements OrdersDao {

	public void placeAnOrders(PlacedOrdersEntity placedOrders) {
		saveObject(placedOrders);
		
	}
	
	public void ordersUpdate(PlacedOrdersEntity placedOrders) {
		updateObject(placedOrders);
		
	}
	
	public void saveHistory(History history){
		saveObject(history);
	}

	public void placeOrdersItems(PlacedOrderItems placedOrderItems) {
		saveObject(placedOrderItems);		
	}
	
	public void updateOrdersItems(PlacedOrderItems placedOrderItems) {
		updateObject(placedOrderItems);		
	}

	public PlacedOrdersEntity getPlacedOrders(String placeOrdersUuid) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrdersEntity> criteriaQuery = builder.createQuery(PlacedOrdersEntity.class);
		Root<PlacedOrdersEntity> root = criteriaQuery.from(PlacedOrdersEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrdersEntity.PLACED_ORDERS_UUID), placeOrdersUuid));
		Query<PlacedOrdersEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrdersEntity> placeOrdersList =  q.getResultList();
		return placeOrdersList.size() > 0 ? placeOrdersList.get(0) : null;
	}

	public PlacedOrderItems getPlacedOrderItems(String placeOrderItemsUuid) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrderItems> criteriaQuery = builder.createQuery(PlacedOrderItems.class);
		Root<PlacedOrderItems> root = criteriaQuery.from(PlacedOrderItems.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrderItems.PLACED_ORDER_ITEMS_UUID), placeOrderItemsUuid));
		Query<PlacedOrderItems> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrderItems> placeOrderItemsList =  q.getResultList();
		return placeOrderItemsList.size() > 0 ? placeOrderItemsList.get(0) : null;
	}
	
	
	public PlacedOrdersEntity getPlacedOrders(TableList tableList) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrdersEntity> criteriaQuery = builder.createQuery(PlacedOrdersEntity.class);
		Root<PlacedOrdersEntity> root = criteriaQuery.from(PlacedOrdersEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrdersEntity.TABLE_NUMBER), tableList));
		Query<PlacedOrdersEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrdersEntity> placeOrdersList =  q.getResultList();
		return placeOrdersList.size() > 0 ? placeOrdersList.get(0) : null;
	}
	
	
	public List<PlacedOrdersEntity> getPlacedOrders(List<String> orderIds) {
		Criteria builder =  sessionFactory.getCurrentSession().createCriteria(PlacedOrdersEntity.class);
		builder.add(Restrictions.not(Restrictions.in(PlacedOrdersEntity.ORDER_ID, orderIds)));
		return builder.list();
	}
	
	public List<PlacedOrdersEntity> getPlacedOrders() {
		Criteria builder =  sessionFactory.getCurrentSession().createCriteria(PlacedOrdersEntity.class);
		return builder.list();
	}
	
	
	public List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrderItems> criteriaQuery = builder.createQuery(PlacedOrderItems.class);
		Root<PlacedOrderItems> root = criteriaQuery.from(PlacedOrderItems.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrderItems.PLACED_ORDERS), placedOrders));
		Query<PlacedOrderItems> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrderItems> placeOrderItemsList =  q.getResultList();
		return placeOrderItemsList;
	}
	
	
	public List<PlacedOrderItems> getPreviousPlacedOrderItems(PlacedOrdersEntity placedOrders,long serverLastUdpateTime) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrderItems> criteriaQuery = builder.createQuery(PlacedOrderItems.class);
		Root<PlacedOrderItems> root = criteriaQuery.from(PlacedOrderItems.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrderItems.PLACED_ORDERS), placedOrders),builder.and(builder.gt(root.get(PlacedOrderItems.SERVER_LAST_UPDATED_TIME), serverLastUdpateTime)));
		Query<PlacedOrderItems> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrderItems> placeOrderItemsList =  q.getResultList();
		return placeOrderItemsList;
	}


	@Override
	public List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders, long serverSyncTime) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrderItems> criteriaQuery = builder.createQuery(PlacedOrderItems.class);
		Root<PlacedOrderItems> root = criteriaQuery.from(PlacedOrderItems.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrderItems.PLACED_ORDERS), placedOrders),builder.and(builder.gt(root.get(PlacedOrderItems.SERVER_SYNC_TIME), serverSyncTime)));
		Query<PlacedOrderItems> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrderItems> placeOrderItemsList =  q.getResultList();
		return placeOrderItemsList;
	}
	
	

}
