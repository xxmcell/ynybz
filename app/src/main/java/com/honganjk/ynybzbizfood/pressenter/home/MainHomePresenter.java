package com.honganjk.ynybzbizfood.pressenter.home;


import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.ActivityManager;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.base.VersionUpdataB;
import com.honganjk.ynybzbizfood.pressenter.common.CommonPresent;
import com.honganjk.ynybzbizfood.pressenter.common.JudgeVersion;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.view.home.iview.MainHomeView;

/**
 * 作者　　: 杨阳  ww
 * 创建时间: 2016/10/19 13:53
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：
 */

public class MainHomePresenter extends CommonPresent<MainHomeView> {

    /**
     * Android APP检查更新
     * 接口：/common/checkUpdate.action
     * 参数 	约束	说明
     * version	必选	当前安装的app版本，格式1.2.1
     */
    public void judgeVersion() {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.NULL_HEAD)
                .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<JudgeVersion>>(buider) {

            @Override
            public void onSuccess(final HttpResult<JudgeVersion> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    //需要更新
                    if (result.getData() != null) {
                        final MaterialDialog.Builder materialDialog = new MaterialDialog.Builder((BaseActivity) mvpView)
                                .title("提示")
                                .positiveText("立即更新")
                                .negativeColorRes(R.color.gray)
                                .cancelable(!result.getData().getForcedBoolean())
                                .content(result.getData().getDesc())
                                .negativeText(result.getData().getForcedBoolean() == true ? "退出" : "取消")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        new VersionUpdataB().installAPK((BaseActivity) mvpView, result.getData().getUrl());
                                    }
                                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //强制更新时则是退出
                                        if (result.getData().getForcedBoolean()) {
                                            ActivityManager.getInstance().exitAllActivity();
                                        }
                                    }
                                });
                        materialDialog.show();
                    }
                    return;
                }
                mvpView.showErrorSnackbar(result.getMsg());
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("version", DeviceUtil.getVersionName());
//        param.addParam("version", "1.1.1");
        HttpRequest.executePost(httpCallBack, "/common/checkUpdate.action", param);
    }




}
