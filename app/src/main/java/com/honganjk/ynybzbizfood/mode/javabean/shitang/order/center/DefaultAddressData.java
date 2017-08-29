package com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center;

/**
 * 注释说明: 默认的地址
 * 阳：2017/3/29-15:47
 * <p>
 * "id": 1,				//收货地址id
 * "distance": 1,			//地址到商户的距离，单位公里
 * "fare": 10,			//配送费，单位元
 * "name": "qaz",			//收货人名字
 * "sex": 1,				//收货人性别 1-先生；2-女士
 * "contact": "13967193365",	//收货联系电话
 * "longitude": 31,			//收货地址经度
 * "latitude": 121,			//收货地址纬度
 * "address": "wsx"			//收货地址
 */
public class DefaultAddressData {
    private int fare;
    private String address;
    private int distance;
    private int sex;
    private String contact;
    private double latitude;
    private String name;
    private int id;
    private double longitude;

    public int getFare() {
        return fare;
    }

    public String getAddress() {
        return address;
    }

    public int getDistance() {
        return distance;
    }

    public int getSex() {
        return sex;
    }

    public String getSexStr() {
        return sex == 1 ? "先生" : "女士";
    }

    public String getContact() {
        return contact;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }
}
