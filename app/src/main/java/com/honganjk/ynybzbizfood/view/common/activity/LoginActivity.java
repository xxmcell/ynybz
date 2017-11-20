package com.honganjk.ynybzbizfood.view.common.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.ActivityManager;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.othre.LoginPresenter;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.other.CountdownUtils;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.utils.other.Validators;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.common.iview.IloginView;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.honganjk.ynybzbizfood.wxapi.WXEntryActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;
import static com.honganjk.ynybzbizfood.wxapi.WXEntryActivity.WX_API;

/**
 * 说明:登录页面
 * 360621904@qq.com 杨阳 2017/3/3  14:33
 */
public class LoginActivity extends BaseMvpActivity<IloginView, LoginPresenter> implements IloginView {
    public EditHelper editHelper = new EditHelper(this);
    @BindView(R.id.phone)
    TextInputLayout phone;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.commitLogin)
    TextView commitLogin;

    //是否是绑定微信号
    boolean mBoundWx = false;
    boolean mIsMainHomeActivity = false;
    MyBroadcast myB = new MyBroadcast();
    IntentFilter intentFilter = new IntentFilter();

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    /**
     * 登录成功关闭当前页
     *
     * @param context
     */
    public static void startUI(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    /**
     * 需要返回到主页
     *
     * @param context
     * @param isMainHomeActivity
     */
    public static void startUI(Context context, boolean isMainHomeActivity) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("isMainHomeActivity", isMainHomeActivity);
        context.startActivity(intent);
    }

    /**
     * 微信登录
     *
     * @param context
     * @param WXId
     */
    public static void startUI(Context context, String WXId) {
        Intent intent = new Intent(context, LoginActivity.class);
        Bundle bundle=new Bundle();

        intent.putExtra("boundWx", true);
        intent.putExtra("WXId", WXId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentFilter.addAction(Global.LOGIN_SUCCEED);
        registerReceiver(myB, intentFilter);

        initView();
    }

    @Override
    public void parseIntent(Intent intent) {
        mBoundWx = intent.getBooleanExtra("boundWx", mBoundWx);
        mIsMainHomeActivity = intent.getBooleanExtra("isMainHomeActivity", mIsMainHomeActivity);

    }

    /*
     * 初始化view
     */
    public void initView() {
        if (mBoundWx) {
            toolbar.setTitle("绑定手机号");
            commitLogin.setText("绑定手机号");
            findViewById(R.id.hint).setVisibility(View.GONE);
            findViewById(R.id.wechat).setVisibility(View.GONE);
        } else {
            toolbar.setTitle("登录");
        }
        toolbar.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsMainHomeActivity) {
                    ActivityManager.getInstance().exitAllActivity();
                    MainHomeActivity.startUI(LoginActivity.this);
                } else {
                    finish();
                }
            }
        });
        editHelper.addEditHelperData(new EditHelper.EditHelperData(phone, Validators.getLenghMinRegex(1), R.string.inputPhone));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(password, Validators.getLenghMinRegex(1), R.string.inputCode));
        AnimUtils.pressAnimationListener(findViewById(R.id.wechat));
    }

    @Override
    public LoginPresenter initPressenter() {
        return new LoginPresenter();
    }

    @Override
    public void clearData() {
    }

    @Override
    public void getVerification(boolean whetherVerification) {
        if (whetherVerification) {
            CountdownUtils.recordTime((TextView) findViewById(R.id.verification));
        }
    }

    @Override
    public void login(boolean isSucceed) {
        sendBroadcast(new Intent(Global.LOGIN_SUCCEED));
        ToastUtils.getToastLong("登录成功");
        MobclickAgent.onProfileSignIn(String.valueOf(userData.getCode()));
        if (mIsMainHomeActivity) {
            ActivityManager.getInstance().exitAllActivity();
            MainHomeActivity.startUI(LoginActivity.this);
            return;
        } else {
            finish();
        }
        //这个有问题先用Toast代替
//        SnackbarUtil.showSnackbar(this, UiUtils.getDecorView(this), "登录成功", SnackbarUtil.Info, true, new Snackbar.Callback() {
//            @Override
//            public void onDismissed(Snackbar transientBottomBar, int event) {
//                super.onDismissed(transientBottomBar, event);
//                if (mIsMainHomeActivity) {
//                    ActivityManager.getInstance().exitAllActivity();
//                    MainHomeActivity.startUI(LoginActivity.this);
//                    return;
//                } else {
//                    finish();
//                }
//            }
//        });


    }

    @Override
    public void boundWX(boolean isSucceed) {
        presenter.login(editHelper.getText(R.id.phone), editHelper.getText(R.id.password));
    }

    @OnClick({R.id.verification, R.id.commitLogin, R.id.wechat})
    public void onClick(View view) {
        if (phone.getEditText().getText().toString().length() != 11 && view.getId() != R.id.wechat) {
            AnimUtils.shakeLeft(phone, 0.85f, 6f);
            showWarningMessage("请正确输入手机号");
            return;
        }
        switch (view.getId()) {
            case R.id.verification:
                if (editHelper.check(R.id.phone)) {
                    presenter.getVerification(editHelper.getText(R.id.phone));
                }
                break;
            case R.id.commitLogin:
                if (editHelper.check()) {
                    //绑定微信号
                    if (mBoundWx) {
                        presenter.boundWX(getIntent().getStringExtra("WXId"), editHelper.getText(R.id.phone), editHelper.getText(R.id.password));
                        //正常登录
                    } else {
                        presenter.login(editHelper.getText(R.id.phone), editHelper.getText(R.id.password));
                    }
                }
                break;
            case R.id.wechat:

                if (WX_API.isWXAppInstalled()) {
                    WXEntryActivity.startWXUI(LoginActivity.this, REQUEST_CODE);
                } else {
                    showWarningMessage("您还没有安装微信,请安装后再试");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myB);
    }

    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Global.LOGIN_SUCCEED)) {
                finish();
            }
        }
    }

}
