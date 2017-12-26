package com.honganjk.ynybzbizfood.code.base.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.code.base.view.iview.IActivityAndFragment;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;
import com.honganjk.ynybzbizfood.widget.empty_layout.MyOnLoadingAndRetryListener;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/8/13.
 */

public abstract class BaseFragment extends Fragment implements IActivityAndFragment {
    /**
     * **
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：请求CODE
     */

    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);

    /**
     * **
     * 创建时间: 2016/9/22 11:15
     * <p>
     * 方法功能：宿主activity
     */


    protected BaseActivity activity;
    /**
     * **
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：布局
     */

    protected View view;

    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：布局切换
     */
    protected LoadingAndRetryManager loadingAndRetryManager = null;

    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：订阅的集合，防止内存泄漏
     */
    private CompositeSubscription mCompositeSubscription;


    /**
     * **
     * 创建时间: 2016/9/22 11:15
     * <p>
     * 方法功能：是否是回收利用的Fragment
     */
    protected boolean isRecycle = false;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            isRecycle = false;
            view = UiUtils.getInflaterView(activity, getContentView());
            unbinder = ButterKnife.bind(this, view);
            UiUtils.applyFont(view);
            loadingAndRetryManager = LoadingAndRetryManager.getLoadingAndRetryManager(getSwitchRoot(), new MyOnLoadingAndRetryListener(activity, getOnRetryClickListion()));
        } else {
            isRecycle = true;
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            initView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    public abstract int getContentView();

    /**
     * **
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    public abstract void initView();


    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    public OnRetryClickListion getOnRetryClickListion() {
        if (this instanceof OnRetryClickListion) {
            return (OnRetryClickListion) this;
        } else {
            return null;
        }
    }


    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取需要转化为loadingAndRetryManager的控件
     */
    public View getSwitchRoot() {
        return view.findViewById(R.id.switchRoot);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取页面切换的布局管理器
     */
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return loadingAndRetryManager;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        /**
         * 销毁网络访问的订阅
         */
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public boolean isLogin(boolean isToLogin) {
        return activity.isLogin(isToLogin);
    }

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    /**
     * 显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    public void showDialog(String msg, boolean isCancelable) {
        activity.showDialog(msg, isCancelable);
    }

    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    public void showDialog() {
        showDialog(null);
    }

    public void dismissDialog() {
        activity.dismissDialog();
    }


    /**
     * 显示错误信息
     */
    public void showErrorSnackbar(String result) {
        activity.showErrorSnackbar(result);
    }

    /**
     * 提示信息
     */
    public void showInforSnackbar(String result) {
        activity.showInforSnackbar(result);
    }

    /**
     * 显示警告信息
     */
    public void showWarningSnackbar(String result) {
        activity.showWarningSnackbar(result);
    }


    /**
     * 显示错误信息
     */
    public void showErrorSnackbar(String result, boolean isFinish) {
        activity.showErrorSnackbar(result, isFinish);
    }

    /**
     * 显示警告信息
     */
    public void showWarningSnackbar(String result, boolean isFinish) {

        activity.showWarningSnackbar(result, isFinish);
    }

    public void showSnackbar(String result, int type, boolean isFinish) {
        activity.showSnackbar(result, type, isFinish);
    }


}
