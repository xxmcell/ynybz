package com.honganjk.ynybzbizfood.pressenter.common;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.BaseHttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.other.view.IOtherView;

import java.util.List;


/**
 * 公共的p
 *
 * @param <V>
 */
public class CommonPresent<V extends BaseSwipeView> extends BasePresenter<V> {
    /**
     * 获取banner页
     * site	可选,int	所属模块，1-首页；2-订餐业务；3-护理业务
     *
     * @param type
     */
    public void getAdvertisement(int type) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                .setHttpHead(HeadType.NULL_HEAD)
                .setShowLoadding(false);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<BannerData>>>(buider) {

            @Override
            public void onSuccess(HttpResult<List<BannerData>> result) {
                super.onSuccess(result);
                try {
                    if (result.isSucceed() && mvpView instanceof IOtherView.IAdverdisement) {
                        ((IOtherView.IAdverdisement) mvpView).getAdvertisement(result.getData());
                    } else {
                        ((BaseActivity) mvpView).showWarningMessage(result.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * 获得轮播图片
         *
         * /nurse/activities.action
         */
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("site", type);
        BaseHttpRequest.executePost(httpCallBack, "/nurse/activities.action", param);
    }


}
