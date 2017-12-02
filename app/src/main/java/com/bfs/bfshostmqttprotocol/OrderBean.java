package com.bfs.bfshostmqttprotocol;

import java.util.List;

/**
 * Created by Joe on 2017/11/21.
 */

public class OrderBean {

    public String id;
    public String time;
    public int sum_price;

    public List<meal> order;

    public void setId(String id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSumprice(int sum_price) {
        this.sum_price = sum_price;
    }

    public void setList(List list) {
        this.order = list;
    }

}

class meal {
    private String type;
    private String name;
    private int quantity;
    public meal(String type, String name, int quantity) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

}