package com.bfs.bfshostmqttprotocol;

/**
 * Created by Joe on 2017/11/22.
 */

public class OrderRespondBean {

    private String id;
    private String orderStatus;
    private int orderSerialNumber;

    public void setId(String id) {
        this.id = id;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderSerialNumber(int orderSerialNumber) {
        this.orderSerialNumber = orderSerialNumber;
    }

}
