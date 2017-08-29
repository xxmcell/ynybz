package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.UserFanKuiPresenter;
import com.honganjk.ynybzbizfood.utils.ui.SnackbarUtil;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import butterknife.BindView;

/**
 * 34、提交用户反馈
 * 接口：/common/addMsg.action
 * 请求方式: http—get/post
 * 开发	http://ur.honganjk.com/common/addMsg.action
 * 生产	https://urapi.honganjk.com/common/addMsg.action
 * mobile	必选	手机号
 * msg	必选	反馈内容
 */

public class UserFangKuiActivity extends BaseMvpActivity<MyParentInterfaces.IUserFangKui,UserFanKuiPresenter> implements MyParentInterfaces.IUserFangKui {

    @BindView(R.id.user_fangkui)
    EditText userFangkui;
    @BindView(R.id.uesr_phone)
    EditText uesrPhone;

    public static void startUi(Context context) {
        Intent intent = new Intent(context, UserFangKuiActivity.class);
        context.startActivity(intent);
    }

    @Override
    public UserFanKuiPresenter initPressenter() {
        return new UserFanKuiPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_userfangkui;
    }

    @Override
    public void initView() {
        toolbar.setTitle("意见反馈");
        toolbar.setBack(this);
        toolbar.addAction(1, "提交");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getJianYiAndPostData();
                return true;
            }
        });
    }

    /**
     * 获取用户的建议
     */
    private void getJianYiAndPostData() {
        String jianyi = userFangkui.getText().toString().trim();
        String uesphone = uesrPhone.getText().toString().trim();
        if(TextUtils.isEmpty(uesphone)){
            SnackbarUtil.showLong(uesrPhone,"手机号码有误号码", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(jianyi)) {
            finish();
        } else {
            postData(jianyi, uesphone);
        }

    }

    /**
     * 请求网咯
     */
    private void postData(String jianyi, String uesphone) {
        presenter.postUesrFanKuiData(jianyi,uesphone);
    }

    @Override
    public void parseIntent(Intent intent) {

    }

    /**
     * 后台返回数据
     */
    @Override
    public void showData(boolean bool) {
        if(bool) {
            SnackbarUtil.showLong(uesrPhone,"提交成功", Snackbar.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void clearData() {

    }
}
