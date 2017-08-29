package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.PromotionInfoBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces.IPromotionInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/14.
 */

public class PromotionInfoPresenter extends BasePresenter<IPromotionInfo> {

    public void getPromotionInfo(final boolean isFirst ) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                if (isFirst ) {
                    mvpView.clearPagingData();
                }
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                        .setShowLoadding(false);
                HttpCallBack<HttpResult<ArrayList<PromotionInfoBean>>> httpCallBack = new HttpCallBack<HttpResult<ArrayList<PromotionInfoBean>>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<ArrayList<PromotionInfoBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFirst ) {
                                mvpView.clearData();
                            }
                            mvpView.showYouHui((ArrayList<PromotionInfoBean>) mvpView.getValidData(result.getData()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                HttpRequest.executePost(httpCallBack, "/bz/promotions.action", param);
            }
        };
    }
}
