package com.honganjk.ynybzbizfood.pressenter.tour.detail;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.DetailCommentsBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.detail.interfaces.TourDetailInterface;

/**
 * 旅游详情-评论
 * Created by Administrator on 2017-11-24.
 */

public class TourDetailCommentPresenter extends BasePresenter<TourDetailInterface.DetailCommentsInterface> {

    public void getCommentsData(final int id, final int type) {       //展示数据
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<DetailCommentsBean.Data>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<DetailCommentsBean.Data> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {

                            mvpView.getHttpData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("tid", id);
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                if (type!=0){
                    param.addParam("type",type);
                }
                HttpRequest.executePostTour(httpCallBack, "/ticket/tourComments.tour", param);
            }
        };
    }
}
