package com.honganjk.ynybzbizfood.mode.javabean.shitang.home;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

/**
 * 注释说明: 优惠的实体类
 * 阳：2017/3/28-10:53
 */
public class FavorableData {
    private int id;//方案数据id
    private int total;//满减门槛，当type ！=2时，无意义
    private int amount;//优惠额度
    private String descs;//优惠信息
    private int type;//优惠类型，1-店铺首单立减优惠，2-满减优惠

    public int getId() {
        return id;
    }

    public int getTotal() {
        return total;
    }

    public int getAmount() {
        return amount;
    }

    public String getAmountContent() {
        return "-¥" + amount;
    }


    public String getDescs() {
        return StringUtils.dataFilter("(" + descs + ")");
    }

    public int getType() {
        return type;
    }


}
