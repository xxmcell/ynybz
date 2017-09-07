package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMainActivity;
import com.honganjk.ynybzbizfood.mode.javabean.LoginData.UserInfoData;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.Mypressenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.code.Global.SERVER_PHONE;
import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 说明:
 * 2017/3/2-18:51
 */
public class MyActivity extends BaseMainActivity<MyParentInterfaces.IMy, Mypressenter> implements MyParentInterfaces.IMy {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.tv_remain_money)
    TextView tvRemainMoney;
    @BindView(R.id.tv_address_num)
    TextView tvAddressNum;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, MyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //返回到主页
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addData();
    }

    @Override
    public Mypressenter initPressenter() {
        return new Mypressenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_my;
    }


    @Override
    public void initView() {
        super.initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //判断是否登录
        if (isLogin(false)) {
            //我要请求网络了获取用户信息
            presenter.getUserInfo();
        } else {
            userName.setText("登录");
            addData();
        }
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public int currentItem() {
        return 3;
    }


    @OnClick({R.id.iv_head, R.id.user_name, R.id.tv_go_addmoney, R.id.ll_adrrse, R.id.tv_aboutmy, R.id.tv_deal, R.id.tv_userfankui, R.id.ll_callphoen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                //条转界面
                if (isLogin(true)) {
                    AccountActivity.startUi(this, userData.getImg(), userData.getName());
                }
                break;
            case R.id.user_name:
                //跳转登录界面
                isLogin(true);
//                if (isLogin(true)) {
//                }
//                LoginActivity.startUI(this);

                break;
            case R.id.tv_go_addmoney:
                //去充值
                if (userData.isLogin()) {
                    ChongZhiActivity.startUi(this);
                } else {
                    LoginActivity.startUI(this);
                }

                break;
            case R.id.ll_adrrse:
                if (userData.isLogin()) {
                    SelectAddressActivity.startUi(this);
                } else {
                    LoginActivity.startUI(this);
                }
                break;
            case R.id.tv_aboutmy:
                //关于我们
                AboutMyActivity.startUi(this);
                break;
            case R.id.tv_deal:
                //用户协议
                ProtocolActivity.startUi(this);
                break;
            case R.id.tv_userfankui:
                //用户反馈
                UserFangKuiActivity.startUi(this);
                break;
            case R.id.ll_callphoen:
                //拨打电话
                DeviceUtil.callByPhone(this, SERVER_PHONE);
                break;
        }
    }

    @Override
    public void clearData() {

    }

    /**
     * 用户信息的回调
     *
     * @param userInfoData
     */
    @Override
    public void getUserInfo(UserInfoData userInfoData) {
        addData();
    }

    private void addData() {

        if (userData.isLogin()) {
            userName.setText(userData.getName());
            tvAddressNum.setText(userData.getImaddrNumgStr());
            tvRemainMoney.setText("¥" + (String.valueOf(userData.getBalance())));
            if (!StringUtils.isBlank(userData.getImg())) {
                GlideUtils.showCircle(ivHead, userData.getImg());
            } else {
                ivHead.setImageResource(R.mipmap.zhan_wei);
            }
        }
    }

    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }
}
