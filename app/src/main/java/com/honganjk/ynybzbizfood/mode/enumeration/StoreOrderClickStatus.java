package com.honganjk.ynybzbizfood.mode.enumeration;

import static android.R.attr.key;


/**
 * 说明:商城订单的状态
 * 作者： 杨阳; 创建于：  2017-07-17  14:31
 * <p>
 * //0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
 * <p>
 * 取消订单,支付金额，确认收货，查看物流，去评价，删除订单
 */
public enum StoreOrderClickStatus {
    CANCLE_ORDER(0, "取消订单"),
    PAY_THE_AMOUNT(1, "支付金额"),
    CONFIRM_THE_GOODS(2, "确认收货"),
    CHECK_THE_LOGISTICS(3, "查看物流"),
    EVALUATION(4, "去评价"),
    DELETE_ORDER(5, "删除订单");

    private String value;

    StoreOrderClickStatus(int key, String valuse) {
        this.value = valuse;
    }

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

}
