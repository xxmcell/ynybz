package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.utils.other.AssetsUtil;

import butterknife.BindView;

public class ProtocolActivity extends BaseMvpActivity {
    @BindView(R.id.tvTxt)
    TextView tvTxt;

    public static void startUi(Context context) {
        Intent intent = new Intent(context, ProtocolActivity.class);
        context.startActivity(intent);
    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_protocol;
    }

    @Override
    public void initView() {
        toolbar.setTitle("用户协议");
        toolbar.setBack(this);
        String fileName = "xieyi.txt";
        tvTxt.setText(AssetsUtil.getTxtFromAssets(this, fileName));
    }

    @Override
    public void parseIntent(Intent intent) {

    }
}
