package com.honganjk.ynybzbizfood.pressenter.tour.home;


import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.home.HomeTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.home.interfa.TourHomeParentinterfaces;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;

import java.util.List;

/**
 * Created by Administrator on 2017-11-07.
 */

public class HomeTourPresenter extends BasePresenter<TourHomeParentinterfaces.IHomeInterface> {
    public void getData(final boolean isFist) {       //展示数据
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
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<HomeTourBean.DataBean>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<HomeTourBean.DataBean> result) {
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
                HttpRequest.executePostTour(httpCallBack, "/ticket/home.tour", param);
            }
        };
    }

    public void getSearchData(boolean isFirst, final String keyword){
        if (isFirst){
            mvpView.clearPagingData();
        }
        //展示数据
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setLoadingStatus(mvpView)
                        .setShowLoadding(true);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<ObjsBean>>>(builder) {

                    @Override
                    public void onSuccess(HttpResult<List<ObjsBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.clearData();
                            mvpView.getSearchHttpData((List<ObjsBean>) mvpView.getValidData(result.getData()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                param.addParam("keyword", keyword);
                HttpRequest.executePostTour(httpCallBack, "/ticket/queryToursByDest.tour", param);
            }
        };
    }
}
