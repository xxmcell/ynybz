package com.honganjk.ynybzbizfood.pressenter.common;

/**
 * 说明:版本检测工具类
 * <p>
 * 360621904@qq.com 杨阳 2017/3/17  14:40
 * <p>
 * "version": "1.0.1",	//最新版本的版本号
 * "forced":0,		//是否强制升级，0-否，1-是
 * "desc":"本次升级内容1…2…3…"	//升级说明
 * "url":      * //最新版本的安装包地址
 */
public class JudgeVersion {
    private int forced;
    private String version;
    private String url;
    private String desc;

    public int getForced() {
        return forced;
    }

    public boolean getForcedBoolean() {

//        return true;
        return forced == 1 ? true : false;
    }


    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }
}
