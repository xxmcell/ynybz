package com.honganjk.ynybzbizfood.mode.javabean.peihu.order;


import android.content.Context;
import android.text.SpannableStringBuilder;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.Calendar;

/**
 * 陪护-订单详情
 * <p>
 * "id": 2,				//订单id
 * "sn": "89521466129949231",	//订单号
 * "mobile": "13345678902",	//下单人手机号
 * "serviceTime": " 20170125至20170126共2天",	//服务时间
 * "begin": "20170125",		//约定开始时间
 * "finish": "20170126,共2天",	//约定结束时间
 * "address": "延安路",		//下单地址
 * "remark": "自带工具",		//备注
 * "self": 1,		//自理类型，1-全自理；2-半自理；3不自理
 * "type": 1,			//护理类型，1-全天；2-半天
 * "nid": 2,				//护理人员数据id
 * "nurse": "郑爽",			//护理人员名字
 * "phone": "18336072778"	//护理人员联系电话
 * "contacts": "郑涛",		//订单联系人
 * "sex": 1				//联系人性别，1-男；2-女
 * "state": 1,	//订单状态值， 1-待服务；2-服务中；3-退款中；4-已完成；5-待评价；7-待接单;8-已取消;9-待确认完成
 * "title": "冠心病老人全天护理",	//服务项目名字
 * "price": 100,			//项目价格，总价格=price*num
 * "num": 1,			//项目数量
 * "tImg": "https://hajk.image.alimdn.com/bz/item.png",//项目图片
 * "name": "qaz",			//评论人名字，如无评论则空
 * "score": 3,			//评分，如无评论则空
 * "content": "测试6",		//评论内容，如无评论则空
 * "cImg": "https://hajk.image.alim.com/bz/head.jpg",//评论人头像
 * "cTime": 1466504329000,	//评论时间，unix毫秒时间戳
 * "createTime": 1466129949000,	//下单时间，unix毫秒时间戳
 * "startTime": 1470383446000,	//服务开始时间，如未开始则空
 * "endTime": 1474538073000	//服务结束时间，如未结束则空
 * <p>
 * "pay": 1,	//付款方式，1微信支付2支付宝支付3银联支付4余额支付
 */
public class OrderDetailsBean {
    private int id;
    private String sn;
    private String mobile;
    private String serviceTime;
    private String address;
    private String remark;
    private int state;
    private String title;
    private double price;
    private int num;
    private String tImg;
    private String name;
    private int score;
    private String content;
    private String cImg;
    private String cTime;
    private long createTime;
    private String startTime;
    private String endTime;
    private int start;
    private int self;
    private int type;
    private String nurse;
    private String contacts;
    private int sex;
    private String begin;
    private String finish;
    private int nid;
    private String phone;
    private long appointTime;
    private String money;

    private String reason;//取消原因（"reason": "不方便服务",）
    private long cancelTime;//取消时间（ "cancelTime": 1495442754000,）
    private int pay;//付款方式，1微信支付2支付宝支付3银联支付4余额支付


    /**
     * 个人基本信息
     * g
     *
     * @param context
     * @return
     */
    public SpannableStringBuilder getInfor(Context context) {
        StringBuffer sb = new StringBuffer(contacts + "\t" + getSexStr() + "\t" + mobile);
        return StringUtils.convertTextColor(sb.toString() + "\n" + address, (sb.toString()), context.getResources().getColor(R.color.black));
    }


    /**
     * @return 获取订单信息
     * "state": 1,	//订单状态值， 1-待服务；2-服务中；3-退款中；4-已完成；5-待评价；7-待接单;8-已取消;9-待确认完成
     */
    public String getOrderInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("订单号：").append(sn);
        sb.append("\n\n下单时间：").append(getCreateTimeStr());
        sb.append("\n\n");
        sb.append(getPayStr());

//        if (state == 2 || state == 4 || state == 9 || state == 5) {
//            sb.append("\n开始服务时间：").append(getStartTime());
//        }
//        if (type == 1 && (state == 4 || state == 9 || state == 5)) {
//            sb.append("\n结束服务时间：").append(getEndTime());
//        }
        return sb.toString();
    }

    public int getPay() {
        return pay;
    }

    /**
     * 付款方式，1微信支付2支付宝支付3银联支付4余额支付
     * <p>
     * 支付方式pay:1支付宝支付2微信支付3银联支付4微信公众号支付5余额支付
     *
     * @return
     */
    public String getPayStr() {
        String str = "";
        if (pay == 1) {
            str = "支付方式：支付宝支付";
        } else if (pay == 2) {
            str = "支付方式：微信支付";
        } else if (pay == 3) {
            str = "支付方式：银联支付";
        } else if (pay == 4) {
            str = "支付方式：微信公众号支付";
        } else if (pay == 5) {
            str = "支付方式：余额支付";
        } else if (pay == 0) {
            str = "未支付";
        }
        return str;
    }

    /**
     * 旧：//订单状态值， 1-待服务；2-服务中；3-退款中；4-已完成；5-待评价；7-待接单;8-已取消;9-待确认完成
     * <p>
     * 新：//状态0待支付;1已接单；2在服务；3介入中；4已完成；5待评价；6用户删除；7待接单；8用户取消；9护工取消；10不接单
     *
     * @return 对应的描述
     */
    public SpannableStringBuilder getStateStr(Context content) {
        String str = null;
        switch (state) {
            case 0:
                str = "待支付";
                break;
            case 1:
                str = "待服务";
                break;
            case 2:
                str = "服务中";
                break;
            case 3:
                str = "待服务（平台处理中）";
                break;
            case 4:
                str = "已完成";
                break;
            case 5:
                str = "待评价";
                break;
            case 6:
                str = "用户删除";
                break;
            case 7:
                str = "待接单";
                break;
            case 8:
                str = "已取消";
                break;
            case 9:
                str = "护理人员取消订单";
                break;
            case 10:
                str = "护理人员未接单";
                break;
            case 11:
                str = "服务中（平台处理中）";
                break;

        }
        StringBuffer sb = new StringBuffer();
        sb.append("订单状态：").append(str);
        return StringUtils.convertTextColor(sb.toString(), str, content.getResources().getColor(R.color.main_red));
    }

    /**
     * //自理类型，1-全自理；2-半自理；3不自理
     *
     * @return 自理类型
     */
    public String getSelfStr() {
        String str = null;
        switch (self) {
            case 1:
                str = "全自理";
                break;
            case 2:
                str = "半自理";
                break;
            case 3:
                str = "不能自理";
                break;
        }
        return str;
    }


    public String getAppointTimeStr() {

        try {
            String start = serviceTime.substring(0, 10);
            //开始的时间
            Calendar startT = Calendar.getInstance();
            startT.setTimeInMillis(appointTime);
            startT.set(Calendar.HOUR_OF_DAY, 0);
            startT.set(Calendar.MINUTE, 0);
            startT.set(Calendar.SECOND, 0);
            startT.set(Calendar.MILLISECOND, 0);

            //当前的时间
            Calendar currentT = Calendar.getInstance();
            currentT.set(Calendar.DATE, currentT.get(Calendar.DATE) + 1);
            currentT.set(Calendar.HOUR_OF_DAY, 0);
            currentT.set(Calendar.MINUTE, 0);
            currentT.set(Calendar.SECOND, 0);
            currentT.set(Calendar.MILLISECOND, 0);

            //开始时间是不明天(0表示是明天)
            int tomorrow = (int) (startT.getTimeInMillis() - currentT.getTimeInMillis());
            //判断日期是不大于明天，并且小于今天18点。才可取消订单，其他情况联系平台

            //大于明天
            if (tomorrow > 0) {
                return "取消订单";

                //小于明天
            } else if (tomorrow < 0) {
                return "联系平台";

                //今天18点后
            } else if (tomorrow == 0 && (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 18)) {
                return "联系平台";

                //今天18点前
            } else if (tomorrow == 0 && (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 18)) {
                return "取消订单";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "取消订单";
    }

    public String getStartTime() {
//        if (StringUtils.isBlank(startTime)) {
//            return "未开始服务";
//        }

        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.dataFilter(DateUtils.dateToString(startTime, DateUtils.TIME_HH)));
        return sb.toString();
    }

    public String getEndTime() {
//        if (StringUtils.isBlank(endTime)) {
//            return "未结束服务";
//        }
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.dataFilter(DateUtils.dateToString(endTime, DateUtils.TIME_HH)));
        return sb.toString();
    }

    /**
     * //联系人性别，1-男；2-女
     *
     * @return 性别
     */
    public String getSexStr() {
        String str = sex == 1 ? "选择" : "女士";
        return str;
    }

    public String getMoney() {
        return money;
    }

    public String getBegin() {
        return StringUtils.dataFilter(begin);
    }

    public int getId() {
        return id;
    }

    public String getSn() {
        return StringUtils.dataFilter(sn);
    }

    public String getMobile() {
        return mobile;
    }

    public String getServiceTime() {
        return StringUtils.dataFilter(serviceTime);
    }

    public String getAddress() {
        return StringUtils.dataFilter(address);
    }

    public String getRemark() {
        return "订单备注：" + StringUtils.dataFilter(remark, "无");

    }

    public int getState() {
        return state;
    }

    public String getTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append("项目：");
        sb.append(title);

        return sb.toString();
    }

    public String getPriceStr() {
        return "参考价格：¥" + price;
    }

    public double getPrice() {
        return price;
    }

    public String getSumPrice() {
        StringBuilder sb = new StringBuilder();
        sb.append("参考价合计：¥");
        sb.append(price * num);

        return sb.toString();
    }

    public int getNum() {
        return num;
    }

    public String getNumStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("数量：");
        sb.append(num);

        return sb.toString();
    }

    public long getAppointTime() {
        return appointTime;
    }

    public String getReason() {
        return StringUtils.dataFilter(reason);
    }

    public long getCancelTime() {
        return cancelTime;
    }

    public String getCancelTimeStr() {
        return DateUtils.dateToString(cancelTime, DateUtils.TIME);
    }

    public String gettImg() {
        return StringUtils.dataFilter(tImg);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public String getcImg() {
        return cImg;
    }

    public String getcTime() {
        return DateUtils.getTime(cTime);
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
        String str = String.valueOf(createTime);
        return StringUtils.dataFilter(DateUtils.dateToString(str, DateUtils.TIME_HH));
    }

    public int getStart() {
        return start;
    }

    public int getSelf() {
        return self;
    }

    public int getType() {
        return type;
    }

    public String getNurse() {
        ////护理类型，1-全天；2-半天
        String str = type == 1 ? "护工" : "康复师";

        return str + ": " + StringUtils.dataFilter(nurse);
    }

    public String getContacts() {
        return StringUtils.dataFilter(contacts);
    }

    public int getSex() {
        return sex;
    }

    public String getFinish() {
        return StringUtils.dataFilter(finish);
    }

    public int getNid() {
        return nid;
    }

    public String getPhone() {
        return StringUtils.dataFilter(phone);
    }
}
