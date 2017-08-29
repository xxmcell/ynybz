package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FoodsMenuPresenter extends BasePresenter<HomeParentInterface.foodsMenu> {

    /**
     * 获取菜单
     */
    public void getFoodMenu(final int bid,final int timeType){
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setShowLoadding(true);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<FoodData>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<FoodData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            LogUtils.e(result.toString());
                            mvpView.foodsMenu(result.getData());
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid", bid);

                long time = 0;
                int type = 0;
                switch (timeType){
                    case 1:
                        time = System.currentTimeMillis();
                        type = 1;
                        break;
                    case 2:
                        time = System.currentTimeMillis();
                        type = 2;
                        break;
                    case 3:
                        time = getTomorrowTime();
                        type = 1;
                        break;
                    case 4:
                        time = getTomorrowTime();
                        type = 2;
                        break;
                }
                param.addParam("time", time+"");
                param.addParam("type", type);

                HttpRequest.executePost(httpCallBack, "/bz/getMenu.action", param);
            }
        };
    }


    private long getTomorrowTime(){
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd ");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.roll(Calendar.DAY_OF_YEAR, 1);
        String format1 = df1.format(calendar1.getTime());
        try {
            return df1.parse(format1).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
