package com.honganjk.ynybzbizfood.code.base.presenter;


import com.honganjk.ynybzbizfood.code.base.view.iview.IListView;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/8.
 */

public class ListPresenter<T> extends BasePresenter<IListView<T>> {
    public void getHttpListData(final boolean isStart) {
        if (isStart) {
            mvpView.clearPagingData();
        }
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(false)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                .setStatusChangListener(mvpView.getStatusChangListener())
                .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout());
        final HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<T>>>(buider) {
            @Override
            public void onSuccess(HttpResult<List<T>> result) {
                super.onSuccess(result);
                //进行data处理
                if (result.isSucceed()) {
                    if (isStart) {
                        mvpView.clearData();
                    }
                    mvpView.upDataUI((List<T>) mvpView.getValidData(result.getData()));
                }
            }
        };
        Map<String, String> map = new HashMap<>();
        addPageData(map);
        mvpView.addSubscription(HttpRequest.executePost(httpCallBack, map));
    }


}
