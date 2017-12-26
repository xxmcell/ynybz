package com.honganjk.ynybzbizfood.pressenter.tour.detail;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.detail.interfaces.TourDetailInterface;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 *
 * Created by Administrator on 2017-11-21.
 */

public class TourDetailPresenter extends BasePresenter<TourDetailInterface> {

    public void getData(final int id) {       //展示数据
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<TourDetailBean.DataBean>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<TourDetailBean.DataBean> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.getHttpData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id", id);
                if (userData.isLogin()) {
                    param.addParam("uid", userData.getCode());
                }
                HttpRequest.executePostTour(httpCallBack, "/ticket/tourDetail.tour", param);
            }
        };
    }

    /**
     * 收藏
     * @param id
     */
    public void collect(final int id){
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.LOGIN_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<String>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<String> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.collect(true);
                        }else {
                            mvpView.showWarningSnackbar(result.getMsg());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid",id);
                HttpRequest.executePostTour(httpCallBack, "/token/keepTour.tour", param);
            }
        };
    }

    /**
     * 取消收藏
     * @param id
     */
    public void reCollect(final int id){
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.LOGIN_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<String>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<String> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.collect(false);
                        }else {
                            mvpView.showWarningSnackbar(result.getMsg());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid",id);
                HttpRequest.executePostTour(httpCallBack, "/token/cancelTour.tour", param);
            }
        };
    }
}
