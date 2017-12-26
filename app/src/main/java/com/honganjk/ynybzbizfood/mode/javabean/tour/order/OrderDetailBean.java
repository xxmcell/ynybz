package com.honganjk.ynybzbizfood.mode.javabean.tour.order;

import java.util.List;

/**
 * Created by Administrator on 2017-11-29.
 */

public class OrderDetailBean {
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
    public class Data {
        private String hotelDesc;//酒店描述信息
        private String hotelName;//酒店名
        private String safeDesc;//保险描述信息
        private String safeName;	//保险名称
        private int safePrice;//保险价格
        private String ticketDesc;//门票描述
        private String ticketName;//门票名称
        private List<Traffics> traffics;

        public String getHotelDesc() {
            return hotelDesc;
        }

        public void setHotelDesc(String hotelDesc) {
            this.hotelDesc = hotelDesc;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getSafeDesc() {
            return safeDesc;
        }

        public void setSafeDesc(String safeDesc) {
            this.safeDesc = safeDesc;
        }

        public String getSafeName() {
            return safeName;
        }

        public void setSafeName(String safeName) {
            this.safeName = safeName;
        }

        public int getSafePrice() {
            return safePrice;
        }

        public void setSafePrice(int safePrice) {
            this.safePrice = safePrice;
        }

        public String getTicketDesc() {
            return ticketDesc;
        }

        public void setTicketDesc(String ticketDesc) {
            this.ticketDesc = ticketDesc;
        }

        public String getTicketName() {
            return ticketName;
        }

        public void setTicketName(String ticketName) {
            this.ticketName = ticketName;
        }

        public void setTraffics(List<Traffics> traffics) {
            this.traffics = traffics;
        }
        public List<Traffics> getTraffics() {
            return traffics;
        }
        public class Traffics {

            private String arrive;//到达地点
            private String atime;	//到达时间
            private int mode;  //交通方式，1-飞机，2-高铁，3-汽车，4-邮轮
            private String otime;   	//出发时间
            private String outset;//出发地点
            private String provider;//服务商信息
            private int type;//往返类型，1-去程，2-返程
            public void setArrive(String arrive) {
                this.arrive = arrive;
            }
            public String getArrive() {
                return arrive;
            }

            public void setAtime(String atime) {
                this.atime = atime;
            }
            public String getAtime() {
                return atime;
            }

            public void setMode(int mode) {
                this.mode = mode;
            }
            public int getMode() {
                return mode;
            }

            public void setOtime(String otime) {
                this.otime = otime;
            }
            public String getOtime() {
                return otime;
            }

            public void setOutset(String outset) {
                this.outset = outset;
            }
            public String getOutset() {
                return outset;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }
            public String getProvider() {
                return provider;
            }

            public void setType(int type) {
                this.type = type;
            }
            public int getType() {
                return type;
            }

        }
    }
}
