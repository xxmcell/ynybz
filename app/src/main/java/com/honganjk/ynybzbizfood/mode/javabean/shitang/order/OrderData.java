package com.honganjk.ynybzbizfood.mode.javabean.shitang.order;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import static android.R.attr.type;

/**
 * 说明:我的订单实体类
 * 360621904@qq.com 杨阳 2017/3/6  14:55
 * <p>
 * <p>
 * "id": 17,				//订单id
 * "sn": "89521468571022975",	//订单号
 * "name": "郑涛",			//收货人电话
 * "mobile": "13967193365",	//收货人联系方式
 * "address": "中山花园风荷苑",	//收货人地址
 * "bid": 8952,				//商户id
 * "bname": "外婆家"			//下单商户名字
 * "contact":"057188888888",	//门店电话
 * "summary": "花菜2份;茭白2份;",//订单摘要
 * "price": 20,				//订单价格
 * "type": 1,				//食堂类型，1-社区食堂；2-营养餐
 * "kind": 1,				//中晚类型，1-中餐；2-晚餐
 * "state": 0,				//订单状态值，0-待支付;1-已接单；2-在配送；3-退款中；4-已完成；5-待评价
 * "create_time": 1468571023000,	//订单创建时间戳，单位ms
 * "delivery_time": "2016-11-21 18:29-18:59 送达"，//送达时间
 */
public class OrderData {
    private int id;
    private String summary;
    private String address;
    private double price;
    private int state;
    private int kind;
    private int bid;
    private String bname;//下单商户名字
    private String name;
    private String mobile;
    private String img;
    private String delivery_time;

    public int getId() {
        return id;
    }

    public String getSummary() {
        return StringUtils.dataFilter(summary);
    }

    public double getPrice() {
        return price;
    }

    public int getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    /**
     * 1-中餐；2-晚餐
     *
     * @return
     */
    public String getPriceS() {
        return StringUtils.dataFilter("¥" + price + (kind == 1 ? "\t中餐" : "\t晚餐"));
    }

    public int getType() {
        return type;
    }

    /**
     * //订单状态值，0-待支付;1-已接单；2-在配送；3-退款中；4-已完成；5-待评价; 7待自提；8自提过期
     * <p>
     * 0:待付款; 2:待收货; 5:待评价;4:已完成;
     *
     * @return
     */
    public String getState() {
        if (state == 0) {

            return "去支付";
        } else if (state == 1) {

            return "确认收货";
        } else if (state == 2) {

            return "确认收货";
        } else if (state == 3) {

            return "退款中";
        } else if (state == 4) {

            return "删除订单";
        } else if (state == 5) {

            return "评价";
        } else if (state == 7) {

            return "待自提";
        } else if (state == 8) {

            return "自提过期";
        }
        return "";
    }




    public int getStateInt() {

        return state;

    }

    public String getBname() {
        return StringUtils.dataFilter(bname);
    }

    public String getImg() {
        return StringUtils.dataFilter(img);
    }

    public String getDelivery_time() {
        if (state == 7 || state == 8) {
            if (!StringUtils.isBlank(delivery_time) && delivery_time.contains("送达")) {
                delivery_time = delivery_time.replace("送达", "自提");
            }
        }
        return StringUtils.dataFilter(delivery_time);
    }

    public int getBid() {
        return bid;
    }

    public String getAddress() {
        return StringUtils.dataFilter(address);
    }
}
