package com.honganjk.ynybzbizfood.mode.javabean.peihu.home;


import android.content.Context;
import android.text.SpannableStringBuilder;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.io.Serializable;

/**
 * 陪护-生成订单请求实体
 */
public class SubscribeOrderBean implements Serializable {
    //服务-名称
    private String serviceName;
    //用户-地址
    private String address;
    //用户-姓名
    private String name;
    //用户-性别
    private String sex;
    //用户-手机号
    private String phone;
    //商家图片
    private String img;
    //1:护工；2康复师
    private int type;
    //用户-地址id
    private int addressid;
    //护工或者康复的id
    private int userId;
    //服务项目的Id
    private int serviceId;
    //自理类型(1-完全自理；2-半自理；3-完全不自理)
    private int selfType;
    //备注
    private String remark;
    //份数
    private double number;
    //单价
    private double price;
    //护工的开始时间
    private int mNursingStartTime = -1;
    private String mNursingStartTimeStr;
    //护工的结束时间
    private int mNursingEndTime = -1;
    //护工的服务时间
    private int[] mNursingServiceTime;
    //周几（康复师）
    private int week;
    //时间（康复师）
    private int time;
    private String serviceTime;//服务时间
    //服务人员的手机号
    private String servicePhone;
  //服务人员头像
    private String portrait;

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

    /**
     * 获取总价
     */
    public double getSumPrice() {
        if (type == 1) {
            return price * number;
        } else {
            return price * 2;
        }
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getTime() {
        return time;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public void setmNursingStartTimeStr(String mNursingStartTimeStr) {
        this.mNursingStartTimeStr = mNursingStartTimeStr;
    }

    public String getmNursingStartTimeStr() {
        return mNursingStartTimeStr;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSelfType() {
        return selfType;
    }

    public void setSelfType(int selfType) {
        this.selfType = selfType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getmNursingEndTime() {
        return mNursingEndTime;
    }

    public void setmNursingEndTime(int mNursingEndTime) {
        this.mNursingEndTime = mNursingEndTime;
    }

    public int[] getmNursingServiceTime() {
        return mNursingServiceTime;
    }

    public void setmNursingServiceTime(int[] mNursingServiceTime) {
        if (mNursingServiceTime != null) {
            number = mNursingServiceTime.length;
        }
        this.mNursingServiceTime = mNursingServiceTime;
    }

    public int getmNursingStartTime() {
        return mNursingStartTime;
    }

    public void setmNursingStartTime(int mNursingStartTime) {
        this.mNursingStartTime = mNursingStartTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
