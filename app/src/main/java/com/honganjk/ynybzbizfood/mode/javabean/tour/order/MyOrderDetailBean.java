package com.honganjk.ynybzbizfood.mode.javabean.tour.order;

import java.util.List;

/**
 * {
 * "outset": "杭州",
 * "dest": "杭州",
 * "createTime": 1499254844000,
 * "roomCount": 1,
 * "roomType": 1,
 * "tid": 1250,
 * "price": 1250,
 * "hotelDesc": "大床或双床，可住两人",
 * "hotelName": "中山国际大酒店",
 * "safeDesc": "平安意外险描述信息",
 * "safeName": "平安意外险",
 * "safePrice": 0,
 * "ticketDesc": "长隆野生动物园一日",
 * "ticketName": "长隆野生动物园门票",
 * "traffics": [
 * {
 * "arrive": "上海虹桥机场",
 * "atime": "23：40",
 * "mode": 4,
 * "otime": "20：45",
 * "outset": "萧山国际机场",
 * "provider": "海南航空XXXXX",
 * "type": 1
 * },
 * {
 * "arrive": "",
 * "atime": "",
 * "mode": 4,
 * "otime": "",
 * "outset": "",
 * "provider": "",
 * "type": 2
 * }
 * ],
 * "sn": "b14992548437629506360",
 * "title": "Swisse 钙&维他命D片 柠檬酸钙 150片/罐",
 * "state": 0,
 * "num": 2,
 * "outsetTime": 1502158632000,
 * "roomPrice": 1858,
 * "tourPrice": 0,
 * "returnTime": 1502181286000,
 * "travelers": [
 * {
 * "mobile": "14990672040",
 * "name": "张三",
 * "nation": "中国",
 * "sex": 1,
 * "sn": "412823199206062010"
 * },
 * {
 * "mobile": "14990672040",
 * "name": "李四",
 * "nation": "中国",
 * "sex": 2,
 * "sn": "412823199206062010"
 * }
 * ]
 * }
 * Created by Administrator on 2017-12-13.
 */

public class MyOrderDetailBean {
    private String outset;//出发地

    private String dest;//目的地

    private String createTime;//创建时间戳

    private int roomCount;//房间数

    private int roomType;//房间类型，0-随机，1-大床，2-标准间

    private int tid;//旅行项目id

    private double price;//订单总价，double

    private String hotelDesc;//酒店描述信息

    private String hotelName;//酒店名

    private String safeDesc;//保险描述信息

    private String safeName;	//保险名称

    private int safePrice;//保险价格

    private String ticketDesc;//门票描述

    private String ticketName;//门票名称

    private List<Traffics> traffics;//交通出行信息

    private String sn;//订单号

    private String title;	//标题

    private int state;//状态，0-待付款，1-待出行，2-待评价，3-已完成，4-退款中，5-退款完成，6-退款被拒

    private int num;//出行人数

    private int outsetTime;//去程时间戳

    private int roomPrice;//房间价格

    private int tourPrice;//行程及门票价格

    private int returnTime;//返程时间戳

    private List<Travelers> travelers;

    public void setOutset(String outset) {
        this.outset = outset;
    }

    public String getOutset() {
        return this.outset;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDest() {
        return this.dest;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getRoomCount() {
        return this.roomCount;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getRoomType() {
        return this.roomType;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTid() {
        return this.tid;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setHotelDesc(String hotelDesc) {
        this.hotelDesc = hotelDesc;
    }

    public String getHotelDesc() {
        return this.hotelDesc;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public void setSafeDesc(String safeDesc) {
        this.safeDesc = safeDesc;
    }

    public String getSafeDesc() {
        return this.safeDesc;
    }

    public void setSafeName(String safeName) {
        this.safeName = safeName;
    }

    public String getSafeName() {
        return this.safeName;
    }

    public void setSafePrice(int safePrice) {
        this.safePrice = safePrice;
    }

    public int getSafePrice() {
        return this.safePrice;
    }

    public void setTicketDesc(String ticketDesc) {
        this.ticketDesc = ticketDesc;
    }

    public String getTicketDesc() {
        return this.ticketDesc;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketName() {
        return this.ticketName;
    }

    public void setTraffics(List<Traffics> traffics) {
        this.traffics = traffics;
    }

    public List<Traffics> getTraffics() {
        return this.traffics;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return this.sn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public void setOutsetTime(int outsetTime) {
        this.outsetTime = outsetTime;
    }

    public int getOutsetTime() {
        return this.outsetTime;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getRoomPrice() {
        return this.roomPrice;
    }

    public void setTourPrice(int tourPrice) {
        this.tourPrice = tourPrice;
    }

    public int getTourPrice() {
        return this.tourPrice;
    }

    public void setReturnTime(int returnTime) {
        this.returnTime = returnTime;
    }

    public int getReturnTime() {
        return this.returnTime;
    }

    public void setTravelers(List<Travelers> travelers) {
        this.travelers = travelers;
    }

    public List<Travelers> getTravelers() {
        return this.travelers;
    }

}
