package com.honganjk.ynybzbizfood.mode.javabean.store.refund;

/**
 * Created by Administrator on 2017-07-20.
 * id	必选，int	订单Id
 * remark	可选	退款/退货说明
 * reach	可选，int	是否已送达，0-否，1-是，默认为0
 * type	必选，int	处置类型，1-确认收货，2-申请退款，3-申请退货
 * reason	可选，int	退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
 * money	可选，double	退款金额，需小于订单总价
 */
public class RefundRequestData {
    private int id;
    private String remark;
    private int reach;
    private int type;
    private int reason;
    private double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
