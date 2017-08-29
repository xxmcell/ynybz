package com.honganjk.ynybzbizfood.mode.enumeration;

/**
 * 说明:阿里百川路径
 * 360621904@qq.com 杨阳 2017/3/7  16:17
 */
public enum ALBCPath {

    USER_ST("Android_USER_ST", "Android/客户端_食堂"),
    USER_PH("Android_USER_PH", "Android/客户端_陪护"),
    BIZ_ST("Android_BIZ_ST", "Android/商户端_食堂"),
    BIZ_PH("Android_BIZ_PH", "Android/商户端_陪护");

    private String valuse;
    private String key;

    ALBCPath(String key, String valuse) {
        this.valuse = valuse;
        this.key = key;
    }

    public String getValuse() {
        return valuse;
    }

    public String getKey() {
        return key;
    }


}
