package com.honganjk.ynybzbizfood.mode.javabean.tour.order;

/**
 * {
 * "mobile": "14990672040",
 * "name": "张三",
 * "nation": "中国",
 * "sex": 1,
 * "sn": "412823199206062010"
 * },
 * Created by Administrator on 2017-12-13.
 */

public class Travelers {
    private String mobile;//联系电话

    private String name;//姓名

    private String nation;//国籍

    private int sex;//性别，1：先生；2：女士

    private String sn;//身份证号

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return this.nation;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return this.sn;
    }
}
