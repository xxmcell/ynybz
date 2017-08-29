package com.honganjk.ynybzbizfood.code.base.view.activity;

import android.os.Bundle;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;

/**
 * Created by Administrator on 2016/8/8.
 */

public abstract class BaseMvpActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseActivity {

    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = initPressenter();
        if (presenter != null) {
            presenter.onCreate((V) this);
        }
        super.onCreate(savedInstanceState);

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
