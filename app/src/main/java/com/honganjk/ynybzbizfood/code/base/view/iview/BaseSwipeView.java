package com.honganjk.ynybzbizfood.code.base.view.iview;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Administrator on 2016/8/8.
 * <p>
 * 下拉刷新的基类接口
 */

public interface BaseSwipeView extends BaseView, SwipeRefreshLayout.OnRefreshListener {


    /**
     * 获取下拉刷新控件
     */
    SwipeRefreshLayout getSwipeRefreshLayout();


}
