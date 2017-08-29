package com.honganjk.ynybzbizfood.mode.javabean.base;

/**
 * 说明:广告位的实体父类，如果参数不够使用或字段不一样可以继承重写，或者重新赋值
 * 360621904@qq.com 杨阳 2017/3/1  10:21
 */
public class BannerAdData {
    /**
     * 图片id,或者表示产品id，或者表示其它id
     */
    public int id;
    /**
     * 广告类型
     */
    public int type;
    /**
     * 图片地址
     */
    public String img_url;
    /**
     * 网络地址（有时候点击图片会加载一个网页）
     */
    public String url;

    public BannerAdData() {
    }


    public BannerAdData(String img_url) {
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getImg_url() {
        return img_url;
    }


}
