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
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.archide.hsb.enumeration.AppType;
import com.archide.hsb.enumeration.OrderStatus;
import com.archide.hsb.enumeration.OrderType;
import com.archide.hsb.enumeration.Status;
import com.archide.hsb.model.CookingCommentsEntity;
import com.archide.hsb.model.DiscardEntity;
import com.archide.hsb.model.HistoryEntity;
import com.archide.hsb.model.LoginUsersEntity;
import com.archide.hsb.model.MenuEntity;
import com.archide.hsb.model.PaymentDetails;
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
	
	public void updates(List<Object> objectsList){
		for(Object object : objectsList){
			updateObject(object);
		}
	}
	
	public void saveHistory(HistoryEntity history){
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
	
	public PlacedOrdersEntity getPlacedOrdersById(String ordersId) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrdersEntity> criteriaQuery = builder.createQuery(PlacedOrdersEntity.class);
		Root<PlacedOrdersEntity> root = criteriaQuery.from(PlacedOrdersEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(PlacedOrdersEntity.ORDER_ID), ordersId));
		Query<PlacedOrdersEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<PlacedOrdersEntity> placeOrdersList =  q.getResultList();
		return placeOrdersList.size() > 0 ? placeOrdersList.get(0) : null;
	}
	
	@Override
	public PlacedOrdersEntity getPlacedOrders(Session session,String placeOrdersUuid) {
		Criteria criteria = session.createCriteria(PlacedOrdersEntity.class);
		criteria.add(Restrictions.eq(PlacedOrdersEntity.PLACED_ORDERS_UUID, placeOrdersUuid));
		List<PlacedOrdersEntity> placeOrdersList = criteria.list();
		
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
	
	
	public PlacedOrderItems getDeliveredItems(PlacedOrdersEntity placedOrdersEntity,String itemCode){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PlacedOrderItems.class);
		criteria.add(Restrictions.eq(PlacedOrderItems.PLACED_ORDERS, placedOrdersEntity));
		criteria.add(Restrictions.eq(PlacedOrderItems.ORDER_STATUS, OrderStatus.DELIVERED));
		criteria.add(Restrictions.eq(PlacedOrderItems.ITEM_CODE, itemCode));
		List<PlacedOrderItems> placedOrderItems = criteria.list();
		if(placedOrderItems.size() > 0){
			return placedOrderItems.get(0);
		}
		return null;
	}
	
	@Override
	public PlacedOrdersEntity getPlacedOrders(TableList tableList, String userMobileNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PlacedOrdersEntity.class);
		criteria.add(Restrictions.eq(PlacedOrdersEntity.TABLE_NUMBER, tableList));
		if(userMobileNumber != null && !userMobileNumber.trim().isEmpty()){
			criteria.add(Restrictions.eq(PlacedOrdersEntity.USER_MOBILE_NUMBER, userMobileNumber));
		}
		
		criteria.addOrder(Order.desc(PlacedOrdersEntity.ORDER_DATE_TIME));
		criteria.setMaxResults(1);
		List<PlacedOrdersEntity> placeOrdersList = criteria.list();
		return placeOrdersList.size() > 0 ? placeOrdersList.get(0) : null;
	}
	
	
	@Override
	public List<PlacedOrdersEntity> getPlacedOrdersByMobile(String userMobileNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PlacedOrdersEntity.class);
		criteria.add(Restrictions.eq(PlacedOrdersEntity.USER_MOBILE_NUMBER, userMobileNumber));
		criteria.addOrder(Order.desc(PlacedOrdersEntity.ORDER_DATE_TIME));
		criteria.add(Restrictions.eq(PlacedOrdersEntity.IS_CLOSED, false));
		criteria.add(Restrictions.ne(PlacedOrdersEntity.ORDER_TYPE, OrderType.TakeAway));
		return criteria.list();
	}
	
	
	public List<PlacedOrdersEntity> getPlacedOrders(List<String> orderIds) {
		Criteria builder =  sessionFactory.getCurrentSession().createCriteria(PlacedOrdersEntity.class);
		builder.add(Restrictions.not(Restrictions.in(PlacedOrdersEntity.ORDER_ID, orderIds)));
		builder.add(Restrictions.eq(PlacedOrdersEntity.IS_CLOSED, false));
		return builder.list();
	}
	
	public List<PlacedOrdersEntity> getPlacedOrders() {
		Criteria builder =  sessionFactory.getCurrentSession().createCriteria(PlacedOrdersEntity.class);
		builder.add(Restrictions.eq(PlacedOrdersEntity.IS_CLOSED, false));
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
	
	
	public List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders,Session session) {
		Criteria builder =  session.createCriteria(PlacedOrderItems.class);
		builder.add(Restrictions.eq(PlacedOrderItems.PLACED_ORDERS, placedOrders));
		List<PlacedOrderItems> placeOrderItemsList =  builder.list();
		return placeOrderItemsList;
	}
	
	
	public List<PlacedOrderItems> getPreviousPlacedOrderItems(PlacedOrdersEntity placedOrders,long serverLastUdpateTime) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<PlacedOrderItems> criteriaQuery = builder.createQuery(PlacedOrderItems.class);
		Root<PlacedOrderItems> root = criteriaQuery.from(PlacedOrderItems.class);
		criteriaQuery.select(root);
		if(serverLastUdpateTime > 0){
			criteriaQuery.where(builder.equal(root.get(PlacedOrderItems.PLACED_ORDERS), placedOrders),builder.and(builder.gt(root.get(PlacedOrderItems.SERVER_SYNC_TIME), serverLastUdpateTime)));
		}else{
			criteriaQuery.where(builder.equal(root.get(PlacedOrderItems.PLACED_ORDERS), placedOrders));
		}
		
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
	
	@Override
	public List<CookingCommentsEntity> getCookingComments(PlacedOrdersEntity placedOrders,long dateTime){
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<CookingCommentsEntity> criteriaQuery = builder.createQuery(CookingCommentsEntity.class);
		Root<CookingCommentsEntity> root = criteriaQuery.from(CookingCommentsEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(CookingCommentsEntity.PLACED_ORDERS), placedOrders),builder.and(builder.gt(root.get(CookingCommentsEntity.DATE_TIME), dateTime)));
		Query<CookingCommentsEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<CookingCommentsEntity> cookingCommentsList =  q.getResultList();
		return cookingCommentsList;
	}
	
	
	@Override
	public boolean isHistory(String orderId) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<HistoryEntity> criteriaQuery = builder.createQuery(HistoryEntity.class);
		Root<HistoryEntity> root = criteriaQuery.from(HistoryEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get(HistoryEntity.ORDER_ID), orderId));
		Query<HistoryEntity> q = sessionFactory.getCurrentSession().createQuery(criteriaQuery);
		List<HistoryEntity> placeOrderItemsList =  q.getResultList();
		return placeOrderItemsList.size() > 0;
	}
	
	
	public void removePlacedOrderItems(PlacedOrdersEntity placedOrdersEntity){
		Query query = sessionFactory.getCurrentSession().createQuery("delete PlacedOrderItems where placedOrders = :ID");
		query.setParameter("ID", placedOrdersEntity);
		int result = query.executeUpdate();
		removePlacedOrders(placedOrdersEntity);
	}
	
	
	public List getUnPaiedListPurchaseUUids(Session session){
	  Criteria criteria =	session.createCriteria(PlacedOrdersEntity.class);
	  criteria.add(Restrictions.isNull(PlacedOrdersEntity.PAYMENT_STATUS));
	  criteria.add(Restrictions.isNotNull(PlacedOrdersEntity.PURCHASE_UUID));
	  criteria.setProjection(Projections.property(PlacedOrdersEntity.PURCHASE_UUID));
	  criteria.setResultTransformer(Transformers.TO_LIST);
	  return  criteria.list();
	}
	
	public List<PlacedOrdersEntity> getPreviousDayOrders(Session session,long startOfDayInMilli){
		Criteria criteria = session.createCriteria(PlacedOrdersEntity.class);
		criteria.add(Restrictions.isNotNull(PlacedOrdersEntity.PAYMENT_STATUS));
		//criteria.add(Restrictions.isNotNull(PlacedOrdersEntity.PURCHASE_UUID));
		criteria.add(Restrictions.lt(PlacedOrdersEntity.SERVER_DATE_TIME, startOfDayInMilli));
		criteria.add(Restrictions.eq(PlacedOrdersEntity.IS_CLOSED, true));
		return criteria.list();
	}
	
	public List<PaymentDetails> getPaymentDetails(Session session,String purchaseUUID){
		Criteria criteria = session.createCriteria(PaymentDetails.class);
		criteria.add(Restrictions.eq(PaymentDetails.PURCHASE_UUID, purchaseUUID));
		return criteria.list();
	}
	
	public List<DiscardEntity> getDiscardEntity(Session session,PaymentDetails paymentDetails){
		Criteria criteria = session.createCriteria(DiscardEntity.class);
		criteria.add(Restrictions.eq(DiscardEntity.PAYMENT_DETAILS, paymentDetails));
		return criteria.list();
	}
	
	
	public List<CookingCommentsEntity> getCookingCommentsEntity(Session session,PlacedOrdersEntity placedOrdersEntity){
		Criteria criteria = session.createCriteria(CookingCommentsEntity.class);
		criteria.add(Restrictions.eq(CookingCommentsEntity.PLACED_ORDERS, placedOrdersEntity));
		return criteria.list();
	}
	
	public Session openSession(){
		return sessionFactory.openSession();
	}
	
	public void closeSession(Session session){
		if(session != null){
			session.close();
		}
	}
	
	
	public PlacedOrdersEntity getPlacedOrdersEntity(String purchaseUUIDs,Session session){
		Criteria criteria =	session.createCriteria(PlacedOrdersEntity.class);
		criteria.add(Restrictions.eq(PlacedOrdersEntity.PURCHASE_UUID, purchaseUUIDs));
		List<PlacedOrdersEntity> placedOrdersList = criteria.list();
		if(placedOrdersList.size() > 0){
			return placedOrdersList.get(0);
		}
		return null;
	}
	
	
	public void savePaymentDetails(PaymentDetails paymentDetails){
		saveObject(paymentDetails);
	}
	
	public void saveCookingComments(CookingCommentsEntity commentsEntity){
		saveObject(commentsEntity);
	}
	
	
	public void saveDiscardEntity(DiscardEntity discardEntity){
		saveObject(discardEntity);
	}
	
	
	private void removePlacedOrders(PlacedOrdersEntity placedOrdersEntity){
		sessionFactory.getCurrentSession().delete(placedOrdersEntity);
	}
	
	
	public List<LoginUsersEntity> getLoginUsersEntity(Session session , long time){
		Criteria criteria =	session.createCriteria(LoginUsersEntity.class);
		criteria.add(Restrictions.le(LoginUsersEntity.CREATED_DATE_TIME, time));
		return criteria.list();
	}
	
	
	public void deleteReservedTable(Session session , long time, AppType appType){
		Query query = session.createQuery("delete ReservedTableEntity where createdTime <= :createdTime and orderId is null and appType = :appType");
		query.setParameter("createdTime", time);
		query.setParameter("appType", appType);
		int result = query.executeUpdate();
		System.out.println(result);
	}
	
	
	@Override
	public PlacedOrdersEntity getPlacedOrders(Session session,String mobileNumber,String tableNumber) {
		Criteria criteria = session.createCriteria(PlacedOrdersEntity.class);
		criteria.add(Restrictions.eq(PlacedOrdersEntity.USER_MOBILE_NUMBER, mobileNumber));
		criteria.add(Restrictions.eq(PlacedOrdersEntity.TABLE_NUMBER, tableNumber));
		List<PlacedOrdersEntity> placeOrdersList = criteria.list();
		
		return placeOrdersList.size() > 0 ? placeOrdersList.get(0) : null;
	}
	
	public int updateCurrentCount(String menuUUID,long serverDateTime,int quantity){
		Query query = sessionFactory.getCurrentSession().createQuery("update MenuEntity set currentCount = currentCount + "+ quantity+", serverTime =:serverTime  where ( currentCount + "+ quantity+" ) <= maxCount and menuUUID =:menuUUID");
		query.setParameter("menuUUID", menuUUID);
		query.setParameter("serverTime", serverDateTime);
		int result = query.executeUpdate();
		return result;
	}
	
	
	public int updateOrderStatus(String menuUUID,long serverDateTime){
		Query query = sessionFactory.getCurrentSession().createQuery("update MenuEntity set status =:status , serverTime =:serverTime  where maxCount <= currentCount  and menuUUID =:menuUUID");
		query.setParameter("menuUUID", menuUUID);
		query.setParameter("serverTime", serverDateTime);
		query.setParameter("status", Status.UN_AVAILABLE);
		int result = query.executeUpdate();
		return result;
	}
	
	
	public Integer getUnAvailableCount(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuEntity.class);
		criteria.add(Restrictions.le(MenuEntity.MAX_COUNT, 15));
		criteria.setProjection(Projections.rowCount());
		return (int)(long)(Long)criteria.uniqueResult();
	}
	
	

}
