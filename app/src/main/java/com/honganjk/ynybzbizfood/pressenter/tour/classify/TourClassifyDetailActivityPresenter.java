package com.honganjk.ynybzbizfood.pressenter.tour.classify;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourDetialBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.classify.interfaces.MyClassifyPrenterInrerfaces;

/**
 * Created by Administrator on 2017-11-17.
 */

public class TourClassifyDetailActivityPresenter  extends BasePresenter<MyClassifyPrenterInrerfaces.MyClassifyInrerfaces> {

    public void getData(final boolean isFist, final int did) {       //展示数据
        if (isFist) {
            mvpView.clearPagingData();
        }

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ClassifyTourDetialBean.Data>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<ClassifyTourDetialBean.Data> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFist) {
                                mvpView.clearData();
                            }
                            mvpView.getHttpData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                param.addParam("did", did);
                HttpRequest.executePostTour(httpCallBack, "/ticket/tours.tour", param);
            }
        };
    }
    public void getDataWithCondition(final boolean isFist, final int did, final int num, final int lower, final int upper, final int sort, final String keyword) {       //展示数据
        if (isFist) {
            mvpView.clearPagingData();
        }

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ClassifyTourDetialBean.Data>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<ClassifyTourDetialBean.Data> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFist) {
                                mvpView.clearData();
                            }
                            mvpView.getHttpData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                param.addParam("did", did);
                if (num!=0){
                    param.addParam("num", num);
                }
                if (lower!=0){
                    param.addParam("lower", lower);
                }
                if (upper!=0){
                    param.addParam("upper",upper);
                }
                if (sort!=0){
                    param.addParam("sort",sort);
                }
                if (keyword!=null){
                    param.addParam("keyword",keyword);
                }
                HttpRequest.executePostTour(httpCallBack, "/ticket/tours.tour", param);
            }
        };
    }
}
