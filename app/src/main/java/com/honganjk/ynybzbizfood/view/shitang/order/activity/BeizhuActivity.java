package com.honganjk.ynybzbizfood.view.shitang.order.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/13.
 */

public class BeizhuActivity extends BaseMvpActivity {
    @BindView(R.id.et_beizhu)
    EditText etBeizhu;

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.beizhuactivity;
    }

    @Override
    public void initView() {
        toolbar.setBack(this);
        toolbar.setTitle("备注");
        toolbar.addAction(1, "保存");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String beizhu = etBeizhu.getText().toString().trim();
                Intent intent = getIntent();
                intent.putExtra("beizhu",beizhu);
                setResult(RESULT_OK,intent);
                finish();
                return true;
            }
        });
    }

    @Override
    public void parseIntent(Intent intent) {
        String oldbeizhu = intent.getStringExtra("oldbeizhu");
        if(!TextUtils.isEmpty(oldbeizhu)) {
            etBeizhu.setText(oldbeizhu);
        }
    }

    public static void startUiForresult(Activity context,int requsetcode,String beizhu) {
        Intent intent = new Intent(context,BeizhuActivity.class);
        intent.putExtra("oldbeizhu", beizhu);
        context.startActivityForResult(intent,requsetcode);
    }
}
