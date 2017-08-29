package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.List;

/**
 * Created by admin on 2017/3/3.
 */

public class HomeParentPresenter extends BasePresenter<HomeParentInterface.homeParent> {
    /**
     * 广告
     */
    public void getHttpData() {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.NULL_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<BannerData>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<BannerData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.banner(result.getData());
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("site", 2);
        HttpRequest.executePost(httpCallBack, "/nurse/activities.action", param);
    }








}
