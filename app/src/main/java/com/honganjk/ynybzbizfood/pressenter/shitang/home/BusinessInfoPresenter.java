package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class BusinessInfoPresenter extends BasePresenter<HomeParentInterface.businessInfo> {
    /**
     * 获取商家信息
     */
    public void getBusinessInfo(final int id){
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setShowLoadding(true);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<BusinessDetailsData>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<BusinessDetailsData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            LogUtils.e(result.toString());
                            mvpView.businessInfo(result.getData());
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id", id);

                HttpRequest.executePost(httpCallBack, "/bz/get.action", param);
            }
        };
    }

    /**
     * 收藏商家信息
     */
    public void getCollect(final int id,final int type,final boolean collect){
                       HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.LOGIN_HEAD)
                        .setShowLoadding(true);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<Boolean> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            LogUtils.e(result.toString());
                            if(collect){
                                mvpView.collectBusiness(result.getData());
                            }else{
                                mvpView.collectBusiness(!result.getData());
                            }
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid", id);
                param.addParam("type", type);

                if(collect){
                    HttpRequest.executePost(httpCallBack, "/user/keep.action", param);
                }else{
                    HttpRequest.executePost(httpCallBack, "/user/cancelKeep.action", param);
                }

    }
}
