package com.honganjk.ynybzbizfood.pressenter.tour.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;

/**
 * Created by Administrator on 2017-12-20.
 */

public class EditEvaluationPresenter extends BasePresenter<OrderTourPresentInterface.EditEvaluationInterface> {

    public void commitTour(int oid, int score, String content, String imgs) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<Boolean>> httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.isCommit(true);
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("oid", oid);
        param.addParam("score", score);
        if (content != null) {
            param.addParam("content", content);
        }
        if (imgs != null) {
            param.addParam("imgs", imgs);
        }
        HttpRequest.executePostTour(httpCallBack, "/token/comment.tour", param);

    }
}
