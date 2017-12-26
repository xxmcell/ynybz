package com.honganjk.ynybzbizfood.mode.javabean.tour.order;

import java.util.List;

/**
 * Created by Administrator on 2017-11-27.
 */

public class OrderTourBean {
    private String msg;
    private String code;
    private Data data;
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public static class Data {

        private int total;//总数
        private List<Objs> objs;
        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal() {
            return total;
        }

        public void setObjs(List<Objs> objs) {
            this.objs = objs;
        }
        public List<Objs> getObjs() {
            return objs;
        }

        public static class Objs {

            private int id;//数据id
            private String outsetTime;	//出发时间戳
            private int price;	//订单总价
            private String sn;//订单号
            private int state;//订单状态，0-待付款，1-待进行，2-待评价，3-正常结束，4-退款中，5-退款完成，6-退款被拒
            private String title;//旅行项目标题
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setOutsetTime(String outsetTime) {
                this.outsetTime = outsetTime;
            }
            public String getOutsetTime() {
                return outsetTime;
            }

            public void setPrice(int price) {
                this.price = price;
            }
            public int getPrice() {
                return price;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }
            public String getSn() {
                return sn;
            }

            public void setState(int state) {
                this.state = state;
            }
            public int getState() {
                return state;
            }

            public void setTitle(String title) {
                this.title = title;
            }
            public String getTitle() {
                return title;
            }

        }
    }
}
