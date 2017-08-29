package com.honganjk.ynybzbizfood.mode.javabean.shitang.home;


/**
 * 注释说明: 创建订单返回实体
 * 阳：2017/3/28-14:44
 * "id": 4,			//订单id
 * "price": 20.00,		//订单要支付金额，单位元，精确到分
 */
public class CreateOrderSucceedData {
    private int id;//订单id
    private double price;//订单要支付金额，单位元，精确到分


    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}
