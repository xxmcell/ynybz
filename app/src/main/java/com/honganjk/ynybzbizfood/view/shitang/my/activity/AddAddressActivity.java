package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.AddAddressPresenter;
import com.honganjk.ynybzbizfood.utils.other.EmojiFilter;
import com.honganjk.ynybzbizfood.view.common.activity.SearchActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 添加地址
 * Created by Administrator on 2017/3/9.
 */

public class AddAddressActivity extends BaseMvpActivity<MyParentInterfaces.IAddAddress, AddAddressPresenter> implements MyParentInterfaces.IAddAddress {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_selectsex)
    RadioGroup rgSelectsex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.et_xiangaddress)
    EditText etXiangaddress;

    private int mSex = 1;
    private double latitude;
    private double longitude;
    private Intent intent;

    public static void startUi(Context context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        context.startActivity(intent);
    }

    public static void startForResultUi(Activity activity) {
        Intent intent = new Intent(activity, AddAddressActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    public AddAddressPresenter initPressenter() {
        return new AddAddressPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_addaddress;
    }

    @Override
    public void initView() {
        toolbar.setTitle("新增收货地址");
        toolbar.setBack(this);
        toolbar.addAction(1, "保存");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //请求网络
                getInputUserInfo();
                return true;
            }
        });

        if (userData != null) {
            etPhone.setText(userData.getMobile());
        }
    }

    @Override
    public void parseIntent(Intent intent) {
        this.intent = intent;
    }


    @OnClick({R.id.rb_man, R.id.rb_woman, R.id.et_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_man:
                mSex = 1;
                break;
            case R.id.rb_woman:
                mSex = 2;
                break;
            case R.id.et_address:
                //开启检索activity 拿回结果
                SearchActivity.startUI(this, 1);

                break;
        }
    }

    /**
     * 获取用户输入的信息
     */
    public void getInputUserInfo() {
        String iputName = etName.getText().toString().trim();
        String inputPhone = etPhone.getText().toString().trim();
        String inputAddress = etAddress.getText().toString().trim();
        String inputXiangAddress = etXiangaddress.getText().toString().trim();
        if (TextUtils.isEmpty(iputName)) {
            showInforSnackbar("请输入姓名");
            return;
        }
        if (inputPhone.length() != 11) {
            showInforSnackbar("手机号有误");
            return;
        }
        if (TextUtils.isEmpty(inputAddress)) {
            showInforSnackbar("请输入地址");
            return;
        }
        if (TextUtils.isEmpty(inputXiangAddress)) {
            showInforSnackbar("请输入详细地址");
            return;
        }
        if (EmojiFilter.containsEmoji(inputXiangAddress)) {
            showInforSnackbar("地址中不能包含表情字符");
            return;
        }

        commitData(inputPhone, iputName, mSex, inputAddress + inputXiangAddress, latitude, longitude);
    }

    public void commitData(String inputPhone, String iputName, int sex, String address, double latitude, double longitude) {
        presenter.commitData(inputPhone, iputName, sex, address, latitude, longitude);
    }

    @Override
    public void isCommitSccess(boolean bool) {

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void clearData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                String address = data.getStringExtra("address");
                etAddress.setText(address);
                latitude = data.getDoubleExtra("latitude", 0.0);
                longitude = data.getDoubleExtra("longitude", 0.0);
            }
        }
    }
}
