package com.honganjk.ynybzbizfood.mode.javabean.tour.me;

/**
 * 旅客信息详情
 * Created by Administrator on 2017-12-07.
 */

public class PassengerDetail {
    /**
     * {
     * "msg":"ok",
     * "code":"A00000",
     * "data":{
     * "address":"中山花园风荷苑",	//联系人地址
     * "credentials":"身份证",		//证件名
     * "nation":"中国",			//国籍
     * "sex":1,				//性别1：先生；2：女士
     * "name":"张三",			//姓名
     * "mobile":"13777879513",	//联系电话
     * "sn":"421221199207076153",//证件号码
     * "id":85,				//数据id
     * "type":0,				//身份类型，0-成人，1-儿童，2-军人
     * "email":"email@126.com"	//电邮地址
     * }
     * }
     */
    private String address; //联系地址
    private String credentials;////证件名
    private String nation;//国籍
    private int sex;//性别1：先生；2：女士
    private String name;//姓名
    private String mobile;//联系电话
    private String sn;//证件号码
    private int id;//数据id
    private int type;    //身份类型，0-成人，1-儿童，2-军人
    private String email;//电邮地址

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return nation;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
