package com.honganjk.ynybzbizfood.mode.enumeration;


/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/22 16:05
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：协议
 */

public enum AgreementEnum {
    XIE_YI(1, "注册协议"),
    KE_FU(2, "联系客服");


    private String valuse;
    private int imageId;
    private int key;

    AgreementEnum(int key, String valuse) {
        this.valuse = valuse;
        this.key = key;

    }

    public String getValuse() {
        return valuse;
    }

    public int getKey() {
        return key;
    }


}
