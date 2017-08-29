package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.List;

/**
 * Created by admin on 2017/3/3.
 */

public class HomePresenter extends BasePresenter<HomeParentInterface.home> {

    /**
     * 78949889
     * <p>
     * 食堂主页-推荐商家
     */
    public void getBusinessListData(final double longitude, final double latitude,
                                    final String city) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                        .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                        .setShowLoadding(false);
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<BusinessData>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<BusinessData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.clearData();
                            mvpView.businessList(result.getData());
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("longitude", longitude);
                param.addParam("latitude", latitude);
                param.addParam("city", city);
                HttpRequest.executePost(httpCallBack, "/bz/recommend.action", param);
            }
        };

    }

    /**
     * 食堂主页-常吃厨房
     */
    public void getCommonListData(final double longitude, final double latitude) {


        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                .setShowLoadding(true);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<BusinessData>>>(buider) {

            @Override
            public void onSuccess(HttpResult<List<BusinessData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.clearData();
                    mvpView.commonList(result.getData());
                    return;
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("longitude", longitude);
        param.addParam("latitude", latitude);
        HttpRequest.executePost(httpCallBack, "/user/daily.action", param);
    }


}
