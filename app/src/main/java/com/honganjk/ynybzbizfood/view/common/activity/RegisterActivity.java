package com.honganjk.ynybzbizfood.view.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.pressenter.othre.RegisterPresenter;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.utils.other.Validators;
import com.honganjk.ynybzbizfood.view.common.iview.IRegisterView;

import butterknife.BindView;


/**
 * 注册
 */
public class RegisterActivity extends BaseMvpActivity<IRegisterView, RegisterPresenter> implements IRegisterView {
    public EditHelper editHelper = new EditHelper(this);
    @BindView(R.id.activity_register_phone)
    EditText activityRegisterPhone;
    @BindView(R.id.activity_register_password)
    EditText activityRegisterPassword;

    public static void startUI(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
    }


    /*
     * 初始化view
     */

    public void initView() {
        toolbar.setTitle("注册");
        toolbar.setBack(this);
        editHelper.addEditHelperData(new EditHelper.EditHelperData(activityRegisterPhone, Validators.getFixLength(11), R.string.inputPhone));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(activityRegisterPassword, Validators.getLengthSRegex(4, 8), R.string.inputCode));

    }


    @Override
    public void parseIntent(Intent intent) {

    }


    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public RegisterPresenter initPressenter() {
        return new RegisterPresenter();
    }

    @Override
    public void isVerification(boolean whetherVerification) {
        if (whetherVerification) {
            //CountdownUtils.recordTime((TextView) findViewById(R.id.activity_register_send_verification));
        }

    }

    @Override
    public void receiverUserData(UserInfo UserData) {

        UserData.save(UserData);
    }

    @Override
    public void clearData() {

    }


}
