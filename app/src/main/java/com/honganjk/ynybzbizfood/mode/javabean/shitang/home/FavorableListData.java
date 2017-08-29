package com.honganjk.ynybzbizfood.mode.javabean.shitang.home;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.io.Serializable;

/**
 * 注释说明: 优惠的实体类
 * 阳：2017/3/28-10:53
 * <p>
 * "total": 15.00,		//满减门槛
 * "amount": 12.00,	//优惠额度
 * "descs": "满15减12"	//优惠信息
 */
public class FavorableListData implements Serializable{

    private double total;
    private double amount;
    private String descs;

    public double getTotal() {
        return total;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescs() {
        return StringUtils.dataFilter(descs);
    }
}
