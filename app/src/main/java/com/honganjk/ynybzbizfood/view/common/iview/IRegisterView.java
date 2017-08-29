package com.honganjk.ynybzbizfood.view.common.iview;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;

/**
 * Created by yang on 2016/8/17.
 */
public interface IRegisterView extends BaseView {
    /**
     * 获取验证码
     *
     * @param whetherVerification
     */
    void isVerification(boolean whetherVerification);

    /**
     * 注册成功后接收UserData对象
     *
     * @param
     */
    void receiverUserData(UserInfo UserData);
}
