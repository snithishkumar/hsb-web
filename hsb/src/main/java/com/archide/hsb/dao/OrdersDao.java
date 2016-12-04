package com.archide.hsb.dao;

import java.util.List;

import com.archide.hsb.model.History;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.TableList;

public interface OrdersDao {

	void placeAnOrders(PlacedOrdersEntity placedOrders);
	
	PlacedOrdersEntity getPlacedOrders(String placeOrdersUuid);
	
	
	PlacedOrderItems getPlacedOrderItems(String placeOrderItemsUuid);
	
	void placeOrdersItems(PlacedOrderItems placedOrderItems);
	
	PlacedOrdersEntity getPlacedOrders(TableList tableList,String mobileNumber);
	
	List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders);
	
	List<PlacedOrderItems> getPlacedOrderItems(PlacedOrdersEntity placedOrders,long serverSyncTime);
	
	List<PlacedOrderItems> getPreviousPlacedOrderItems(PlacedOrdersEntity placedOrders,long serverLastUdpateTime);
	
	void saveHistory(History history);
	
	List<PlacedOrdersEntity> getPlacedOrders(List<String> orderIds);
	
	List<PlacedOrdersEntity> getPlacedOrders();
	
	void updateOrdersItems(PlacedOrderItems placedOrderItems);
	
	void ordersUpdate(PlacedOrdersEntity placedOrders);
}
