package com.honganjk.ynybzbizfood.view.shitang.order.activity;

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
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.mode.third.PingPPUtils;
import com.honganjk.ynybzbizfood.pressenter.shitang.order.PayPresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils2;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.ChongZhiActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 注释说明: 支付的页面
 * 阳：2017/3/13-16:42
 */
public class PayActivity extends BaseMvpActivity<OrderParentInterfaces.IPay, PayPresenter> implements OrderParentInterfaces.IPay {

    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.orderName)
    TextView orderName;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.balance)
    RadioButton balance;
    OrderPayData mData;
    @BindView(R.id.topUp)
    TextView topUp;
    /**
     * 支付类型 ：4为余额，1-支付宝；2-微信；3-银联
     */
    private int payType = 4;

    public static void startUI(Context context, OrderPayData data) {
        Intent intent = new Intent(context, PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public void parseIntent(Intent intent) {
        toolbar.setTitle("在线支付");
        toolbar.setBack(this);
        mData = (OrderPayData) intent.getSerializableExtra("data");
        if (mData != null) {
            if (mData.getOrderType() == 1 || mData.getOrderType() == 2) {
                GlideUtils.setImageBitmapRound(picture, mData.getImgeUrl(), 10);
                shopName.setText(mData.getBizName());
                if (mData.getOrderType() == 1) {
                    orderName.setText(mData.getUserInfo());
                } else {
                    orderName.setText("服务时间: " + mData.getServiceTime());
                }
                price.setText(mData.getPriceStr());
            }

            if (mData.getOrderType() == 3) {
                GlideUtils2.showRound(mData.getImgeUrl(), picture, 2);
                shopName.setText(mData.getProductName());
                orderName.setText("规格：" + mData.getProductType());
                orderName.setTextColor(getResources().getColor(R.color.gray_66));
                price.setText(mData.getPriceStr());
            }

        } else {
            showErrorSnackbar("订单错误", true);
        }
    }

    @Override
    public PayPresenter initPressenter() {
        return new PayPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_zhifu;
    }

    @Override
    public void initView() {

        presenter.getBalance();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.balance:
                        payType = 4;
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

    }


    @OnClick({R.id.topUp, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            //充值
            case R.id.topUp:
                ChongZhiActivity.startUi(this);
                break;
            //支付
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
                                    case 4:
                                        if (mData.getOrderType() == 2 || mData.getOrderType() == 1) {
                                            presenter.balancePay(mData.getPrice(), mData.getOrderId(), mData.getOrderType());
                                        }
                                        if (mData.getOrderType() == 3) {
                                            presenter.storeBalancePay(mData.getOrderId());
                                        }
                                        break;
                                    case 1:
                                    case 3:
                                    case 2:
                                        //订单类型，1-餐饮订单，2-护理订单，默认值为1
                                        if (mData.getOrderType() == 2) {
                                            presenter.otherPayNurse(payType, mData.getOrderId());
                                        }
                                        //陪护
                                        if (mData.getOrderType() == 1) {
                                            presenter.otherPay(payType, mData.getOrderId());
                                        }
                                        //商城
                                        if (mData.getOrderType() == 3) {
                                            presenter.storePayNurse(payType, mData.getOrderId());
                                        }
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
    public void balancePay(boolean bool, double balance) {
        showInforSnackbar("支付成功", true);
        sendBroadcast(new Intent(Global.ST_PAY_SUCCEED));
    }

    @Override
    public void otherPay(String data) {
        PingPPUtils.createPayment(PayActivity.this, data);
    }

    @Override
    public void getBalance(boolean bool, double balance) {
        this.balance.setText("帐户余额: ¥" + balance);
        if (mData.getPrice() < balance) {
            topUp.setVisibility(View.GONE);
        } else {
            topUp.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void clearData() {

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
