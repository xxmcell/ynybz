package com.honganjk.ynybzbizfood.code.base.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.iview.IActivityAndFragment;
import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.utils.ui.ActivityUtils;
import com.honganjk.ynybzbizfood.utils.ui.SnackbarUtil;
import com.honganjk.ynybzbizfood.utils.ui.SystemBarTintManager;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.widget.appbarlayout.SupperToolBar;
import com.honganjk.ynybzbizfood.widget.dialog.LoadDialog;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;
import com.honganjk.ynybzbizfood.widget.empty_layout.MyOnLoadingAndRetryListener;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity implements IActivityAndFragment {
    /**
     * **
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：请求CODE
     */
    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：订阅的集合，防止内存泄漏
     */
    private CompositeSubscription mCompositeSubscription;
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：布局切换
     */
    protected LoadingAndRetryManager loadingAndRetryManager = null;

    public SystemBarTintManager systemBarTintManager;
    /**
     * **
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：用于显示提示
     */

    private LoadDialog loadDialog;
    public Activity mActivity;
    @Nullable
    @BindView(R.id.toolbar)
    protected SupperToolBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        setTranslucentStatus(getStatusBarEnable(), getStatusBarResource());
        //初始化布局文件
        setContentView(getContentView());
        //初始化意图
        parseIntent(getIntent());

        //初始化view
        initView();

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
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：解析意图
     */
    public abstract void parseIntent(Intent intent);


    /**
     * **
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取状态栏颜色
     */


    public int getStatusBarResource() {
        return R.color.main_color;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取状态栏高度
     */


    public int getStatusBarPaddingTop() {
        return UiUtils.getStatusHeight() - 2;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取状态栏是否开启
     */


    public boolean getStatusBarEnable() {
        return true;
    }


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
        return findViewById(R.id.switchRoot);
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



    private void setTranslucentStatus(boolean on, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏透明 需要在创建SystemBarTintManager 之前调用。
            Window win = getWindow();
            WindowManager
                    .LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
            systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setNavigationBarTintEnabled(true);
            systemBarTintManager.setTintResource(color);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        UiUtils.applyFont(UiUtils.getRootView(this));
        setRootViewPadding(getStatusBarPaddingTop());
        loadingAndRetryManager = LoadingAndRetryManager.getLoadingAndRetryManager(getSwitchRoot(), new MyOnLoadingAndRetryListener(this, getOnRetryClickListion()));
        if (toolbar != null) {

        }

    }

    public void setRootViewPadding(int top) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getStatusBarEnable()) {
            UiUtils.getRootView(this).setPadding(0, top, 0, 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        /**
         * 销毁网络访问的订阅
         */
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：是否登录
     *
     * @param isToLogin : 是否跳转到登录界面
     */

    @Override
    public boolean isLogin(boolean isToLogin) {
        if (!UserInfo.isSLogin()) {
            if (isToLogin) {
                LoginActivity.startUI(this);
            }
            return false;
        } else {
            return true;
        }
    }


    /**
     * **
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：RXJava 的订阅集合  用于销毁
     */

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：添加一个订阅
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }


    /**
     * 方法功能：显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    public void showDialog(String msg, boolean isCancelable) {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
        // 判断是否加载对话框
        if (!isFinishing() && ActivityUtils.isForeground(this)) {
            if (loadDialog == null) {
                loadDialog = new LoadDialog(this);
            }
            loadDialog.setContent(StringUtils.dataFilter(msg, getResources().getString(R.string.loadding)));
            loadDialog.setCancelable(isCancelable);
            try {
                loadDialog.show();
            } catch (Exception e) {

            }

        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showDialog() {
        showDialog(null);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：销毁对话框
     */
    public void dismissDialog() {
        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能： Snackbar 显示错误信息
     */
    public void httpError(String errString) {
        SnackbarUtil.showLong(this, errString, SnackbarUtil.Error).show();
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能： Snackbar 显示信息
     */
    public void showMessage(BaseHttpResponse result) {
        if (result != null) {
            SnackbarUtil.showLong(this, result.getMsg(), SnackbarUtil.Error).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示错误信息
     */

    public void showErrorMessage(String result) {
        if (result != null) {
            SnackbarUtil.showLong(this, result, SnackbarUtil.Error).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningMessage(String result) {
        if (result != null) {
            SnackbarUtil.showLong(this, result, SnackbarUtil.Warning).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示信息 并自动班的是否是toast
     */
    public void showMessageFinish(BaseHttpResponse result) {
        if (result.isSucceed()) {
            ToastUtils.showShort(this, result.getMsg());
        } else {
            SnackbarUtil.showLong(this, result.getMsg(), SnackbarUtil.Error).show();
        }
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorSnackbar(String result) {
        showSnackbar(result, SnackbarUtil.Error, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorSnackbar(String result, boolean isFinish) {
        showSnackbar(result, SnackbarUtil.Error, isFinish);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningSnackbar(String result) {
        showSnackbar(result, SnackbarUtil.Warning, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningSnackbar(String result, boolean isFinish) {
        showSnackbar(result, SnackbarUtil.Warning, isFinish);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示提示信息
     */
    public void showInforSnackbar(String result, boolean isFinish) {
        showSnackbar(result, SnackbarUtil.Info, isFinish);
    }


    /**
     * 显示正确提示信息
     *
     * @param result
     */
    public void showInforSnackbar(String result) {
        showInforSnackbar(result, false);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示信息
     */
    public void showSnackbar(String result, int type, boolean isFinish) {
        showSnackbar(UiUtils.getDecorView(this), result, type, isFinish, null);
    }

    /**
     * **
     * 创建时间: 2016/9/22 11:12
     * <p>
     * 方法功能：显示信息
     */
    public void showSnackbar(View view, String result, int type, boolean isFinish, final Snackbar.Callback callback) {
        SnackbarUtil.showSnackbar(this, view, result, type, isFinish, callback);
    }
}
