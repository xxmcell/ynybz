package com.honganjk.ynybzbizfood.pressenter.tour.me;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourDetialBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.me.interfaces.TourMeInterface;

import java.util.List;

/**
 * 我的收藏
 * Created by Administrator on 2017-12-12.
 */

public class MyKeepsPresenter extends BasePresenter<TourMeInterface.MyKeepsInrerface> {

    public void getData(final boolean isFirst) {
        if (isFirst) {
            mvpView.clearPagingData();
        }
        //展示数据
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.LOGIN_HEAD)
                        .setLoadingStatus(mvpView)
                        .setShowLoadding(false);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ClassifyTourDetialBean.Data>>(builder) {

                    @Override
                    public void onSuccess(HttpResult<ClassifyTourDetialBean.Data> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFirst) {
                                mvpView.clearData();
                            }
                            mvpView.getKeeps((List<ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                HttpRequest.executePostTour(httpCallBack, "/token/keeps.tour", param);
            }
        };
    }
}
