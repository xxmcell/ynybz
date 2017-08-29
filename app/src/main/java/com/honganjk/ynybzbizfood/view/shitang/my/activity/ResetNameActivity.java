package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.ResetNamePresenter;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.utils.other.Validators;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 说明: 重置姓名
 * 2017/3/7-19:56
 */
public class ResetNameActivity extends BaseMvpActivity<MyParentInterfaces.IRename, ResetNamePresenter> implements MyParentInterfaces.IRename {
    @BindView(R.id.til_inptname)
    TextInputLayout tilInptname;
    @BindView(R.id.iv_delet_name)
    ImageView ivDeletName;
    EditHelper editHelper = new EditHelper(this);

    public static void startUiForRseult(Activity activity, String name, int requestCode) {
        Intent intent = new Intent(activity, ResetNameActivity.class);
        intent.putExtra("name", name);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public ResetNamePresenter initPressenter() {
        return new ResetNamePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.resetname;
    }

    @Override
    public void initView() {
        toolbar.setTitle("昵称修改");
        toolbar.setBack(this);
        toolbar.addAction(1, "保存");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //关闭 拿到结果 ,请求网络更该名字
                if (editHelper.check()) {
                    presenter.saveNewName(null, editHelper.getText(R.id.til_inptname));
                }
                return false;
            }
        });
        editHelper.addEditHelperData(new EditHelper.EditHelperData(tilInptname, Validators.getLenghMinRegex(1), "请输入名称"));
    }

    @Override
    public void parseIntent(Intent intent) {
        tilInptname.getEditText().setText(intent.getStringExtra("name"));
    }

    @OnClick(R.id.iv_delet_name)
    public void onClick() {
        tilInptname.getEditText().setText("");
    }

    /**
     * 修改名字的回调
     */
    @Override
    public void saveNewname(boolean bool) {
        Intent intent = getIntent();
        intent.putExtra("name", editHelper.getText(R.id.til_inptname));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void clearData() {

    }
}
