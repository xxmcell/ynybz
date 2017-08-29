package com.honganjk.ynybzbizfood.code.base.view.activity;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import butterknife.BindView;

/**
 * Created by yang on 2016/8/22.
 * 下拉刷新的基类
 */

public abstract class BaseSwipeActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V, T> implements SwipeRefreshLayout.OnRefreshListener, OnRetryClickListion {
    @Nullable
    @BindView(R.id.switchRoot)
    public SwipeRefreshLayout swipe;

    @Override
    public void initView() {
        if (swipe == null) {
            swipe = getSwipeRefreshLayout();
        }
        swipe.setOnRefreshListener(this);
    }

    /**
     * 获取下拉刷新控件
     */
    public abstract SwipeRefreshLayout getSwipeRefreshLayout();
}
