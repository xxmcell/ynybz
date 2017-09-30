package com.honganjk.ynybzbizfood.view.store.order.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;

/**
 * Created by Administrator on 2017-09-29.
 */

public abstract class textBaseFragment <V extends BaseView, T extends BasePresenter<V>> extends Fragment {
    public T presenter;

    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);

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
