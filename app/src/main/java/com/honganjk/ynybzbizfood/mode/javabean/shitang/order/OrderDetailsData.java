package com.honganjk.ynybzbizfood.mode.javabean.shitang.order;

import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * Created by admin on 2017/3/11.
 * <p>
 * <p>
 * "id": 17,				//订单id
 * "sn": "89521468571022975",	//订单号
 * "uid": 2,				//订餐用户id
 * "delivery_time": "14点送达",	//配送时间要求
 * "address": "中山花园风荷苑",	//配送地址
 * "remark": "加辣",			//订单备注
 * "price": 20,				//订单价格
 * "amount": 20.22,	//优惠的额度，浮点值，单位为元，精确到分
 * "pay": 1,	//支付渠道，1-支付宝；2-微信；3-银联
 * "state": 4,	//订单状态值，0-待支付;1-已接单；2-在配送；3-退款中；4-已完成；5-待评价; 7待自提；8自提过期
 * "fare": 5,				//配送费
 * "create_time": 1468571023000,	//下单时间戳，单位ms
 * "ods": [
 * {
 * "price": 10,		//菜品价格
 * "num": 2,		//下单数量
 * "name": "菜品1"	//菜品名字
 * },
 * {
 * "price": 1.1,		//菜品价格
 * "num": 2,		//下单数量
 * "name": "bcd"		//菜品名字
 * }
 * ],
 * "name": "1467628023718",	//收货人名字
 * "mobile": "13967193365",		//收货人联系电话
 * "img": "https://hajk.image.alimmdn.com/dev/bz.jpg",//商户图片url
 * "bid": 8952,				//商户id
 * "type": 1,				//中晚类型，1-中餐；2-晚餐
 * "order_time": 1470383446000,	//商户接单时间
 * "contact":"057188888888",	//门店电话
 * "bname": "外婆家"，		//下单商户名字
 * "bulletin": "今天休息"	//商户公告
 */

public class OrderDetailsData {
    private int id;
    private String sn;
    private int uid;
    private String delivery_time;
    private String address;////配送(自提)地址
    private String remark;
    private double price;
    private double amount;
    private int pay;
    private int day;
    private int state;
    private double fare;//配送费
    private int type;
    private long create_time;
    private long update_time;
    private String name;
    private int sex;
    private String mobile;
    private String img;
    private long order_time;
    private int bid;
    private int num;
    private String bname;
    private String contact;
    private List<OdsBean> ods;
    private int self;////是否自提，0-否，1-是
    private String code;//"code": "123456",		//六位数的自提码，当self=1时有意义
    private double fee;//包装费


    public int getId() {
        return id;
    }

    public String getSn() {
        return sn;
    }

    public String getOrderDetails() {
        return "订单号码：" + sn + "\n\n订单时间：" + getCreate_timeStr() + "\n\n收货姓名：" + name + "\n\n手机号码："
                + mobile + "\n\n收货地址：" + address + "\n\n备注信息：" + remark;
    }


    public int getUid() {
        return uid;
    }

    public String getDelivery_time() {
        //自提
        if (self == 1) {
            if (delivery_time.contains("送达")) {
                delivery_time = delivery_time.substring(0, delivery_time.indexOf("送达"));
            }
            return StringUtils.dataFilter("自提时间：" + delivery_time);
        }
        //配送
        return StringUtils.dataFilter("预计送达时间：" + delivery_time);
    }

    public String getAddress() {
        return StringUtils.dataFilter("自提地址：" + address);
    }

    public String getAddress2() {
        return StringUtils.dataFilter(address);
    }

    public String getRemark() {
        return remark;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceStr() {
        return "合计：¥" + price;
    }

    public String getFeeStr() {
        return "¥" + fee;
    }

    public int getSelf() {
        return self;
    }

    public String getCode() {
        return StringUtils.dataFilter(code, "待支付");
    }

    /**
     * 优惠
     *
     * @return
     */
    public String getAmount() {
        return "-¥" + amount;
    }

    public int getPay() {
        return pay;
    }

    public int getDay() {
        return day;
    }

    public int getState() {
        return state;
    }

    /**
     * 状态的描述
     * <p>
     * //订单状态值，0-待支付;1-已接单；2-在配送；3-退款中；4-已完成；5-待评价; 7待自提；8自提过期
     * <p>
     *
     * @return
     */
    public String getStateStr() {
        if (state == 0) {
            return "未支付";

        } else if (state == 1) {
            return "商家已接单：" + StringUtils.dataFilter(DateUtils.getTime(order_time));

        } else if (state == 2) {
            return "正在配送中";

        } else if (state == 3) {
            return "退款中";

        } else if (state == 4) {
            return "订单已完成";

        } else if (state == 5) {
            return "已收货";

        } else if (state == 7) {
            return "待自提";

        } else if (state == 8) {
            return "自提过期";

        } else {
            return "未知";
        }
    }


    public double getFare() {
        return fare;
    }


    public String getFareStr() {
        return "¥" + StringUtils.dataFilter(fare);
    }

    public int getType() {
        return type;
    }

    /**
     * //中晚类型，1-中餐；2-晚餐
     *
     * @return
     */
    public String getTypeStr() {

        return StringUtils.dataFilter(type == 1 ? "中餐" : "晚餐");
    }

    public long getCreate_time() {
        return create_time;
    }


    public String getCreate_timeStr() {
        return StringUtils.dataFilter(DateUtils.dateToString(create_time, DateUtils.TIME_HH));
    }

    public long getUpdate_time() {
        return update_time;
    }

    public String getName() {
        return StringUtils.dataFilter(name);
    }

    public int getSex() {
        return sex;
    }

    public String getMobile() {
        return StringUtils.dataFilter(mobile);
    }

    public String getImg() {
        return img;
    }

    public long getOrder_time() {
        return order_time;
    }

    public int getBid() {
        return bid;
    }

    public int getNum() {
        return num;
    }

    /**
     * 店名
     *
     * @return
     */
    public String getBname() {
        return StringUtils.dataFilter(bname);
    }

    public String getContact() {
        return StringUtils.dataFilter("商家电话：" + contact);
    }

    public List<OdsBean> getOds() {
        return ods;
    }

    public static class OdsBean {
        /**
         * id : 1099
         * oid : 802
         * did : 620
         * price : 1299.0
         * num : 1
         * name : 高血压包月套餐一
         * mid : 549
         * bid : 8997
         * day : 14892480
         * type : 2
         * create_time : 2017-03-11 13:47:58
         * update_time : 2017-03-11 13:47:58
         */

        private int id;
        private int oid;
        private int did;
        private double price;
        private int num;
        private String name;
        private int mid;
        private int bid;
        private int day;
        private int type;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public int getOid() {
            return oid;
        }

        public int getDid() {
            return did;
        }

        public double getPrice() {
            return price;
        }

        public String getPriceStr() {
            return "¥" + price;
        }

        public int getNum() {
            return num;
        }

        public String getName() {
            return name;
        }

        public int getMid() {
            return mid;
        }

        public int getBid() {
            return bid;
        }

        public int getDay() {
            return day;
        }

        public int getType() {
            return type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }
    }
}
