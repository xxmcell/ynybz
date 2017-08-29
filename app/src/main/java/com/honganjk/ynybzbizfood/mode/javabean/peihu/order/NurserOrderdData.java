package com.honganjk.ynybzbizfood.mode.javabean.peihu.order;


import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.Calendar;
import java.util.List;

/**
 * 陪护-我的订单-实体
 * <p>
 */
public class NurserOrderdData {

    /**
     * total : 4
     * objs : [{"id":245,"nid":25,"name":"曾立中","img":"https://hajk.image.alimmdn.com/bz/head3.jpg?t=1487739590014","type":1,"serviceTime":"2017-03-04共1天","num":1,"title":"老人全天护理","price":199,"state":5,"mobile":"18658130532","start":null}]
     */

    private int total;
    private List<ObjsBean> objs;

    public int getTotal() {
        return total;
    }

    public List<ObjsBean> getObjs() {
        return objs;
    }

    /**
     * "id": 10,		//订单id
     * "mobile": "18336072778"	//护理人员联系电话
     * "state": 1,	//订单状态值， 1-待服务；2-服务中；3-退款中；4-已完成；5-待评价；7-待接单;8-已取消;9-待确认完成
     * "nid": 1,		//护理人员id
     * "name": "武",	//护理人员名字
     * "img": "https://hajk.image.alimmdn.com/ynybzNursing/upload/Mac_020000000000_Registor/2049FCCD-4924-442E-8A53-2BF98C793E30_Avatar",//护工头像
     * "type": 2,		//护理类型，1-全天；2-钟点
     * "serviceTime": null,	//服务时间
     * "num": 1,			//服务项目数量
     * "title": "老人全天护理",	//服务项目名字
     * "price": 100		//服务项目单价，总价为price*num
     * <p>
     * money": 100.00，//订单真实价格，护工未上报之前为空值
     */
    public static class ObjsBean {


        private int id;
        private int nid;
        private String name;
        private String img;
        private int type;
        private String serviceTime;
        private int num;
        private String title;
        private double price;
        private String money;
        private int state;
        private String mobile;
        private long start;
        private long appointTime;


        public String getMoney() {
            return money;
        }

        public String getMoneyStr() {
            if (StringUtils.isBlank(money)) {
                return " 参考价合计：¥" + price*num;
            } else {
                return " 合计：¥" + money;

            }
        }

        public int getId() {
            return id;
        }

        public int getNid() {
            return nid;
        }

        public String getName() {
            return StringUtils.dataFilter(name);
        }

        /**
         * //护理类型，1-全天；2-钟点
         *
         * @return
         */
        public String getNameDetails() {
            return StringUtils.dataFilter(type == 1 ? "护工：" + name : "康复师：" + name);
        }

        public String getImg() {
            return StringUtils.dataFilter(img);
        }

        public int getType() {
            return type;
        }

        public String getServiceTime() {
            return StringUtils.dataFilter(serviceTime);
        }


        public String getServiceTimeDetails() {
            return StringUtils.dataFilter(serviceTime );
        }

        public int getNum() {
            return num;
        }

        public String getNumStr() {
            return "合计：" + num * price;
        }


        public String getTitle() {
            return StringUtils.dataFilter(title);
        }

        public double getPrice() {
            return price;
        }

        /**
         * 0待支付；1已接单；2在服务；3待服务介入中；4已完成；5待评价；6用户删除；7待接单；8用户取消；9护工取消；10不接单；11服务中介入
         *
         * @return
         */
        public int getState() {
            return state;
        }

        /**
         * 0待支付；1已接单；2在服务；3待服务介入中；4已完成；5待评价；6用户删除；7待接单；8用户取消；9护工取消；10不接单；11服务中介入
         *
         * @return
         */
        public String getStateStr() {
            String str = "";
            switch (state) {
                case 1:
//                    str = "已接单";
                    str = "预约成功";
                    break;
                case 2:
                    str = "服务中";
                    break;
                case 3:
//                    str = "待服务介入中";
                    str = "平台介入中";
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
//                    str = "待接单";
                    str = "预约成功";
                    break;
                case 8:
                    str = "用户取消";
                    break;
                case 9:
                    str = "护工取消";
                    break;
                case 10:
                    str = "不接单";
                    break;
                case 11:
//                    str = "服务中介入";
                    str = "平台介入中";
                    break;
            }
            return str;
        }

        public String getMobile() {
            return StringUtils.dataFilter(mobile);
        }

        public long getStart() {
            return start;
        }

        public long getAppointTime() {
            return appointTime;
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
    }
}
