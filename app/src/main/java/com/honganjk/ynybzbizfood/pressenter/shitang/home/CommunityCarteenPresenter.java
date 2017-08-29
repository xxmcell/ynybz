package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class CommunityCarteenPresenter extends BasePresenter<HomeParentInterface.communityCarteen>
{

    /**
     * 食堂主页-社区食堂
     * type : 0-距离  1-评分
     */
    public void getCommunicationCarteenListData(final boolean isFirst,
                                                final double longitude, final double latitude,
                                                final String currentCity, final int businessType, final int sortType) {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setLoadingStatus(mvpView)
                        .setShowLoadding(true);

                if(isFirst){
                    mvpView.clearPagingData();
                }

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<BusinessData>>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<List<BusinessData>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if(isFirst){
                                mvpView.clearData();
                            }
                            mvpView.businessList((List<BusinessData>) mvpView.getValidData(result.getData()));
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("longitude", longitude);
                param.addParam("latitude", latitude);
                param.addParam("city", currentCity);
                param.addParam("type", businessType);
                param.addParam("sort", sortType);
                HttpRequest.executePost(httpCallBack, "/bz/canteens.action", param);
            }
        };

    }
}
