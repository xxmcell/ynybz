package com.honganjk.ynybzbizfood.mode.enumeration;

import static android.R.attr.key;


/**
 * 说明:订单按钮的状态与文字
 * 作者： 杨阳; 创建于：  2017-06-23  14:08
 * <p>
 * 绿色按钮：支付金额，联系平台，去评价
 * <p>
 * 灰色按钮：取消订单，删除订单
 */
public enum OrderClickStatus {
    QU_XIAO_DING_DAN(0, "取消订单"),
    SHAN_CHU_DING_DAN(1, "删除订单"),
    ZHI_FU_JIN_E(2, "支付金额"),
    LIAN_XI_PING_TAI(3, "联系平台"),
    QU_PING_JIA(4, "去评价");

    private String value;

    OrderClickStatus(int key, String valuse) {
        this.value = valuse;
    }

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

}
