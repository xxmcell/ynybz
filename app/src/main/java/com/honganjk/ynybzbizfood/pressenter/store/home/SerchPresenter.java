package com.honganjk.ynybzbizfood.pressenter.store.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;


/**
 * 说明:产品详情
 * 作者： 杨阳; 创建于：  2017-07-07  14:01
 */
public class SerchPresenter extends BasePresenter<IHomeParentInterfaces.ISearchSubscribe> {


    public void getData(final String  s) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreHomeData>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<StoreHomeData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            //返回搜索结果
                            mvpView.getSearchdata(result.getData().getObjs());
                        }
                    }
                };


                HttpRequestParam param = new HttpRequestParam();// TODO: 2017-09-01  .....
                param.addParam("start","0");
                param.addParam("size", "20");
                param.addParam("keyword", s);
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjps.json", param); //搜索接口
            }
        };
    }
}
