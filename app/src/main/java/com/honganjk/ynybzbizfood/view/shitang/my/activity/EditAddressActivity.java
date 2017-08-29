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
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.AddressBean;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.EditAddressPresenter;
import com.honganjk.ynybzbizfood.view.common.activity.SearchActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */

public class EditAddressActivity extends BaseMvpActivity<MyParentInterfaces.IEditAddress, EditAddressPresenter> implements MyParentInterfaces.IEditAddress {

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
    private AddressBean mUserinfo;

    public static void startUi(Context context) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        context.startActivity(intent);
    }

    public static void startForResultUi(Activity activity, AddressBean addressBean) {
        Intent intent = new Intent(activity, EditAddressActivity.class);
        intent.putExtra("userinfo", addressBean);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    public EditAddressPresenter initPressenter() {
        return new EditAddressPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_addaddress;
    }

    @Override
    public void initView() {
        toolbar.setTitle("编辑收货地址");
        toolbar.setBack(this);
        toolbar.addAction(1, "保存");
        latitude = mUserinfo.latitude;
        longitude = mUserinfo.longitude;
        etName.setText(mUserinfo.name);
        etName.setSelection(mUserinfo.name.length());//将光标移至文字末尾
        etPhone.setText(mUserinfo.contact);
        etAddress.setText(mUserinfo.address);
        mSex = mUserinfo.sex;
        ((RadioButton) rgSelectsex.getChildAt(mSex - 1)).setChecked(true);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //请求网络
                getInputUserInfo();
                return true;
            }
        });
    }

    @Override
    public void parseIntent(Intent intent) {
        this.intent = intent;
        mUserinfo = (AddressBean) intent.getSerializableExtra("userinfo");
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
                SearchActivity.startUI(this, 2);
                break;
        }
    }

    /**
     * 获取用户输入的信息
     */
    public void getInputUserInfo() {
        int id = mUserinfo.id;
        String iputName = etName.getText().toString().trim();
        String inputPhone = etPhone.getText().toString().trim();
        String inputAddress = etAddress.getText().toString().trim();
        String inputXiangAddress = etXiangaddress.getText().toString().trim();
        if (TextUtils.isEmpty(iputName)) {
            showInforSnackbar("请输入姓名", true);
            return;
        }
        if (inputPhone.length()!=11) {
            showInforSnackbar("手机号有误", true);
            return;
        }
        if (TextUtils.isEmpty(inputAddress)) {
            showInforSnackbar("请输入地址", true);
            return;
        }

        commitData(id, inputPhone, iputName, mSex, inputAddress + " " + inputXiangAddress, latitude, longitude);
    }

    public void commitData(int id, String inputPhone, String iputName, int sex, String address, double latitude, double longitude) {
        presenter.commitData(id, inputPhone, iputName, sex, address, latitude, longitude);
    }


    @Override
    public void clearData() {

    }

    @Override
    public void isEditSccess(boolean bool) {
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 2) {
                String address = data.getStringExtra("address");
                etAddress.setText(address);
                latitude = data.getDoubleExtra("latitude", 0.0);
                longitude = data.getDoubleExtra("longitude", 0.0);

            }
        }
    }
}
