package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class BreakfastMenuPresenter extends BasePresenter<HomeParentInterface.breakfastMenu> {

    /**
     * 获取菜单
     */
    public void getBreakfastMenu(final int bid){
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setShowLoadding(true);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ArrayList<FoodData.DishsBeanX.DishsBean>>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<ArrayList<FoodData.DishsBeanX.DishsBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.breakfastMenu(result.getData());
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid", bid);

                HttpRequest.executePost(httpCallBack, "/bz/getBreakfast.action", param);
            }
        };
    }

}
