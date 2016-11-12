package com.archide.hsb.dao;

import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrders;

public interface OrdersDao {

	void placeAnOrders(PlacedOrders placedOrders);
	
	PlacedOrders getPlacedOrders(String placeOrdersUuid);
	
	PlacedOrderItems getPlacedOrderItems(String placeOrderItemsUuid);
	
	void placeOrdersItems(PlacedOrderItems placedOrderItems);
}
