package com.honganjk.ynybzbizfood.pressenter.othre;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.other.view.IOtherView;

/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/22 16:05
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：协议
 */
public class AgreementPresenter extends BasePresenter<IOtherView.IAgreement> {

    public void getHttpData(int id) {

        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<String>>(buider) {
            @Override
            public void onSuccess(HttpResult<String> result) {
                super.onSuccess(result);
                mvpView.getHttpData(result.getData());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addAction("GetOtherList");
        param.addParam("id",id);
        HttpRequest.executePost(httpCallBack, param);
    }


}
