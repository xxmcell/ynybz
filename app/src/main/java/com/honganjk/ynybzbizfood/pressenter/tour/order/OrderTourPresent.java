package com.honganjk.ynybzbizfood.pressenter.tour.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderTourBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;

/**
 *
 * Created by Administrator on 2017-11-07.
 */

public  class  OrderTourPresent extends BasePresenter<OrderTourPresentInterface.IOrderInterface> {

    public void getOrder(final boolean isFirst, final int state){
        if (isFirst){
            mvpView.clearPagingData();
        }
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.LOGIN_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<OrderTourBean.Data>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<OrderTourBean.Data> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFirst) {
                                mvpView.clearData();
                            }
                            mvpView.getHttpData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("state", state);
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                HttpRequest.executePostTour(httpCallBack, "/token/orders.tour", param);
            }
        };
    }
}
