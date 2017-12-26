package com.honganjk.ynybzbizfood.mode.javabean.tour.order;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-13.
 */

public class OrderBean implements Serializable{
    String id;
    String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
