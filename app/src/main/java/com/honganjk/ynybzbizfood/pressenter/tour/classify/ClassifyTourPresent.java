package com.honganjk.ynybzbizfood.pressenter.tour.classify;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.classify.interfa.TourClassifyParentinterfaces;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;

/**
 * Created by Administrator on 2017-11-07.
 */

public class ClassifyTourPresent extends BasePresenter<TourClassifyParentinterfaces.IClassifyInterface> {
    public void getData(final boolean isFist, final int type) {       //展示数据
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
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ClassifyTourBean.Data>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<ClassifyTourBean.Data> result) {
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
                param.addParam("type", type);    //分类，1-推荐；2-周边；3-全国；4-日本；5-东南亚；6-海岛；7-港澳台；8-中东非；9-邮轮游
                HttpRequest.executePostTour(httpCallBack, "/ticket/dests.tour", param);
            }
        };
    }
}
