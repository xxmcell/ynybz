package com.honganjk.ynybzbizfood.code.base.view.iview;

import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/8/8.
 */

public interface BaseView {
    boolean isLogin(boolean isToLogin);

    /**
     * 显示对话框，用于网络请求
     */
    void showDialog(String msg, boolean isCancelable);

    /**
     * 显示对话框
     */
    void showDialog(String msg);

    /**
     * 显示对话框
     */
    void showDialog();

    /**
     * 关闭加载中对话框
     */
    void dismissDialog();


    /**
     * 清空数据  一般在列表使用  但是也可以作为其他的界面使用
     */
    void clearData();

    /**
     * 获取自动切换加载中布局的管理器
     */

    LoadingAndRetryManager getLoadingAndRetryManager();

    /**
     * 网络回调保存进集合，尽量在P里面调用
     *
     * @param s
     */
    public void addSubscription(Subscription s);

    public CompositeSubscription getCompositeSubscription();

    /**
     * 显示错误信息
     */
    void showErrorSnackbar(String result);

    void showErrorSnackbar(String result, boolean isFinish);

    /**
     * 显示警告信息
     */
    void showWarningSnackbar(String result, boolean isFinish);

    /**
     * 显示警告信息
     */
    void showWarningSnackbar(String result);

    /**
     * 基本的snackbar显示
     *
     * @param result
     * @param type
     * @param isFinish
     */
    void showSnackbar(String result, int type, boolean isFinish);



}
