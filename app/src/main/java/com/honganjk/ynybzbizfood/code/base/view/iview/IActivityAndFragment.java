package com.honganjk.ynybzbizfood.code.base.view.iview;

import android.view.View;

import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/8/17.
 */

public interface IActivityAndFragment {
    //获取需要转化为loadingAndRetryManager的控件
    public View getSwitchRoot();

    /**
     * 显示不同的界面布局 监听器
     */
    OnRetryClickListion getOnRetryClickListion();


     boolean isLogin(boolean isToLogin);

     void addSubscription(Subscription s);

     CompositeSubscription getCompositeSubscription();


}
