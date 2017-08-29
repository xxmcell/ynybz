package com.honganjk.ynybzbizfood.mode.enumeration;


/**
 * 注释说明:  http的返回值
 * 阳：2017/3/9-17:31
 * <p>
 * 返回码值	中文说明	应用场景
 * A00000	成功	所有交互
 * A00001	参数错误	所有交互
 * A00002	系统繁忙，此结果一般表示系统有错，需开发人员解决	所有交互
 * A00004	未登录或token已过期	所有交互
 * A00008	短信提供商错误	发送短信
 * A00009	禁止在短期内重复发送验证码	发送短信
 * A00010	短信验证码已过期	重置密码
 * A00011	短信验证码不正确	重置密码
 * A02405	表示该商户已经注册，禁止再次注册	商户注册
 * A02416	账号或密码错误	商户登录
 * A02404	商户不存在，禁止重置密码	商户忘记密码
 * A03404	菜单不存在，下单失败	下单
 */
public enum HttpCode {

    SUCCEED("A00000", "成功"), FAIL_A("A00001", "参数错误"), FAIL_B("A00002", "系统繁忙"),
    FAIL_C("A00004", "请重新登录"), FAIL_D("A00008", "短信提供商错误"),
    FAIL_E("A00009", "禁止在短期内重复发送验证码"), FAIL_F("A00010", "短信验证码已过期"), FAIL_G("A00011", "短信验证码不正确"),
    FAIL_H("A02405", "表示该商户已经注册，禁止再次注册"), FAIL_I("A02416", "账号或密码错误"),
    FAIL_J("A02404", "商户不存在，禁止重置密码"), FAIL_K("A03404", "菜单不存在，下单失败");

    private String valuse;
    private String key;

    HttpCode(String key, String valuse) {
        this.valuse = valuse;
        this.key = key;
    }

    public String getValuse() {
        return valuse;
    }

    public String getKey() {
        return key;
    }

    public static HttpCode getState(String status) {
        if (status.equals(HttpCode.SUCCEED.getKey())) {
            return SUCCEED;
        } else if (status.equals(HttpCode.FAIL_A.getKey())) {
            return FAIL_A;
        } else if (status.equals(HttpCode.FAIL_B.getKey())) {
            return FAIL_B;
        } else if (status.equals(HttpCode.FAIL_C.getKey())) {
            return FAIL_C;
        } else if (status.equals(HttpCode.FAIL_D.getKey())) {
            return FAIL_D;
        } else if (status.equals(HttpCode.FAIL_E.getKey())) {
            return FAIL_E;
        } else if (status.equals(HttpCode.FAIL_F.getKey())) {
            return FAIL_F;
        } else if (status.equals(HttpCode.FAIL_G.getKey())) {
            return FAIL_G;
        } else if (status.equals(HttpCode.FAIL_H.getKey())) {
            return FAIL_H;
        } else if (status.equals(HttpCode.FAIL_I.getKey())) {
            return FAIL_I;
        } else if (status.equals(HttpCode.FAIL_J.getKey())) {
            return FAIL_J;
        } else if (status.equals(HttpCode.FAIL_K.getKey())) {
            return FAIL_K;
        }
        return FAIL_B;
    }
}
