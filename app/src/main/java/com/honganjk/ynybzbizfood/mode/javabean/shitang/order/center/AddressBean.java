package com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */

public class AddressBean implements Serializable{
    /**
     * id : 181
     * uid : 15
     * name : 中山花
     * sex : 2
     * contact : 13545139398
     * longitude : 120.173541
     * latitude : 30.281521
     * address : 中山花园-风荷苑
     * createTime : 2016-12-13 16:07:13
     * updateTime : 2016-12-13 16:07:13
     */
    public int id;
    public int uid;
    public String name;
    public int sex;
    public String contact;
    public double longitude;
    public double latitude;
    public String address;
    public String createTime;
    public String updateTime;

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public String getContact() {
        return contact;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
