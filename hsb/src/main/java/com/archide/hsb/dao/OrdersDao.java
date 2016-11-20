package com.archide.hsb.dao;

import java.util.List;

import com.archide.hsb.model.History;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrders;
import com.archide.hsb.model.TableList;

public interface OrdersDao {

	void placeAnOrders(PlacedOrders placedOrders);
	
	PlacedOrders getPlacedOrders(String placeOrdersUuid);
	
	PlacedOrderItems getPlacedOrderItems(String placeOrderItemsUuid);
	
	void placeOrdersItems(PlacedOrderItems placedOrderItems);
	
	PlacedOrders getPlacedOrders(TableList tableList);
	
	List<PlacedOrderItems> getPlacedOrderItems(PlacedOrders placedOrders);
	void saveHistory(History history);
}
