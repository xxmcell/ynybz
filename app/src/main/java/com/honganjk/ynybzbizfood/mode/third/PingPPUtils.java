package com.honganjk.ynybzbizfood.mode.third;


import android.app.Activity;
import android.content.Intent;

import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.pingplusplus.android.Pingpp;

/**
 * 说明:支付的工具类
 * 360621904@qq.com 杨阳 2017/3/8  14:39
 */
public class PingPPUtils {
    /**
     * 拿到支付要素时调用此方法
     *
     * @param activity
     * @param data     支付要素
     */
    public static void createPayment(final BaseActivity activity, final String data) {
        activity.showDialog();
        new Thread() {
            @Override
            public void run() {
                super.run();

                Pingpp.createPayment(activity, data);
            }
        }.start();
    }

    /**
     * 支付状态的判断,在Activity的onActivityResult() 方法中调用
     *
     * @param activity
     * @param requestCode 请求码
     * @param resultCode  响应码
     * @param data        支付要素
     */
    public static int judgePaymentStatus(BaseActivity activity, int requestCode, int resultCode, Intent data) {
        activity.dismissDialog();
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT && resultCode == Activity.RESULT_OK) {
            /**
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
            String result = data.getExtras().getString("pay_result");
            if (result.equals("success")) {
                activity.showInforSnackbar("支付成功", true);
                return 1;

            } else if (result.equals("fail")) {
                activity.showErrorMessage("支付失败");
                return 0;

            } else if (result.equals("cancel")) {
                activity.showWarningMessage("取消支付");
                return 0;

            } else if (result.equals("invalid")) {
                activity.showErrorMessage("支付插件未安装");
                return 0;

            } else if (result.equals("unknown")) {
                activity.showErrorMessage("app进程异常被杀死");
                return 0;
            }
        }
        return 0;
    }
}
