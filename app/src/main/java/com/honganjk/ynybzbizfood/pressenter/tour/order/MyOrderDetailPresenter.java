package com.honganjk.ynybzbizfood.pressenter.tour.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.MyOrderDetailBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;

/**
 * 订单详情
 * Created by Administrator on 2017-12-13.
 */

public class MyOrderDetailPresenter extends BasePresenter<OrderTourPresentInterface.MyOrderDetailInterface>{

    public void getOrderDetail(final int id) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.LOGIN_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<MyOrderDetailBean>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<MyOrderDetailBean> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.getOrderDetail(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id",id);
                HttpRequest.executePostTour(httpCallBack, "/token/orderDetail.tour", param);
            }
        };
    }
}
