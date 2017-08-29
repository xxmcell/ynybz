package com.honganjk.ynybzbizfood.mode.javabean.store.order;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

/**
 * "address": "杭州市下城区中山花园-风荷苑",	//收货地址
 * "amount": 0,					//优惠金额
 * "code": "111",	//快递单号，当state>1时有效
 * "contact": "0571-87603133",	//供货商联系电话
 * "createTime": 1499254844000,	//创建时间戳
 * "deliveryTime": null,			//发货时间戳，为空时待发货
 * "express": null,	//快递名称，当state>1时有效
 * "fare": 0,		//运费
 * "id": 1250,	//数据id
 * "img": "https://hajk.image.alimmdn.com/bz/banner1.jpg",//商品图片
 * "label": "500g",	//规格显示名称
 * "mobile": "658999",			//收货人手机号
 * "money": 98,	//真实单价，总价=money*num
 * "name": "你",				//收货人昵称
 * "num": 1,	//售出数量
 * "payTime": null,			//支付时间
 * "price": 218,	//参考单价
 * "remark": null,				//订单备注
 * "sex": 1,		//收货人性别，1：先生；2：女士
 * "sn": "b14992548437629506360",	//订单号
 * "state": 0,	//0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
 * "title": "Swisse 钙&维他命D片 柠檬酸钙 150片/罐"	//商品标题
 */
public class StoreOrderDetailsData {

    private String address;
    private double amount;
    private String code;
    private String contact;
    private long createTime;
    private String deliveryTime;
    private String express;
    private int fare;
    private int id;
    private String img;
    private String label;
    private String mobile;
    private double money;
    private String name;
    private int num;
    private String payTime;
    private double price;
    private String remark;
    private int sex;
    private String sn;
    private int state;
    private String title;

    public String getAddress() {
        return StringUtils.dataFilter(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCode() {
        return StringUtils.dataFilter(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContact() {
        return StringUtils.dataFilter(contact);
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDeliveryTime() {
        return StringUtils.dataFilter(deliveryTime);
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getExpress() {
        return StringUtils.dataFilter(express);
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return StringUtils.dataFilter(img);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLabel() {
        return StringUtils.dataFilter(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMobile() {
        return StringUtils.dataFilter(mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return StringUtils.dataFilter(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPayTime() {
        return StringUtils.dataFilter(payTime);
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return StringUtils.dataFilter(remark);
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSn() {
        return StringUtils.dataFilter(sn);
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return StringUtils.dataFilter(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
