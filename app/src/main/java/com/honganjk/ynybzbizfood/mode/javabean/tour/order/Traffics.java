package com.honganjk.ynybzbizfood.mode.javabean.tour.order;

/**
 * {
 * "arrive": "上海虹桥机场",
 * "atime": "23：40",
 * "mode": 4,
 * "otime": "20：45",
 * "outset": "萧山国际机场",
 * "provider": "海南航空XXXXX",
 * "type": 1
 * },
 * Created by Administrator on 2017-12-13.
 */

public class Traffics {
    private String arrive;//到达地点

    private String atime;	//到达时间

    private int mode;//交通方式，1-飞机，2-高铁，3-汽车，4-邮轮

    private String otime;	//出发时间

    private String outset;//出发地点

    private String provider;//服务商信息

    private int type;//往返类型，1-去程，2-返程

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getArrive() {
        return this.arrive;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getAtime() {
        return this.atime;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getOtime() {
        return this.otime;
    }

    public void setOutset(String outset) {
        this.outset = outset;
    }

    public String getOutset() {
        return this.outset;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
