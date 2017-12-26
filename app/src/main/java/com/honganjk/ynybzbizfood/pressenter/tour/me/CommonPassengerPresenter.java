package com.honganjk.ynybzbizfood.pressenter.tour.me;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.CommonPassengerBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.me.interfaces.TourMeInterface;

import java.util.List;

/**
 * 获取常用联系人列表
 * Created by Administrator on 2017-12-04.
 */

public class CommonPassengerPresenter extends BasePresenter<TourMeInterface.MyCommonPassengerInrerface> {
    public void getData(boolean isFirst) {
        if (isFirst){
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

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<CommonPassengerBean>>>(builder) {

                    @Override
                    public void onSuccess(HttpResult<List<CommonPassengerBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.clearData();
                            mvpView.getCommonPassenger((List<CommonPassengerBean>) mvpView.getValidData(result.getData()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                HttpRequest.executePostTour(httpCallBack, "/token/travelers.tour", param);
            }
        };
    }

    /**
     * 删除旅客
     */
    public void delePassenger(final int id) {
        //展示数据
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.LOGIN_HEAD)
                        .setShowLoadding(false);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<String>>(builder) {

                    @Override
                    public void onSuccess(HttpResult<String> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.deleteSuccess(id);
                        }else {
                            mvpView.showWarningSnackbar(result.getMsg());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id",id);
                HttpRequest.executePostTour(httpCallBack, "/token/delTraveler.tour", param);
            }
        };
    }
}
