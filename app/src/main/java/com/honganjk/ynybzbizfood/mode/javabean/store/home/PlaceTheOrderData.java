package com.honganjk.ynybzbizfood.mode.javabean.store.home;

import android.content.Context;
import android.text.SpannableStringBuilder;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

/**
 * 说明:商城下单
 * 说明:商城下单
 * 说明:商城下单
 * 作者： 杨阳; 创建于：  2017-07-10  15:48
 * aid	必选，int	配送地址的Id
 * remark	可选	订单备注
 * bid	必选，int	保健品id
 * type	必选，int	保健品规格类型
 * num	必选，int	购买数量
 * fare	必选，int	运费，0=包邮
 */
public class PlaceTheOrderData {
    private int aid;
    private String remark;
    private int bid;
    private int type;
    private int num;
    private int fare;


    //用户-地址
    private String address;
    //用户-姓名
    private String name;
    //用户-性别
    private String sex;
    //用户-手机号
    private String phone;
    //用户-地址id
    private int addressid;
    /**
     * 个人基本信息
     *
     * @param context
     * @return
     */
    public SpannableStringBuilder getInfor(Context context) {
        StringBuffer sb = new StringBuffer(name + "\t" + sex + "\t" + phone);
        return StringUtils.convertTextColor(sb.toString() + "\n" + address, (sb.toString()), context.getResources().getColor(R.color.black));
    }
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }
}
