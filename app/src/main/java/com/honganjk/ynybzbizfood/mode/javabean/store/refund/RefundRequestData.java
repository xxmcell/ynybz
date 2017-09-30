package com.honganjk.ynybzbizfood.mode.javabean.store.refund;

/**
 * Created by Administrator on 2017-07-20.
 * id	必选，int	订单Id
 * remark	可选	退款/退货说明
 * reach	可选，int	是否已送达，0-否，1-是，默认为0
 * type	必选，int	处置类型，1-确认收货，2-申请退款，3-申请退货
 * reason	可选，int	退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
 * money	可选，double	退款金额，需小于订单总价
 *
 *
 * {
 "msg": "ok",
 "code": "A00000",
 "data": {
 "code": null,			//快递单号
 "express": null,			//快递公司名
 "reason": 1,	//退款货原因code，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货
 "money": 10.2,	//退款金额，double
 "state": 10,	//退款状态，5-待发货申请退款，6-待收货申请退款，7-退款中，8-退款完成，
 9-待收货申请退货、10-待评价申请退货、11-已收货申请退货，12-退货中待买家发货，13-退货中-待卖家收货，14-退货退款完成，
 15-拒绝退款-待发货，16-拒绝退款-待收货，17-拒绝退货-待收货，
 18-拒绝退货-待评价，19-拒绝退货-已收货，20-待发货-取消退货，25-退货退款中
 "time": 1499326517000,	//退款自动处理时间
 "current": 1501575389316,	//当前时间戳
 "type": 1		//1-退款，2-退货退款
 }

 */


public class RefundRequestData {

        /**
         * code : null
         * express : null
         * reason : 1
         * money : 10.2
         * state : 10
         * time : 1499326517000
         * current : 1501575389316
         * type : 1
         */

        private Object code;
        private Object express;
        private int reason;
        private double money;
        private int state;
        private long time;
        private long current;
        private int type;

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public Object getExpress() {
            return express;
        }

        public void setExpress(Object express) {
            this.express = express;
        }

        public int getReason() {
            return reason;
        }

        public void setReason(int reason) {
            this.reason = reason;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getCurrent() {
            return current;
        }

        public void setCurrent(long current) {
            this.current = current;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

}



