package com.honganjk.ynybzbizfood.pressenter.tour.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;

/**
 * Created by Administrator on 2017-11-07.
 */

public  class  OrderTourPresent <V extends BaseView> extends BasePresenter<V> {

    public void deleteOrder(StoreOrderData2.ObjsBean data, int type) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (mvpView instanceof StoreOrderParentInterfaces.IOrderParent) {
                        ((StoreOrderParentInterfaces.IOrderParent) mvpView).deleteOrder(true);
                    }

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", data.getId());
        if(type==5){
            param.addParam("type",4);
        }else if(type==2){
            param.addParam("type",1);
        }

        HttpRequest.executePostTour(httpCallBack, "/token/order.tour", param);
    }
}
