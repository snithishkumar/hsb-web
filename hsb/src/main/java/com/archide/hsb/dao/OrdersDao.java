package com.archide.hsb.dao;

import java.util.List;

import org.hibernate.Session;

import com.archide.hsb.model.CookingCommentsEntity;
import com.archide.hsb.model.DiscardEntity;
import com.archide.hsb.model.HistoryEntity;
import com.archide.hsb.model.LoginUsersEntity;
import com.archide.hsb.model.PaymentDetails;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.TableList;

public interface OrdersDao {

	void placeAnOrders(PlacedOrdersEntity placedOrders);

	PlacedOrdersEntity getPlacedOrders(String placeOrdersUuid);

	PlacedOrderItems getPlacedOrderItems(String placeOrderItemsUuid);

	void placeOrdersItems(PlacedOrderItems placedOrderItems);

	PlacedOrdersEntity getPlacedOrders(TableList tableList, String mobileNumber);

	List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders);

	List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders, long serverSyncTime);

	List<PlacedOrderItems> getPreviousPlacedOrderItems(PlacedOrdersEntity placedOrders, long serverLastUdpateTime);

	void saveHistory(HistoryEntity history);

	List<PlacedOrdersEntity> getPlacedOrders(List<String> orderIds);

	List<PlacedOrdersEntity> getPlacedOrders();

	void updateOrdersItems(PlacedOrderItems placedOrderItems);

	void ordersUpdate(PlacedOrdersEntity placedOrders);

	List<PlacedOrdersEntity> getPlacedOrdersByMobile(String userMobileNumber);

	void removePlacedOrderItems(PlacedOrdersEntity placedOrdersEntity);

	boolean isHistory(String orderId);

	List getUnPaiedListPurchaseUUids(Session session);

	PlacedOrdersEntity getPlacedOrdersEntity(String purchaseUUIDs, Session session);

	void savePaymentDetails(PaymentDetails paymentDetails);

	void saveDiscardEntity(DiscardEntity discardEntity);

	Session openSession();

	void closeSession(Session session);

	List<PlacedOrdersEntity> getPreviousDayOrders(Session session, long startOfDayInMilli);
	
	void saveCookingComments(CookingCommentsEntity commentsEntity);
	
	List<CookingCommentsEntity> getCookingComments(PlacedOrdersEntity placedOrders,long serverSyncTime);
	
	PlacedOrderItems getDeliveredItems(PlacedOrdersEntity placedOrdersEntity,String itemCode);
	
	void updates(List<Object> objectsList);
	
	List<PaymentDetails> getPaymentDetails(Session session,String purchaseUUID);
	
	List<CookingCommentsEntity> getCookingCommentsEntity(Session session,PlacedOrdersEntity placedOrdersEntity);
	
	List<DiscardEntity> getDiscardEntity(Session session,PaymentDetails paymentDetails);
	
	 List<LoginUsersEntity> getLoginUsersEntity(Session session , long time);
}
