package com.honganjk.ynybzbizfood.view.tour.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.MyBaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.LoginData.UserInfoData;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.Mypressenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.AccountActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.ChongZhiActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;
import com.honganjk.ynybzbizfood.view.tour.me.activity.CommonPassengerActivity;
import com.honganjk.ynybzbizfood.view.tour.me.activity.MyKeepsActivity;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.honganjk.ynybzbizfood.code.Global.SERVER_PHONE;
import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * Created by Administrator on 2017-11-07.
 */

public class MeFragment extends MyBaseFragment<MyParentInterfaces.IMy, Mypressenter>
        implements MyParentInterfaces.IMy {
    private static final int REQUEST_CODE_PASSENGER = 100;
    private static final int REQUEST_CODE_COLLECTION = 200;
    @BindView(R.id.back)
    View back;     //返回
    @BindView(R.id.tv_remain_money)
    TextView tvRemainMoney;     //余额
    @BindView(R.id.tv_go_addmoney)
    TextView tvGoAddmoney;     //充值
    @BindView(R.id.iv_head)
    ImageView ivHead;           //头像
    @BindView(R.id.user_name)
    TextView userName;          //名称
    @BindView(R.id.ll_stroke)
    LinearLayout llStroke;      //我的行程
    @BindView(R.id.ll_common_passenger)
    LinearLayout llCommonPassenger;  //常用旅客
    @BindView(R.id.ll_my_collection)
    LinearLayout llMyCollection;  //我的收藏
    @BindView(R.id.ll_call_us)
    LinearLayout llCallUs;  //联系我们
    Unbinder unbinder;
    @BindView(R.id.collection_num)
    TextView collectionNum; //收藏数
    @BindView(R.id.passenger_num)
    TextView passengerNum; //联系人数

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_tour_my, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否登录
        if (isLogin(false)) {
            //我要请求网络了获取用户信息
            presenter.getUserInfo();
        } else {
            userName.setText("登录");
            tvRemainMoney.setText("0");
            addData();
        }
    }

    @Override
    public boolean isLogin(boolean isToLogin) {
        if (!UserInfo.isSLogin()) {
            if (isToLogin) {
                LoginActivity.startUI(getActivity());
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void showDialog(String msg, boolean isCancelable) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return null;
    }

    @Override
    public void addSubscription(Subscription s) {

    }

    @Override
    public CompositeSubscription getCompositeSubscription() {
        return null;
    }

    @Override
    public void showErrorSnackbar(String result) {

    }

    @Override
    public void showErrorSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result) {

    }

    @Override
    public void showSnackbar(String result, int type, boolean isFinish) {

    }

    @Override
    public void getUserInfo(UserInfoData userInfoData) {
        addData();
    }

    @Override
    public Mypressenter initPressenter() {
        return new Mypressenter();
    }

    private void addData() {
        if (userData.isLogin()) {
            userName.setText(userData.getName());
            tvRemainMoney.setText("¥" + (String.valueOf(userData.getBalance())));
            passengerNum.setText(userData.getPassengerNum() + "个");
            collectionNum.setText(userData.getKeepsNum() + "个");
            if (!StringUtils.isBlank(userData.getImg())) {
                GlideUtils.showCircle(ivHead, userData.getImg());
            } else {
                ivHead.setImageResource(R.mipmap.zhan_wei);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back, R.id.user_name, R.id.iv_head, R.id.tv_go_addmoney, R.id.ll_stroke, R.id.ll_common_passenger, R.id.ll_my_collection, R.id.ll_call_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;

            case R.id.user_name:
                //条转界面
                if (isLogin(true)) {
                    AccountActivity.startUi(getActivity(), userData.getImg(), userData.getName());
                }
                break;
            case R.id.iv_head:
                //条转界面
                if (isLogin(true)) {
                    AccountActivity.startUi(getActivity(), userData.getImg(), userData.getName());
                }
                break;
            case R.id.tv_go_addmoney:
                //去充值
                if (isLogin(true)) {
                    ChongZhiActivity.startUi(getActivity());
                }
                break;
            case R.id.ll_stroke: //我的行程
                break;
            case R.id.ll_common_passenger://常用旅客
                if (isLogin(true)) {
                    //      CommonPassengerActivity.startForResultUi(getActivity(), REQUEST_CODE_PASSENGER, -1);
                    Intent intent = new Intent(getContext(), CommonPassengerActivity.class);
                    intent.putExtra("content", -1);
                    startActivityForResult(intent, REQUEST_CODE_PASSENGER);
                }

                break;
            case R.id.ll_my_collection://我的收藏
                if (isLogin(true)) {
//                    MyKeepsActivity.startUiForResult(getActivity(), REQUEST_CODE_COLLECTION);
                    Intent intent = new Intent(getContext(), MyKeepsActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_COLLECTION);
                }
                break;
            case R.id.ll_call_us:
                //拨打电话
                DeviceUtil.callByPhone(getActivity(), SERVER_PHONE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (requestCode == REQUEST_CODE_PASSENGER) { //常用旅客
                    passengerNum.setText(data.getIntExtra("passengerNum", 0) + "个");
                    userData.setPassengerNum(data.getIntExtra("passengerNum", 0));
                } else {//收藏
                    collectionNum.setText(data.getIntExtra("keepNum", 0) + "个");
                    userData.setKeepsNum(data.getIntExtra("keepNum", 0));
                }
        }
    }
}
