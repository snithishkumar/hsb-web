package com.archide.hsb.service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.archide.hsb.dao.MenuListDao;
import com.archide.hsb.dao.OrdersDao;
import com.archide.hsb.dao.TableListDao;
import com.archide.hsb.enumeration.OrderStatus;
import com.archide.hsb.enumeration.OrderType;
import com.archide.hsb.enumeration.Status;
import com.archide.hsb.jsonmodel.CookingCommentsJson;
import com.archide.hsb.jsonmodel.FoodCategoryJson;
import com.archide.hsb.jsonmodel.GetKitchenOrders;
import com.archide.hsb.jsonmodel.HistoryMenuItem;
import com.archide.hsb.jsonmodel.KitchenCookingComments;
import com.archide.hsb.jsonmodel.KitchenOrderListResponse;
import com.archide.hsb.jsonmodel.KitchenOrderStatusSyncResponse;
import com.archide.hsb.jsonmodel.MenuItemJson;
import com.archide.hsb.jsonmodel.MenuListJson;
import com.archide.hsb.jsonmodel.OrderedMenuItems;
import com.archide.hsb.jsonmodel.PlaceOrdersJson;
import com.archide.hsb.jsonmodel.ResponseData;
import com.archide.hsb.jsonmodel.UnAvailableMenuDetails;
import com.archide.hsb.model.CookingCommentsEntity;
import com.archide.hsb.model.DiscardEntity;
import com.archide.hsb.model.FoodCategory;
import com.archide.hsb.model.HistoryDetailsEntity;
import com.archide.hsb.model.HistoryEntity;
import com.archide.hsb.model.MenuCourse;
import com.archide.hsb.model.MenuEntity;
import com.archide.hsb.model.PaymentDetails;
import com.archide.hsb.model.PaymentDetailsHistory;
import com.archide.hsb.model.PlacedOrderItems;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.ReservedTableEntity;
import com.archide.hsb.model.TableList;
import com.archide.mobilepay.enumeration.PaymentStatus;
import com.archide.mobilepay.exception.ValidationException;
import com.archide.mobilepay.json.AmountDetails;
import com.archide.mobilepay.json.CreatePurchaseResponse;
import com.archide.mobilepay.json.HistoryPurchaseData;
import com.archide.mobilepay.json.MerchantPurchaseData;
import com.archide.mobilepay.json.OrderStatusUpdate;
import com.archide.mobilepay.json.PurchaseItem;
import com.archide.mobilepay.json.PurchaseStatus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


@Service
public class OrdersService {
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private TableListDao tableListDao;
	
	@Autowired
	private MenuListDao menuListDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Gson gson;
	@Autowired
	private JsonParser jsonParser;
	@Autowired
	private RestUrls restUrls;
	
	private static final Logger logger = Logger.getLogger(OrdersService.class);
	
	
	
	
	private List<MenuListJson> getMenuDetails(){
		List<MenuCourse> menuCourses = menuListDao.getMenuCourse();
		List<MenuListJson> menuListJsonList = new ArrayList<MenuListJson>();
		for(MenuCourse menuCourse : menuCourses){
			MenuListJson menuListJson = new MenuListJson(menuCourse);
			menuListJsonList.add(menuListJson);
			List<FoodCategory> foodCategories = menuListDao.getFoodCategory(menuCourse);
			for(FoodCategory foodCategory : foodCategories){
				FoodCategoryJson categoryJson = new FoodCategoryJson(foodCategory);
				menuListJson.getCategoryJsons().add(categoryJson);
				List<MenuEntity> menuEntities = menuListDao.getMenuEntity(menuCourse, foodCategory,0);
				for(MenuEntity menuEntity : menuEntities){
					MenuItemJson menuItemJson = new MenuItemJson(menuEntity);
					categoryJson.getMenuItems().add(menuItemJson);
				}
			}
		}
		return menuListJsonList;
	}
	
	private String generateOrderId(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String orderId = dateFormat.format(date);
		int ran = RandomUtils.nextInt(1, 100);
		orderId = orderId + ran;
		return orderId;
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public ResponseEntity<String> placeAnOrder(String requestData,UnAvailableMenuDetails unAvailableMenuDetails)throws Exception{
		PlaceOrdersJson placeOrdersJson = serviceUtil.fromJson(requestData, PlaceOrdersJson.class);
		ReservedTableEntity reservedTableEntity = null;
		if(placeOrdersJson != null){
			PlacedOrdersEntity placedOrders = null;
			if(placeOrdersJson.getOrderType().toString().equals(OrderType.Dinning.toString())){
			    reservedTableEntity = tableListDao.getReservedTableByMobile(placeOrdersJson.getUserMobileNumber(), placeOrdersJson.getTableNumber());
				if(reservedTableEntity.getOrderId() == null){
				    placedOrders =  new PlacedOrdersEntity(placeOrdersJson);
				    placedOrders.setTotalPrice(0);
				    placedOrders.setPrice(0);
				    String orderId = generateOrderId();
				    if(reservedTableEntity.isWaiting()){
				    	 orderId = "DW"+orderId;
				    }else{
				    	orderId = "D"+orderId;
				    }
				    placedOrders.setOrderId(orderId);
					TableList tableList =	tableListDao.getTables(placeOrdersJson.getTableNumber());
					 placedOrders.setTableNumber(tableList);
					 ordersDao.placeAnOrders(placedOrders);
					// New Order
				}else{
				     placedOrders =  ordersDao.getPlacedOrdersById(reservedTableEntity.getOrderId());
					// Update Existing order
				}
			}else{
				 placedOrders =  new PlacedOrdersEntity(placeOrdersJson);
				 placedOrders.setTotalPrice(0);
				 placedOrders.setPrice(0);
				 String orderId = generateOrderId();
				 orderId = "TA"+orderId;
				 placedOrders.setOrderId(orderId);  
				    
					TableList tableList =	tableListDao.getTables(placeOrdersJson.getTableNumber());
					 placedOrders.setTableNumber(tableList);
					 ordersDao.placeAnOrders(placedOrders);
			}
			
			
			
			double totalAmount = placedOrders.getTotalPrice();
			placedOrders.setServerDateTime(ServiceUtil.getCurrentGmtTime());
			placedOrders.setLastUpdatedDateTime(placedOrders.getServerDateTime());
			
			CookingCommentsEntity cookingComments = new CookingCommentsEntity();
			cookingComments.setCookingComments(placeOrdersJson.getComments());
			cookingComments.setCookingCommentsUUID(ServiceUtil.uuid());
			cookingComments.setDateTime(ServiceUtil.getCurrentGmtTime());
			cookingComments.setPlacedOrders(placedOrders);
			ordersDao.saveCookingComments(cookingComments);
			
			List<OrderedMenuItems> menuItemsList = placeOrdersJson.getMenuItems();
			for(OrderedMenuItems menuItems : menuItemsList){
				MenuEntity menuEntity = menuListDao.getMenuEntity(menuItems.getMenuUuid());
				if(menuEntity != null){
					PlacedOrderItems placedOrderItems = ordersDao.getPlacedOrderItems(menuItems.getPlacedOrderItemsUUID());
					if(placedOrderItems == null){
						if(menuEntity.getStatus().toString().equals(Status.UN_AVAILABLE.toString())){
							unAvailableMenuDetails.getUnAvailableMenuDetails().add(menuItems.getPlacedOrderItemsUUID());
						} else {
							int res = ordersDao.updateCurrentCount(menuEntity.getMenuUUID(),
									placedOrders.getServerDateTime(), menuItems.getQuantity());
							if (res > 0) {
								placedOrderItems = new PlacedOrderItems();
								placedOrderItems.setQuantity(menuItems.getQuantity());
								placedOrderItems.setMenuItem(menuEntity);
								placedOrderItems.setTableNumber(placeOrdersJson.getTableNumber());
								placedOrderItems.setName(menuItems.getName());
								placedOrderItems.setItemCode(menuItems.getItemCode());
								placedOrderItems.setPlacedOrderItemsUUID(menuItems.getPlacedOrderItemsUUID());
								placedOrderItems.setPlacedOrders(placedOrders);
								placedOrderItems.setLastUpdatedTime(placedOrders.getServerDateTime());
								placedOrderItems.setOrderDateTime(placedOrders.getServerDateTime());
								placedOrderItems.setServerSyncTime(placedOrders.getServerDateTime());
								placedOrderItems.setOrderStatus(menuItems.getOrderStatus());
								placedOrderItems.setFoodCategoryName(menuEntity.getFoodCategory().getCategoryName());
								placedOrderItems.setMenuCourseName(menuEntity.getMenuCourse().getCategoryName());
								
								double amt = serviceUtil.roundOff(menuItems.getQuantity() * menuEntity.getPrice());
								totalAmount = totalAmount + amt;
								ordersDao.updateOrderStatus(menuEntity.getMenuUUID(), placedOrders.getServerDateTime());
								ordersDao.placeOrdersItems(placedOrderItems);
							} else {
								unAvailableMenuDetails.getUnAvailableMenuDetails()
										.add(menuItems.getPlacedOrderItemsUUID());
							}
						}
						
						
					}
					
				}
			}
			
				placedOrders.setServerDateTime(ServiceUtil.getCurrentGmtTime());
				double amt = serviceUtil.roundOff(totalAmount);
				placedOrders.setTotalPrice(amt);
				placedOrders.setPrice(amt);
				placedOrders.setLastUpdatedDateTime(placedOrders.getServerDateTime());
				ordersDao.ordersUpdate(placedOrders);
				if(reservedTableEntity != null){
					reservedTableEntity.setOrderId(placedOrders.getOrderId());
					if(reservedTableEntity.getMobileNumber() == null){
						reservedTableEntity.setMobileNumber(placedOrders.getUserMobileNumber());
					}
					tableListDao.updateReservedTableEntity(reservedTableEntity);
				}
				
				
			if(unAvailableMenuDetails.getUnAvailableMenuDetails().size() > 0){
				List<MenuListJson> menuListJsons = getMenuDetails();
				unAvailableMenuDetails.setMenuListJsonList(menuListJsons);
				throw new Exception("Some of ordered Items are unavailable");
			}else{
				if(placedOrders.getOrderType().toString().equals(OrderType.TakeAway.toString())){
					try{
						sendTakeAwayOrder(placedOrders);
						return serviceUtil.getRestResponse(true,placedOrders.getServerDateTime(),201);
					}catch(ValidationException e){
						return serviceUtil.getRestResponse(false,e.getMessage(),e.getCode());
					}
					
				}
				return serviceUtil.getRestResponse(true,placedOrders.getServerDateTime(),200);
				
			}
		}
		throw new ValidationException(500,"Invalid data");
		
	}
	
	@Transactional(rollbackFor = ValidationException.class,propagation=Propagation.REQUIRED,readOnly=false)
	public ResponseEntity<String> closeAnOrder(String tableNumber,String mobileNumber,String orderUUID) throws ValidationException{
		 return generateBilling(tableNumber, mobileNumber,orderUUID);
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardData(String placedOrderUUID){
		try{
			PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(placedOrderUUID);
			OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate();
			orderStatusUpdate.setDeclineReason("Paid by Cash");
			orderStatusUpdate.setOrderStatus(com.archide.mobilepay.enumeration.OrderStatus.CANCELLED);
			orderStatusUpdate.setPurchaseUUID(placedOrdersEntity.getPurchaseUUID());
			//com.archide.mobilepay.json.OrderStatusUpdate 
			
			List<OrderStatusUpdate> orderStatusUpdates = new ArrayList<>();
			orderStatusUpdates.add(orderStatusUpdate);
			
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			//headers.add("Authorization", "Basic " + base64Creds);
			headers.add("content-type", "application/json");
			headers.add("mobilePay", "merchant");
			headers.add("accessToken", restUrls.getAccessToken());
			headers.add("serverToken", restUrls.getServerToken());
			HttpEntity<List<OrderStatusUpdate>> request = new HttpEntity<List<OrderStatusUpdate>>(orderStatusUpdates, headers);
			String response = restTemplate.postForObject(restUrls.getServerUrl()+"/"+restUrls.getOrderStatus(), request, String.class);
			ResponseData responseData = gson.fromJson(response, ResponseData.class);
			if (responseData.getStatusCode() == 200) {
				return serviceUtil.getRestResponse(true,"success",200);
			}
		}catch(Exception e){
			logger.error("Error in discardData,Params["+placedOrderUUID+"]", e);
		}
		return serviceUtil.getRestResponse(true, "Invalid data",500);
		
	}
	
	private void sendData(PlacedOrdersEntity placedOrdersEntity,List<PurchaseItem> purchaseItems) throws ValidationException{
		MerchantPurchaseData purchaseData = new MerchantPurchaseData(placedOrdersEntity);
		AmountDetails amountDetails = new AmountDetails(placedOrdersEntity);
		purchaseData.setAmountDetails(amountDetails);
		purchaseData.setPurchaseItems(purchaseItems);
		
		List<MerchantPurchaseData> merchantPurchaseDatas = new ArrayList<>();
		merchantPurchaseDatas.add(purchaseData);
	//	String dataJson = gson.toJson(merchantPurchaseDatas);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		//headers.add("Authorization", "Basic " + base64Creds);
		headers.add("content-type", "application/json");
		headers.add("mobilePay", "merchant");
		headers.add("accessToken", restUrls.getAccessToken());
		headers.add("serverToken", restUrls.getServerToken());
		HttpEntity<List<MerchantPurchaseData>> request = new HttpEntity<List<MerchantPurchaseData>>(merchantPurchaseDatas, headers);
		
		String response = restTemplate.postForObject(restUrls.getServerUrl()+"/"+restUrls.getCreatePurchase(), request, String.class);
		
		ResponseData responseData = gson.fromJson(response, ResponseData.class);
		
		logger.info("------MobilePay Sync Start----------");
		logger.info(response);
		logger.info("------MobilePay Sync End----------");
		if (responseData.getStatusCode() == 200) {
			String responseString =  gson.toJson(responseData.getData());
			Type listType = new TypeToken<ArrayList<CreatePurchaseResponse>>() {
			}.getType();
			List<CreatePurchaseResponse> createPurchaseResponses = gson.fromJson(responseString.toString(), listType);
			CreatePurchaseResponse createPurchaseResponse = createPurchaseResponses.get(0);
			if (createPurchaseResponse.getStatusCode() == 200) {
				placedOrdersEntity.setPurchaseUUID(createPurchaseResponse.getPurchaseUUID());
			} else {
				throw new ValidationException(createPurchaseResponse.getStatusCode(),
						createPurchaseResponse.getMessage());
			}
		}else{
			String responseString = (String) responseData.getData();
			throw new ValidationException(responseData.getStatusCode(),
					responseString);
		}
	}
	
	
	private void sendTakeAwayOrder(PlacedOrdersEntity placedOrdersEntity) throws ValidationException{
		List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrdersEntity);
		List<PurchaseItem> purchaseDetails = new ArrayList<>();
		for (PlacedOrderItems orderItems : placedOrderItemsList) {
			PurchaseItem purchaseItem = new PurchaseItem(orderItems);
			purchaseDetails.add(purchaseItem);
		}
		sendData(placedOrdersEntity, purchaseDetails);
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> reSentBillDetails(String placedOrderUUID){
		try{
			PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(placedOrderUUID);
			List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrdersEntity);
			List<PurchaseItem> purchaseDetails = new ArrayList<>();
			int statusCode = 600;
			for (PlacedOrderItems orderItems : placedOrderItemsList) {
				PurchaseItem purchaseItem = new PurchaseItem(orderItems);
				purchaseDetails.add(purchaseItem);
			}
			
			try {
				sendData(placedOrdersEntity, purchaseDetails);
			} catch (ValidationException e) {
				statusCode = e.getCode() == 500 ? 602 : 601;
				e.printStackTrace();
			}
			return serviceUtil.getRestResponse(true, "Success",statusCode);
		}catch(Exception e){
			logger.error("Error in reSentBillDetails,Params["+placedOrderUUID+"]",e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.",500);
		
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
	public ResponseEntity<String> removeLoginedUser(String mobileNumber,String tableNumber){
		try{
			List<PlacedOrdersEntity> placedOrdersEntities =	ordersDao.getPlacedOrdersByMobile(mobileNumber);
			if(placedOrdersEntities.size() < 1){
				menuListDao.deleteLoginUser(mobileNumber, tableNumber);
			}
		}catch(Exception e){
			logger.error("Error in removeLoginedUser,mobileNumber["+mobileNumber+"],tableNumber["+tableNumber+"]", e);
		}
		return serviceUtil.getRestResponse(true, "Success.",200);
	}
	
	
	private ResponseEntity<String> generateBilling(String tableNumber,String mobileNumber,String orderUUID)throws ValidationException{
		PlacedOrdersEntity closingPlacedOrder = ordersDao.getPlacedOrders(orderUUID);
		if(closingPlacedOrder == null || closingPlacedOrder.isClosed()){
			return serviceUtil.getRestResponse(true, "Already in History",404);
		}
		
		List<PlacedOrdersEntity> placedOrdersList = ordersDao.getPlacedOrdersByMobile(mobileNumber);
		
		for(PlacedOrdersEntity placedOrdersEntity : placedOrdersList){
			boolean isClosingPlacedOrder = placedOrdersEntity.getPlaceOrdersId() == closingPlacedOrder.getPlaceOrdersId();
			if(isClosingPlacedOrder){
				continue;
			}
			List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrdersEntity);
			for (PlacedOrderItems orderItems : placedOrderItemsList) {
				if (orderItems.getOrderStatus().toString().equals(OrderStatus.ORDERED.toString())
						|| orderItems.getOrderStatus().toString().equals(OrderStatus.VIEWED.toString())) {
					throw new ValidationException(403,"Some Items not yet Delivered.");
					//return serviceUtil.getRestResponse(true, "Some Items not yet Delivered.", 403);
				}
				
				if (!(orderItems.getUnAvailableCount() > 0) && !orderItems.isDeleted()) {
					orderItems.setPlacedOrders(closingPlacedOrder);
					ordersDao.updateOrdersItems(orderItems);
				}
			}
			if(!isClosingPlacedOrder){
				placedOrdersEntity.setClosed(true);
				placedOrdersEntity.setMerged(true);
				placedOrdersEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
				placedOrdersEntity.setLastUpdatedDateTime(closingPlacedOrder.getServerDateTime());
				ordersDao.ordersUpdate(placedOrdersEntity);
			}
			
		}
		List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(closingPlacedOrder);
		List<PurchaseItem> purchaseDetails = new ArrayList<>();
		List<OrderedMenuItems> billingList = new ArrayList<>();
		double cost = 0;
		int statusCode = 200;
		
		for (PlacedOrderItems orderItems : placedOrderItemsList) {
			if (orderItems.getOrderStatus().toString().equals(OrderStatus.ORDERED.toString())
					|| orderItems.getOrderStatus().toString().equals(OrderStatus.VIEWED.toString())) {
				throw new ValidationException(403,"Some Items not yet Delivered.");
				//return serviceUtil.getRestResponse(true, "Some Items not yet Delivered.", 403);
			}
			OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
			billingList.add(orderedMenuItems);
			if (!(orderItems.getUnAvailableCount() > 0) && !orderItems.isDeleted()) {
				
				PurchaseItem purchaseItem = new PurchaseItem(orderItems);
				MenuEntity menuEntity = menuListDao.getMenuEntity(orderedMenuItems.getMenuUuid());
				double amt = serviceUtil.roundOff(menuEntity.getPrice() * orderedMenuItems.getQuantity());
				cost = cost + amt;
				
				int pos = purchaseDetails.indexOf(purchaseItem);
				if(pos != -1){
					PurchaseItem previousPurchaseItem = purchaseDetails.get(pos);
					previousPurchaseItem.setQuantity(previousPurchaseItem.getQuantity() + purchaseItem.getQuantity());
					previousPurchaseItem.setAmount(serviceUtil.roundOff(previousPurchaseItem.getQuantity() * previousPurchaseItem.getUnitPrice()));
				}else{
					purchaseDetails.add(purchaseItem);
				}
			}
		}
		
		/*	
				if (!(orderItems.getUnAvailableCount() > 0) && !orderItems.isDeleted()) {
					OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
					PurchaseItem purchaseItem = new PurchaseItem(orderItems);
					MenuEntity menuEntity = menuListDao.getMenuEntity(orderedMenuItems.getMenuUuid());
					cost = cost + menuEntity.getPrice() * orderedMenuItems.getQuantity();
					billingList.add(orderedMenuItems);
					int pos = purchaseDetails.indexOf(purchaseItem);
					if(pos != -1){
						PurchaseItem previousPurchaseItem = purchaseDetails.get(pos);
						previousPurchaseItem.setQuantity(previousPurchaseItem.getQuantity() + purchaseItem.getQuantity());
						previousPurchaseItem.setAmount(previousPurchaseItem.getUnitPrice() * previousPurchaseItem.getUnitPrice());
					}else{
						purchaseDetails.add(purchaseItem);
					}
					
					if(!isClosingPlacedOrder){
						orderItems.setPlacedOrders(closingPlacedOrder);
						//objects.add(orderItems);
						ordersDao.updateOrdersItems(orderItems);
					}
				}
				
			}
			if(!isClosingPlacedOrder){
				placedOrdersEntity.setClosed(true);
				placedOrdersEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
				placedOrdersEntity.setLastUpdatedDateTime(closingPlacedOrder.getServerDateTime());
				ordersDao.ordersUpdate(placedOrdersEntity);
				//objects.add(placedOrdersEntity);
			}
		}*/
		
		closingPlacedOrder.setPrice(serviceUtil.roundOff(cost));
		closingPlacedOrder.setDiscount(0);
		closingPlacedOrder.setTaxAmount(0);
		closingPlacedOrder.setTotalPrice(serviceUtil.roundOff(cost));
		closingPlacedOrder.setServerDateTime(ServiceUtil.getCurrentGmtTime());
		closingPlacedOrder.setLastUpdatedDateTime(closingPlacedOrder.getServerDateTime());
		closingPlacedOrder.setClosed(true);
		try {
			sendData(closingPlacedOrder, purchaseDetails);
		} catch (ValidationException e) {
			statusCode = e.getCode() == 500 ? 405 : 400;
			e.printStackTrace();
		}
		//ordersDao.updates(objects);
		ordersDao.ordersUpdate(closingPlacedOrder);
		menuListDao.deleteReservedTable(closingPlacedOrder.getUserMobileNumber());
		PlaceOrdersJson placeOrdersJson = new PlaceOrdersJson(closingPlacedOrder);
		placeOrdersJson.getMenuItems().addAll(billingList);
		
		String data = gson.toJson(placeOrdersJson);
		return serviceUtil.getRestResponse(true, data, statusCode);
	}
	
	private ResponseEntity<String> generateBilling(PlacedOrdersEntity placedOrders, TableList tableNumber) {
		List<OrderedMenuItems> billingList = new ArrayList<>();
		int statusCode = 200;
		if (!placedOrders.isClosed()) {
			List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrders);
			List<PurchaseItem> purchaseDetails = new ArrayList<>();
			double cost = 0;
			for (PlacedOrderItems orderItems : placedOrderItemsList) {
				if (orderItems.getOrderStatus().toString().equals(OrderStatus.ORDERED.toString())
						|| orderItems.getOrderStatus().toString().equals(OrderStatus.VIEWED.toString())) {
					return serviceUtil.getRestResponse(true, "Some Items not yet Delivered.", 403);
				}
				OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
				PurchaseItem purchaseItem = new PurchaseItem(orderItems);
				
				billingList.add(orderedMenuItems);
				if (!(orderedMenuItems.getUnAvailableCount() > 0) && !orderItems.isDeleted()) {
					MenuEntity menuEntity = menuListDao.getMenuEntity(orderedMenuItems.getMenuUuid());
					cost = cost + menuEntity.getPrice() * orderedMenuItems.getQuantity();
					purchaseDetails.add(purchaseItem);
				}
			}
			placedOrders.setPrice(cost);
			placedOrders.setDiscount(0);
			placedOrders.setTaxAmount(0);
			placedOrders.setTotalPrice(cost);
			placedOrders.setServerDateTime(ServiceUtil.getCurrentGmtTime());
			placedOrders.setLastUpdatedDateTime(placedOrders.getServerDateTime());
			placedOrders.setClosed(true);
			try {
				sendData(placedOrders, purchaseDetails);
			} catch (ValidationException e) {
				statusCode = e.getCode() == 500 ? 405 : 400;
				e.printStackTrace();
			}
			ordersDao.ordersUpdate(placedOrders);
		} else {
			List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrders);
			for (PlacedOrderItems orderItems : placedOrderItemsList) {
				OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
				billingList.add(orderedMenuItems);
			}
		}

		PlaceOrdersJson placeOrdersJson = new PlaceOrdersJson(placedOrders);
		placeOrdersJson.getMenuItems().addAll(billingList);
		
		String data = gson.toJson(placeOrdersJson);
		return serviceUtil.getRestResponse(true, data, statusCode);
	}
	
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getKitchenOrderDetails(String requestData){
		try {
			List<GetKitchenOrders> getKitchenOrderList = gson.fromJson(requestData,
					new TypeToken<List<GetKitchenOrders>>() {}.getType());
			List<String> orderIds = new ArrayList<>();
			KitchenOrderListResponse kitchenOrderListResponse = new KitchenOrderListResponse();
			//List<PlaceOrdersJson> placeOrdersJsons = new ArrayList<>();
			for (GetKitchenOrders getKitchenOrders : getKitchenOrderList) {
				orderIds.add(getKitchenOrders.getOrderId());
				PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(getKitchenOrders.getPlacedOrdersUuid());
				if (placedOrdersEntity != null && !placedOrdersEntity.isClosed()) {
					processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), getKitchenOrders.getServerDateTime());
				} else {
					kitchenOrderListResponse.getClosedOrders().add(getKitchenOrders.getOrderId());
				}
			}

			int size = orderIds.size();
			if(size > 0){
				int quo = size / 25;
				int rem = size % 25;
				if (quo > 0) {
					for (int i = 0; i < quo; i++) {
						List<String> orderIdsTemp = orderIds.subList(i * 25, (i + 1) * 25);
						List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPlacedOrders(orderIdsTemp);
						for (PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities) {
							processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), 0);
						}
					}
				}

				if (rem > 0) {
					List<String> orderIdsTemp = orderIds.subList(quo * 25, size);
					List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPlacedOrders(orderIdsTemp);
					for (PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities) {
						processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), 0);
					}
				}
			}else{
				List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPlacedOrders();
				for (PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities) {
					processKitchenData(placedOrdersEntity, kitchenOrderListResponse.getPlaceOrdersJsonList(), 0);
				}
			}
			
			String data = gson.toJson(kitchenOrderListResponse);
			return serviceUtil.getRestResponse(true, data);
		}catch(Exception e){
			logger.error("Error in getKitchenOrderDetails,Params["+requestData+"]", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	private void processKitchenData(PlacedOrdersEntity placedOrdersEntity, List<PlaceOrdersJson> placeOrdersJsons,
			long lastServerTime) {
		PlaceOrdersJson placeOrdersJson = new PlaceOrdersJson(placedOrdersEntity, true);
		if (placedOrdersEntity.getTableNumber() == null) {
			if (placedOrdersEntity.getOrderType().toString().equals(OrderType.TakeAway.toString())) {
				placeOrdersJson.setTableNumber("T");
			} else {
				placeOrdersJson.setTableNumber("W");
			}
		} else {
			placeOrdersJson.setTableNumber(placedOrdersEntity.getTableNumber().getTableNumber());
		}

		placeOrdersJsons.add(placeOrdersJson);
		List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrdersEntity, lastServerTime);
		for (PlacedOrderItems placedOrderItems : placedOrderItemsList) {
			OrderedMenuItems orderedMenuItems = new OrderedMenuItems(placedOrderItems, true);
			placeOrdersJson.getMenuItems().add(orderedMenuItems);
		}
		List<CookingCommentsEntity> commentsEntities = ordersDao.getCookingComments(placedOrdersEntity, lastServerTime);
		for (CookingCommentsEntity cookingCommentsEntity : commentsEntities) {
			KitchenCookingComments kitchenCookingComments = new KitchenCookingComments(cookingCommentsEntity);
			placeOrdersJson.getCookingCommentsList().add(kitchenCookingComments);
		}
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getPreviousOrder(String requestData){
		try{
			JsonObject requestJson = (JsonObject)jsonParser.parse(requestData);
			//long serverLastUdpateTime = requestJson.get("serverLastUdpateTime").getAsLong();
			String mobileNumber = null;
			if(requestJson.get("mobileNumber") != null){
				 mobileNumber = requestJson.get("mobileNumber").getAsString();
			}
			
			TableList tableList = tableListDao.getTables(requestJson.get("tableNumber").getAsString());
			PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(tableList,mobileNumber);
			if(placedOrdersEntity != null && !placedOrdersEntity.isClosed()){
				PlaceOrdersJson placeOrdersJson =new PlaceOrdersJson(placedOrdersEntity);
				List<PlacedOrderItems> placedOrderItemsList = ordersDao.getPreviousPlacedOrderItems(placedOrdersEntity, 0);
				for(PlacedOrderItems orderItems : placedOrderItemsList){
					OrderedMenuItems orderedMenuItems = new OrderedMenuItems(orderItems);
					placeOrdersJson.getMenuItems().add(orderedMenuItems);
				}
				String data = gson.toJson(placeOrdersJson);
				return serviceUtil.getRestResponse(true, data,200);
			}
			return serviceUtil.getRestResponse(true, "",404);
		}catch(Exception e){
			logger.error("Error in getPreviousOrder,Params["+requestData+"]", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> updateKitchenOrderStatus(String requestData){
		try{
			List<PlaceOrdersJson> placeOrdersList = gson.fromJson(requestData,
					new TypeToken<List<PlaceOrdersJson>>() {}.getType());
			List<KitchenOrderStatusSyncResponse> placedOrderUuid = new ArrayList<>();
			for(PlaceOrdersJson placeOrdersJson : placeOrdersList){
				PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(placeOrdersJson.getPlaceOrderUuid());
				if(placedOrdersEntity != null){
					KitchenOrderStatusSyncResponse statusSyncResponse = new KitchenOrderStatusSyncResponse();
					placedOrderUuid.add(statusSyncResponse);
					statusSyncResponse.setPlacedOrderUuid(placeOrdersJson.getPlaceOrderUuid());
					List<OrderedMenuItems> menuItemsList =	placeOrdersJson.getMenuItems();
					for(OrderedMenuItems menuItems : menuItemsList){
						PlacedOrderItems placedOrderItems =	ordersDao.getPlacedOrderItems(menuItems.getPlacedOrderItemsUUID());
						if(placedOrderItems != null){
							if(menuItems.getOrderStatus().toString().equals(OrderStatus.DELIVERED.toString())){
								PlacedOrderItems previousPlacedOrderItems =	 ordersDao.getDeliveredItems(placedOrdersEntity, placedOrderItems.getItemCode());
								if(previousPlacedOrderItems != null){
									previousPlacedOrderItems.setQuantity(previousPlacedOrderItems.getQuantity() + menuItems.getQuantity());
									previousPlacedOrderItems.setLastUpdatedTime(ServiceUtil.getCurrentGmtTime());
									placedOrderItems.setDeleted(true);
									ordersDao.updateOrdersItems(previousPlacedOrderItems);
								}
							}
							
							placedOrderItems.setUnAvailableCount(menuItems.getUnAvailableCount());
							placedOrderItems.setOrderStatus(menuItems.getOrderStatus());
							placedOrderItems.setLastUpdatedTime(ServiceUtil.getCurrentGmtTime());
							//placedOrderItems.setServerSyncTime(ServiceUtil.getCurrentGmtTime());
							placedOrderItems.setQuantity(menuItems.getQuantity());
							ordersDao.updateOrdersItems(placedOrderItems);
							statusSyncResponse.getPlacedOrderItemsUuid().add(menuItems.getPlacedOrderItemsUUID());
							if(menuItems.getUnAvailableCount() > 0){
								MenuEntity menuEntity = placedOrderItems.getMenuItem();
								if(menuEntity != null){
									menuEntity.setStatus(Status.UN_AVAILABLE);
									menuEntity.setServerTime(placedOrderItems.getServerSyncTime());
									menuListDao.udpateMenuEntity(menuEntity);
								}
							}
						}
					}
					placedOrdersEntity.setLastUpdatedDateTime(ServiceUtil.getCurrentGmtTime());
					ordersDao.ordersUpdate(placedOrdersEntity);
				}
			}
			String data = gson.toJson(placedOrderUuid);
			return serviceUtil.getRestResponse(true, data,200);
		}catch(Exception e){
			logger.error("Error in updateKitchenOrderStatus,Prams["+requestData+"]",e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.");
	}
	
	
	@Scheduled(fixedDelay = 10000)
	public void getPaymentStatus(){
		Session session = null;
		try {
			session = ordersDao.openSession();
			List<List<String>> unPaiedListUUids = ordersDao.getUnPaiedListPurchaseUUids(session);
			int size = unPaiedListUUids.size();
			if (size > 0) {
				int quo = size / 200;
				// int ream = size % 200;
				for (int i = 0; i < quo; i++) {
					List<List<String>> temp = unPaiedListUUids.subList(i * 200, i + 200);
					List<String> unPaiedUUIDs = new ArrayList<>();
					for (List<String> uuids : temp) {
						unPaiedUUIDs.add(uuids.get(0));
					}
					getOrderStatus(unPaiedUUIDs, session);
				}
				List<List<String>> unPaiedUUIDsTemp = unPaiedListUUids.subList(quo * 200, size);
				if (unPaiedUUIDsTemp.size() > 0) {
					List<String> unPaiedUUIDs = new ArrayList<>();
					for (List<String> uuids : unPaiedUUIDsTemp) {
						unPaiedUUIDs.add(uuids.get(0));
					}
					getOrderStatus(unPaiedUUIDs, session);
				}
			}
		}catch(Exception e){
			logger.error("Error in getPaymentStatus", e);
		}finally{
			ordersDao.closeSession(session);
		}
	}
	
	private void getOrderStatus(List<String> purchaseUUids,Session session){
		
		try{
			session.getTransaction().begin();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			//headers.add("Authorization", "Basic " + base64Creds);
			headers.add("Content-Type", "application/json");
			headers.add("mobilePay", "merchant");
			headers.add("accessToken", restUrls.getAccessToken());
			headers.add("serverToken", restUrls.getServerToken());
			String requestBody = gson.toJson(purchaseUUids);
			HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
			
			String response = restTemplate.postForObject(restUrls.getServerUrl()+"/"+restUrls.getOrderStatus(), request, String.class);
			ResponseData responseData = gson.fromJson(response, ResponseData.class);
			if(responseData.getStatusCode() == 200){
				String orderStatusList = gson.toJson(responseData.getData());
				PurchaseStatus purchaseStatusList = gson.fromJson(orderStatusList, PurchaseStatus.class);
				for(HistoryPurchaseData historyPurchaseData  : purchaseStatusList.getHistoryData()){
					
					PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrdersEntity(historyPurchaseData.getPurchaseUUID(),session);
					if(placedOrdersEntity != null){
						placedOrdersEntity.setPaymentStatus(historyPurchaseData.getPaymentStatus());
						placedOrdersEntity.setAmountPaid(historyPurchaseData.getTotalAmount());
						placedOrdersEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					}
					PaymentDetails paymentDetails = new PaymentDetails(historyPurchaseData);
					
					String purchaseItems = gson.toJson(historyPurchaseData.getPurchaseItem());
					paymentDetails.setPurchaseItems(purchaseItems);
					
					String calculatedAmounts = gson.toJson(historyPurchaseData.getCalculatedAmounts());
					paymentDetails.setCalculatedAmounts(calculatedAmounts);
					
					session.save(paymentDetails);
					
					//ordersDao.savePaymentDetails(paymentDetails);
					//ordersDao.ordersUpdate(placedOrdersEntity);
					
					DiscardEntity discardEntity = historyPurchaseData.getDiscardDetails();
					if(discardEntity != null){
						placedOrdersEntity.setPaymentStatus(PaymentStatus.CANCELLED);
						discardEntity.setPaymentDetails(paymentDetails);
						session.save(discardEntity);
						//ordersDao.saveDiscardEntity(discardEntity);
					}
					
					session.update(placedOrdersEntity);
				}
			}
			session.getTransaction().commit();
		}catch(Exception e){
			logger.error("Error in getOrderStatus", e);
			session.getTransaction().rollback();
		}
		
	}
	
	
	private void processHistory(PlacedOrdersEntity placedOrdersEntityTemp){
		Session session = null;
		try{
			session = ordersDao.openSession();
			session.getTransaction().begin();
			PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(session,placedOrdersEntityTemp.getPlaceOrdersUUID());
			HistoryEntity history = new HistoryEntity(placedOrdersEntity);
			
			List<CookingCommentsEntity> cookingCommentsEntities = ordersDao.getCookingCommentsEntity(session, placedOrdersEntity);
			List<CookingCommentsJson> cookingCommentsList = new ArrayList<>();
			for(CookingCommentsEntity commentsEntity : cookingCommentsEntities){
				CookingCommentsJson commentsJson =  new CookingCommentsJson(commentsEntity);
				cookingCommentsList.add(commentsJson);
				session.delete(commentsEntity);
			}
			String cookingComments  = gson.toJson(cookingCommentsList);
			history.setCookingComments(cookingComments);
			session.save(history);
			//save
			List<PlacedOrderItems>  placedOrderItemsList = ordersDao.getPlacedOrderItems(placedOrdersEntity,session);
			for(PlacedOrderItems placedOrderItems : placedOrderItemsList){
				HistoryMenuItem historyMenuItem = new HistoryMenuItem(placedOrderItems);
				String historyData = gson.toJson(historyMenuItem);
				HistoryDetailsEntity historyDetailsEntity = new HistoryDetailsEntity();
				historyDetailsEntity.setItemDetails(historyData);
				historyDetailsEntity.setHistoryUUID(ServiceUtil.uuid());
				historyDetailsEntity.setHistoryEntity(history);
				historyDetailsEntity.setDeleted(placedOrderItems.isDeleted());
				session.save(historyDetailsEntity);
				session.delete(placedOrderItems);
				//save
			}
			if(placedOrdersEntity.getPurchaseUUID() != null){
				List<PaymentDetails>  paymentDetailsList =	ordersDao.getPaymentDetails(session, placedOrdersEntity.getPurchaseUUID());
				for(PaymentDetails paymentDetails : paymentDetailsList){
					PaymentDetailsHistory paymentDetailsHistory = new PaymentDetailsHistory(paymentDetails);
					paymentDetailsHistory.setHistoryEntity(history);
					List<DiscardEntity> discardEntities = ordersDao.getDiscardEntity(session, paymentDetails);
					JsonArray jsonArray = new JsonArray();
					for(DiscardEntity discardEntity : discardEntities){
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("reason", discardEntity.getReason());
						jsonObject.addProperty("discardBy", discardEntity.getReason());
						jsonObject.addProperty("createdDateTime", discardEntity.getReason());
						jsonArray.add(jsonObject);
						session.delete(discardEntity);
					}
					paymentDetailsHistory.setDiscardDetails(jsonArray.toString());
					session.save(paymentDetailsHistory);
					session.delete(paymentDetails);
					//save
				}
				
			}
			session.delete(placedOrdersEntity);
			session.getTransaction().commit();
			// Delete
		}catch(Exception e){
			if(session != null){
				session.getTransaction().rollback();
			}
			
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	
	@Scheduled(fixedDelay = 10000)
	public void moveHistory(){
		Session session = null;
		try{
			session = ordersDao.openSession();
		/*	LocalDateTime localDateTime = LocalDateTime.now();
			LocalDateTime startOfDay = localDateTime.toLocalDate().atStartOfDay();
			
			long startOfDayInMilli = startOfDay.toInstant(ZoneOffset.UTC).toEpochMilli() - 600000;*/
			long startOfDayInMilli = System.currentTimeMillis() - 600000;
			List<PlacedOrdersEntity> placedOrdersEntities = ordersDao.getPreviousDayOrders(session, startOfDayInMilli);
			ordersDao.closeSession(session);
			session = null;
			for(PlacedOrdersEntity placedOrdersEntity : placedOrdersEntities){
				processHistory(placedOrdersEntity);
			}
		}catch(Exception e){
			logger.error("Error in moveHistory", e);
		}finally{
			ordersDao.closeSession(session);
		}
	}
	
	

}
