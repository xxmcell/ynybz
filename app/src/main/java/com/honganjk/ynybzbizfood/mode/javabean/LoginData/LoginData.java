package com.honganjk.ynybzbizfood.mode.javabean.LoginData;


/**
 * 说明:登录实体类
 * <p>
 * 360621904@qq.com 杨阳 2017/3/3  17:32
 * "code": "8952",   //该用户的唯一码，之后的接口请求header中使用，建议前端解析为数字类型数据存储在本地
 * "token": "e8a69bf65aefc23d0f360ab695e9eac7" //该用户的当前时段的token，之后的接口请求header中使用
 */
public class LoginData {
    private int code;
    private String token;

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }
}
