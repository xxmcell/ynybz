package com.honganjk.ynybzbizfood.mode.enumeration;

/**
 * 说明:请求接口要添加的头部
 * 360621904@qq.com 杨阳 2017/2/20  17:17
 * <p>
 * NULL_HEAD            没有头部（一些共用接口，不需要头部）
 * LOGIN_HEAD           两个头部(登录时要添加的头部)
 * UNREGISTERED_HEAD    三个头部（未登录要添加的头部）
 *             头部类型：0没有头部，2两个头部，3三个头部
 */

public enum HeadType {
    NULL_HEAD(0, "没有头部"), LOGIN_HEAD(2, "两个头部"), UNREGISTERED_HEAD(3, "三个头部");

    private int key;
    private String value;

    HeadType(int key, String valuse) {
        this.key = key;
        this.value = valuse;
    }

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

}
