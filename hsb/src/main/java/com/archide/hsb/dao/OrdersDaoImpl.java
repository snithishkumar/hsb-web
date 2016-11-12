package com.archide.hsb.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrders;

@Repository
public class OrdersDaoImpl extends BaseDAOImpl implements OrdersDao {

	public void placeAnOrders(PlacedOrders placedOrders) {
		saveObject(placedOrders);
		
	}

	public void placeOrdersItems(PlacedOrderItems placedOrderItems) {
		saveObject(placedOrderItems);		
	}

	public PlacedOrders getPlacedOrders(String placeOrdersUuid) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrders> criteriaQuery = builder.createQuery(PlacedOrders.class);
		Root<PlacedOrders> root = criteriaQuery.from(PlacedOrders.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrders.PLACED_ORDERS_UUID), placeOrdersUuid));
		Query<PlacedOrders> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrders> placeOrdersList =  q.getResultList();
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

}
