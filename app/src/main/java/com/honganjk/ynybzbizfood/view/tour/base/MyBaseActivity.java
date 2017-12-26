package com.honganjk.ynybzbizfood.view.tour.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;

/**
 * Created by Administrator on 2017-11-20.
 */

public abstract class MyBaseActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V,T> {
    public T presenter;

    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPressenter();
        if (presenter != null) {
            presenter.onCreate((V) this);
        }

    }

    @Override
    protected void onDestroy() {
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
