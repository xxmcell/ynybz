package com.honganjk.ynybzbizfood.view.common.iview;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;

/**
 * Created by yang on 2016/8/17.
 */
public interface IloginView extends BaseView {
    /**
     * 获取验证码
     *
     * @param whetherVerification
     */
    void getVerification(boolean whetherVerification);

    /**
     * 登录成功后接收UserData对象
     *
     * @param
     */
    void login(boolean isSucceed);

    /**
     * 绑定微信号
     *
     * @param
     */
    void boundWX(boolean isSucceed);
}
