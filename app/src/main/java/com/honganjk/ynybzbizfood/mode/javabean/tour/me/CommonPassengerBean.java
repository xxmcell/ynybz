package com.honganjk.ynybzbizfood.mode.javabean.tour.me;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-04.
 */

public class CommonPassengerBean implements Serializable{
    /**
     * {"name":"测试",
     * "mobile":"15144171713",
     * "self":0,
     * "sn":"220202199310161211",
     * "id":94,
     * "type":0}
     */
    private String name;  //姓名
    private String mobile;//手机
    private int self;   //自己
    private String sn;//身份证
    private int id;//标识id
    private int type;//身份类型，0-成人，1-儿童，2-军人

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setSelf(int self) {
        this.self = self;
    }

    public int getSelf() {
        return self;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return sn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
