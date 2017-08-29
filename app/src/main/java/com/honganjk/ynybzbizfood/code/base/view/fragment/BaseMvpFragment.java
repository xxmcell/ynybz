package com.honganjk.ynybzbizfood.code.base.view.fragment;

import android.os.Bundle;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;

/**
 * Created by Administrator on 2016/8/13.
 */

public abstract class BaseMvpFragment<V extends BaseView, T extends BasePresenter<V>> extends BaseFragment {

    public T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPressenter();
        if (presenter != null) {
            presenter.onCreate((V) this);
        }
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if (presenter != null) {
            presenter.onLowMemory();
        }
        super.onLowMemory();
    }

    /**
     * 实例化Presenter对象
     *
     * @return
     */
    public abstract T initPressenter();
}
