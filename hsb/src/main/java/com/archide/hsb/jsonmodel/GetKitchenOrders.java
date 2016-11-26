package com.archide.hsb.jsonmodel;

public class GetKitchenOrders {

    private String orderId;
    private String placedOrdersUuid;
    private long serverDateTime;

    public GetKitchenOrders(){

    }

   

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlacedOrdersUuid() {
        return placedOrdersUuid;
    }

    public void setPlacedOrdersUuid(String placedOrdersUuid) {
        this.placedOrdersUuid = placedOrdersUuid;
    }

    public long getServerDateTime() {
        return serverDateTime;
    }

    public void setServerDateTime(long serverDateTime) {
        this.serverDateTime = serverDateTime;
    }
}
