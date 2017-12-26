package com.honganjk.ynybzbizfood.view.tour.detail.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.third.PingPPUtils;
import com.honganjk.ynybzbizfood.pressenter.shitang.order.PayPresenter;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.ChongZhiActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.button.FlatButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 旅游在线支付
 * Created by Administrator on 2017-12-11.
 */

public class OnLinePayActivity extends BaseMvpActivity<OrderParentInterfaces.IPay, PayPresenter> implements OrderParentInterfaces.IPay {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.topUp)
    TextView topUp;
    @BindView(R.id.commit)
    FlatButton commit;
    @BindView(R.id.balance)
    RadioButton balance;

    /**
     * 支付类型 ：5为余额，1-支付宝；2-微信；3-银联
     */
    private int payType = 5;

    private String id;    //订单id
    private String price;//订单要支付金额，单位元，精确到分
    private String title;

    public static void StartUi(Context context, String id, String price, String title) {
        Intent intent = new Intent(context, OnLinePayActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("price", price);
        intent.putExtra("title", title);
        context.startActivity(intent);

    }

    @Override
    public void parseIntent(Intent intent) {
        id = intent.getStringExtra("id");
        price = intent.getStringExtra("price");
        title = intent.getStringExtra("title");

    }

    @Override
    public void clearData() {

    }

    /**
     * 余额支付
     *
     * @param bool
     * @param balance
     */
    @Override
    public void balancePay(boolean bool, double balance) {
        showInforSnackbar("支付成功", true);
        sendBroadcast(new Intent(Global.ST_PAY_SUCCEED));
    }

    /**
     * 得到订单要素
     *
     * @param data
     */
    @Override
    public void otherPay(String data) {
        PingPPUtils.createPayment(OnLinePayActivity.this, data);
    }

    /**
     * 获取余额
     */
    @Override
    public void getBalance(boolean bool, double balance) {
        this.balance.setText("帐户余额: ¥" + balance);
        if (Double.parseDouble(price) < balance) {
            topUp.setVisibility(View.GONE);
        } else {
            topUp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public PayPresenter initPressenter() {
        return new PayPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tour_pay;
    }

    @Override
    public void initView() {
        presenter.getBalance();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.balance:
                        payType = 5;
                        break;
                    case R.id.weiXi:
                        payType = 2;
                        break;
                    case R.id.zhiFuBao:
                        payType = 1;
                        break;
                    case R.id.yinLian:
                        payType = 3;
                        break;
                }
            }
        });
        tvTitle.setText(title);
        tvPrice.setText("￥" + price);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.topUp, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.topUp:
                //去充值
                ChongZhiActivity.startUi(this);
                break;
            case R.id.commit:
                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .title("提示")
                        .positiveText("确定")
                        .negativeColorRes(R.color.gray)
                        .negativeText("取消")
                        .content("您确定要支付吗？")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                switch (payType) {
                                    case 5:
                                        presenter.tourBalancePay(Integer.parseInt(id));
                                        break;
                                    case 1:
                                    case 3:
                                    case 2:
                                        presenter.otherPayTour(payType, Integer.parseInt(id), null);
                                        break;
                                }
                            }
                        })
                        .build();
                materialDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //支付成功的判断
        if (PingPPUtils.judgePaymentStatus(this, requestCode, resultCode, data) == 1) {
            sendBroadcast(new Intent(Global.ST_PAY_SUCCEED));
        }
    }
}
