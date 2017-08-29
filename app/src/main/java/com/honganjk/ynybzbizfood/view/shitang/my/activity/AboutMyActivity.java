package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.content.Context;
import android.content.Intent;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;

/**
 * Created by Administrator on 2017/3/6.
 */

public class AboutMyActivity extends BaseMvpActivity {

    public static void startUi(Context context) {
        Intent intent = new Intent(context,AboutMyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_aboutmy;
    }

    @Override
    public void initView() {
        toolbar.setTitle("关于我们");
        toolbar.setBack(this);
    }

    @Override
    public void parseIntent(Intent intent) {

    }
}
