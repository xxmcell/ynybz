package com.honganjk.ynybzbizfood.mode.javabean.shitang.home;

import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/27.
 * <p>
 * aid	必选，int	配送地址的Id
 * time	必选	配送(自提)时间
 * startTime	必选，long	配送(自提)开始时间，unix-ms时间戳
 * endTime	必选，long	配送(自提)截至时间，unix-ms时间戳
 * remark	可选	订单备注
 * bid	必选，int	下单的商户id
 * type	必选，int	餐饮类型，0-早餐，1-中晚餐
 * mid	可选，int	中晚餐的菜单id，type=1时必选
 * self	必选，int	是否自提，0-否，1-是
 * dids	必选	所点菜品及分数，格式如下：
 * 菜品id-份数；菜品id-份数；菜品之间用分号间隔，菜品id和份数用-间隔，实例：1-2;3-2   表示菜品1点了2份，菜品3点了2份
 * fid	可选，int	优惠方案id，使用价格优惠时必传
 */
public class OrderCommitData {
    private int aid;//必选，int	配送地址的Id
    private String time;//配送(自提)时间
    private String remark;//订单备注
    private int type;//餐饮类型，0-早餐，1-中晚餐
    private int bid;//下单的商户id
    private int mid;//	中晚餐的菜单id，type=1时必选
    private String dids;//所点菜品及分数
    private int fid;//	优惠方案id，使用价格优惠时必传
    private long startTime;
    private long endTime;
    private int self;//是否自提，0-否，1-是

    //不用上传的参数
    private double price;//订单支付的金额
    private double theShoppingFee;//运费的价格
    private String address;//	配送地址的
    private double fee;//包装费
    private String userName;//收货人名称
    private String phone;//收货人电话

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDids() {
        return dids;
    }

    /**
     * 设置菜品的类型
     *
     * @param datas
     */
    public void setDids(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < datas.size(); x++) {
            sb.append(datas.get(x).getId()).append("-").append(datas.get(x).getNum()).append(";");
        }
        this.dids = sb.toString();
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime + (1000 * 60 * 5);
    }


    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }

    public void setDids(String dids) {
        this.dids = dids;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTheShoppingFee() {
        return theShoppingFee;
    }

    public void setTheShoppingFee(double theShoppingFee) {
        this.theShoppingFee = theShoppingFee;
    }

    public double getFee() {
        return fee;
    }

    public String getFeeStr() {
        return "¥" + StringUtils.dataFilter(fee,2);
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
