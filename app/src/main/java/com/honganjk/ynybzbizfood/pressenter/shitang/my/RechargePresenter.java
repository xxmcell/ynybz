package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.RechargeBean;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/3/8.
 */

public class RechargePresenter extends BasePresenter<MyParentInterfaces.IRecharge> {
    public void postData(int pay, double real, double obtain) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<RechargeBean>> httpCallBack = new HttpCallBack<HttpResult<RechargeBean>>(builder) {
            @Override
            public void onSuccess(HttpResult<RechargeBean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {

                    try {
                        mvpView.backZhifuYaoSui(result.getJSONObject().getJSONObject("data").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("pay", pay);
        param.addParam("real", real);
//        param.addParam("obtain",obtain);

        HttpRequest.executePost(httpCallBack, "/pay/getRecharge.action", param);

    }

}
