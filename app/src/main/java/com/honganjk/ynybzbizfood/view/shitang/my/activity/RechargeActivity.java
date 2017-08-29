package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.PromotionInfoBean;
import com.honganjk.ynybzbizfood.mode.third.PingPPUtils;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.RechargePresenter;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;
import com.honganjk.ynybzbizfood.widget.button.FlatButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值
 * <p>
 * 27、提交充值支付要素
 * 接口：/pay/getRecharge.action
 * 开发	http://ur.honganjk.com/pay/getRecharge.action
 * 生产	https://urapi.honganjk.com/pay/getRecharge.action
 * code	必选 	单一用户唯一码,用户登录接口返回
 * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
 * 参数 	约束	说明
 * pay	必选,int	支付渠道：1-支付宝；2-微信；3-银联
 * real	必选，double	充值金额，真正的支付金额
 * obtain	可选，double	充值总共可以获得的金额，为优惠活动预留，可以不填，表示无活动，充值多少即多少
 */

public class RechargeActivity extends BaseMvpActivity<MyParentInterfaces.IRecharge, RechargePresenter> implements MyParentInterfaces.IRecharge {
    @BindView(R.id.tie_money)
    TextView tieMoney;
    @BindView(R.id.rb_weixi)
    RadioButton rbWeixi;
    @BindView(R.id.rb_zhifubao)
    RadioButton rbZhifubao;
    @BindView(R.id.rb_yinlian)
    RadioButton rbYinlian;
    @BindView(R.id.rg_selecttondao)
    RadioGroup rgSelecttondao;
    @BindView(R.id.commit)
    FlatButton commit;
    private int mPayTongDao = 2;  //默认微信充值
    private Double mDoubleMoney;
    private PromotionInfoBean chonzhiyouhui;

    public static void startUi(Context context, PromotionInfoBean promotionInfoBean) {
        Intent intent = new Intent(context, RechargeActivity.class);
        intent.putExtra("chonzhiyouhui", promotionInfoBean);
        context.startActivity(intent);
    }


    @Override
    public RechargePresenter initPressenter() {
        return new RechargePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView() {
        toolbar.setTitle("去充值");
        toolbar.setBack(this);
        tieMoney.setText("充" + chonzhiyouhui.getReality() + "送" + chonzhiyouhui.getExtra());
        commit.setText("确认支付" + chonzhiyouhui.getReality());
    }

    @Override
    public void parseIntent(Intent intent) {
        chonzhiyouhui = intent.getParcelableExtra("chonzhiyouhui");
    }


    @OnClick({R.id.rb_weixi, R.id.rb_zhifubao, R.id.rb_yinlian, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_weixi:
                mPayTongDao = 2;
                break;
            case R.id.rb_zhifubao:
                mPayTongDao = 1;
                break;
            case R.id.rb_yinlian:
                mPayTongDao = 3;
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
                                commitData();
                            }
                        })
                        .build();
                materialDialog.show();

                break;
        }
    }

    /**
     * 提交数据
     */
    private void commitData() {
        presenter.postData(mPayTongDao, chonzhiyouhui.getReality(), chonzhiyouhui.getExtra());
    }

    @Override
    public void backZhifuYaoSui(String str) {
        //支付要素返回在这里
        PingPPUtils.createPayment(this, str);

    }

    @Override
    public void clearData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PingPPUtils.judgePaymentStatus(this, requestCode, resultCode, data);
    }
}
