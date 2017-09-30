package com.honganjk.ynybzbizfood.pressenter.store.refund;

import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundProgress;
import com.honganjk.ynybzbizfood.pressenter.store.order.StoreOrderParentPresenter;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;

/**
 * Created by Administrator on 2017-09-29.
 */

public class RefundProgressPresenter extends StoreOrderParentPresenter<StoreOrderParentInterfaces.IOrderDetails> {


    public RefundProgressPresenter(int requestCode) {
        super(requestCode);
    }
    public void getRefundProgressData(int id) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<RefundProgress>>(builder) {
            @Override
            public void onSuccess(HttpResult<RefundProgress> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.RefundProgress(result.getData());
                } else {
                    showMsg(result);
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePostStore(httpCallBack, "/token/refundProgress.json", param);
    }
}
