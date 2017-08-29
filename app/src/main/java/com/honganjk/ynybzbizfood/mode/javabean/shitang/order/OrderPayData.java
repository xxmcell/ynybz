package com.honganjk.ynybzbizfood.mode.javabean.shitang.order;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.io.Serializable;

/**
 * 注释说明: 订单支付的实体类
 * 阳：2017/3/13-17:46
 */
public class OrderPayData implements Serializable {
    private String imgeUrl;//订单的图片
    private int orderType;//订单类型，1-餐饮订单，2-护理订单，默认值为1
    private int orderId;//订单类型，1-餐饮订单，2-护理订单，默认值为1
    private String bizName;//商家名称
    private String address;//收货地址
    private double price;//价格
    private String userName;//收货人名称
    private String phone;//收货人电话
    private boolean isOneself;//是否是自提
    private String serviceTime;//服务时间
    private String productName;//产品名称
    private String productType;//产品规格
    /**
     * 食堂支付
     *
     * @param imgeUrl   订单的图片
     * @param orderType 订单类型，1-餐饮订单，2-护理订单，默认值为1
     * @param orderId   订单id
     * @param bizName   商家名称
     * @param address   收货地址
     * @param price     价格
     * @param userName  用户姓名
     * @param phone     用户姓电话
     */
    public OrderPayData(String imgeUrl, int orderType, int orderId, String bizName, String address, double price, String userName, String phone, boolean isOneself) {
        this.imgeUrl = imgeUrl;
        this.orderType = orderType;
        this.orderId = orderId;
        this.bizName = bizName;
        this.address = address;
        this.price = price;
        this.userName = userName;
        this.phone = phone;
        this.isOneself = isOneself;
    }

    /**
     * 陪护支付
     */
    public OrderPayData(String imgeUrl, int orderType, int orderId, String bizName, double price, String serviceTime) {
        this.imgeUrl = imgeUrl;
        this.orderType = orderType;
        this.orderId = orderId;
        this.bizName = bizName;
        this.price = price;
        this.serviceTime = serviceTime;
    }

    /**
     * 商城支付
     */
    public OrderPayData(String imgeUrl, String productName, double price, String productType, int orderType, int orderId) {
        this.imgeUrl = imgeUrl;
        this.productName = productName;
        this.price = price;
        this.productType = productType;
        this.orderType = orderType;
        this.orderId = orderId;
    }
    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public boolean isOneself() {
        return isOneself;
    }

    public void setOneself(boolean oneself) {
        isOneself = oneself;
    }

    public String getImgeUrl() {
        return imgeUrl;
    }

    public void setImgeUrl(String imgeUrl) {
        this.imgeUrl = imgeUrl;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBizName() {
        return StringUtils.dataFilter("商家：" + bizName);
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getAddress() {
        return address;
    }

    public String getUserInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("订单详情：").append(userName).append("\t").append(phone).append(isOneself ? "\n" : "\t").append(isOneself ? "自提地址：" + address : address);
        return stringBuffer.toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceStr() {
        return StringUtils.dataFilter("¥" + price, 2);
    }

    public void setPrice(double price) {
        this.price = price;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
