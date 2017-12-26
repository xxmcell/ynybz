package com.honganjk.ynybzbizfood.view.tour.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.base.LoopViewData;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.CommonPassengerBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.PassengerDetail;
import com.honganjk.ynybzbizfood.pressenter.tour.me.EditPassengerPresenter;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.common.activity.SearchActivity;
import com.honganjk.ynybzbizfood.view.tour.me.interfaces.TourMeInterface;
import com.honganjk.ynybzbizfood.widget.datepicker.CustomDatePicker;
import com.honganjk.ynybzbizfood.widget.dialog.LoopViewDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */

public class EditPassengerActivity extends BaseMvpActivity<TourMeInterface.MyAddPassengerInterface, EditPassengerPresenter> implements TourMeInterface.MyAddPassengerInterface {

    private static final String PASSENGERBEAN = "PassengerBean";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;  //标题
    @BindView(R.id.et_name)
    EditText etName;   //姓名
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;//生日
    @BindView(R.id.et_nation)
    EditText etNation;//国籍
    @BindView(R.id.tv_id_type)
    TextView tvIdType;//证件类型
    @BindView(R.id.et_id_num)
    EditText etIdNum;//证件号码
    @BindView(R.id.tv_passenger_type)
    TextView tvPassengerType;//旅客类型
    @BindView(R.id.et_phone)
    EditText etPhone;//联系电话
    @BindView(R.id.et_email)
    EditText etEmail;//联系邮箱
    @BindView(R.id.btn_save)
    Button btnSave; //提交按钮
    @BindView(R.id.tv_sex)
    TextView tvSex; //选择性别
    @BindView(R.id.et_address)
    TextView etAddress; //联系地址
    private CommonPassengerBean passengerBean;
    private CustomDatePicker customDatePicker; //时间选择器

    private LoopViewDialog loopViewDialog;
    private ArrayList<LoopViewData> loopDatas = new ArrayList();

    /**
     * @param activity 新增开启
     */
    public static void startForResultUi(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, EditPassengerActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @param activity 修改开启
     */
    public static void startForResultUi(Activity activity, int requestCode, CommonPassengerBean data) {
        Intent intent = new Intent(activity, EditPassengerActivity.class);
        intent.putExtra(PASSENGERBEAN, data);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void clearData() {

    }

    @Override
    public void getPassengerDetail(PassengerDetail data) {
        if (data != null) {
            etName.setText(data.getName());
            tvIdType.setText(data.getCredentials());
            etNation.setText(data.getName());
            tvSex.setText(data.getSex() == 1 ? "先生" : "女士");
            etAddress.setText(data.getAddress());
            etPhone.setText(data.getMobile());
            etIdNum.setText(data.getSn());
            tvPassengerType.setText(data.getType() == 0 ? "成人" : (data.getType() == 1 ? "儿童" : "军人"));
            etEmail.setText(data.getEmail());
        }
    }

    @Override
    public EditPassengerPresenter initPressenter() {
        return new EditPassengerPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_addpassenger;
    }

    @Override
    public void initView() {
        if (passengerBean == null) {
            tvTitle.setText("新增常用游客");
        } else {
            tvTitle.setText("修改");
            presenter.getPassengerInfo(passengerBean.getId());
        }
        initDatePicker();
    }

    private void selectSex() {
        loopDatas.clear();
        loopDatas.add(new LoopViewData(1, "先生"));
        loopDatas.add(new LoopViewData(2, "女士"));
        selectData(new LoopViewDialog.CallBack() {
            @Override
            public void setData(LoopViewData data) {
                tvSex.setText(data.getName());
            }
        });
    }

    private void selectPassengerType() {
        loopDatas.clear();
        loopDatas.add(new LoopViewData(0, "成人"));
        loopDatas.add(new LoopViewData(1, "儿童"));
        loopDatas.add(new LoopViewData(2, "军人"));

        selectData(new LoopViewDialog.CallBack() {
            @Override
            public void setData(LoopViewData data) {
                tvPassengerType.setText(data.getName());
            }
        });
    }

    private void selectIdType() {
        loopDatas.clear();
        loopDatas.add(new LoopViewData(0, "身份证"));
        loopDatas.add(new LoopViewData(1, "军官证"));
        selectData(new LoopViewDialog.CallBack() {
            @Override
            public void setData(LoopViewData data) {
                tvIdType.setText(data.getName());
            }
        });
    }

    /**
     * 对话框选择
     *
     * @param listener
     */
    private void selectData(LoopViewDialog.CallBack listener) {
        if (loopViewDialog != null) {
            loopViewDialog.dismiss();
            loopViewDialog = null;
        }
        loopViewDialog = new LoopViewDialog(this, loopDatas, listener);
        loopViewDialog.show();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvBirthday.setText(now.split(" ")[0]);

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvBirthday.setText(time.split(" ")[0]);
            }
        }, "1920-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setYearIsLoop(false);  //设置循环  年不能  月,日能循环
        customDatePicker.setMonIsLoop(true);
        customDatePicker.setDayIsLoop(true);

    }

    @Override
    public void parseIntent(Intent intent) {
        passengerBean = (CommonPassengerBean) intent.getSerializableExtra(PASSENGERBEAN);
    }

    @Override
    public void addSucess(boolean isSuccess) {
        if (isSuccess) {
            Intent intent = new Intent();
            this.setResult(1, intent);
            finish();
        }
    }

    @Override
    public void resetSuccess(boolean isSuccess) {
        if (isSuccess) {
            Intent intent = new Intent();
            this.setResult(1, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_id_type, R.id.tv_passenger_type, R.id.btn_save, R.id.tv_birthday, R.id.tv_sex,R.id.et_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_id_type:
                selectIdType();
                break;
            case R.id.tv_passenger_type:
                selectPassengerType();
                break;
            case R.id.btn_save:
                onPushData();
                break;
            case R.id.tv_birthday:
                customDatePicker.show(DateUtils.getCurrentTime(DateUtils.TIME));
                break;
            case R.id.tv_sex:
                selectSex();
                break;
            case R.id.et_address:
                //开启检索activity 拿回结果
                SearchActivity.startUI(this, 2);
                break;

        }
    }

    private void onPushData() {
        String name;
        if (TextUtils.isEmpty(etName.getText().toString())) {
            ToastUtils.getToastShort("请填写姓名");
            return;
        } else {
            name = etName.getText().toString();
        }
        String birthday;
        if (TextUtils.isEmpty(tvBirthday.getText().toString())) {
            ToastUtils.getToastShort("请填生日");
            return;
        } else {
            birthday = tvBirthday.getText().toString();
        }
        String sn;
        if (TextUtils.isEmpty(etIdNum.getText().toString())) {
            ToastUtils.getToastShort("请填写证件号");
            return;
        } else {
            sn = etIdNum.getText().toString();
        }
        String address;
        if (TextUtils.isEmpty(etAddress.getText().toString())) {
            ToastUtils.getToastShort("请填写联系地址");
            return;
        } else {
            address = etAddress.getText().toString();
        }
        String mobile;
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            ToastUtils.getToastShort("请填写电话");
            return;
        } else {
            mobile = etPhone.getText().toString();
        }
        int type;
        try {
            type = TextUtils.equals(tvPassengerType.getText().toString(), "成人") ? 0 : (TextUtils.equals(tvPassengerType.getText().toString(), "儿童") ? 1 : 2);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getToastShort("请选择旅客类型");
            return;
        }
        //       ToastUtils.geoastShort("int type ="+type);
        int sex;
        try {
            sex = TextUtils.equals(tvSex.getText().toString(), "先生") ? 1 : 2;
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getToastShort("请选择性别");
            return;
        }

        String credentials;

        if (TextUtils.isEmpty(tvIdType.getText().toString())) {
            ToastUtils.getToastShort("请填写证件类型");
            return;
        } else {
            credentials = tvIdType.getText().toString();
        }
        String nation;
        if (TextUtils.isEmpty(etNation.getText().toString())) {
            ToastUtils.getToastShort("请填写国籍");
            return;
        } else {
            nation = etNation.getText().toString();
        }
        String email;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            ToastUtils.getToastShort("请填写联系邮箱");
            return;
        } else {
            email = etEmail.getText().toString();
        }
        if (passengerBean == null) {
            presenter.addPassenger(birthday, name, sn, address, mobile, type, sex, credentials, nation, email);
        } else {
            presenter.resetPassenger(passengerBean.getId(), birthday, name, sn, address, mobile, type, sex, credentials, nation, email);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 2) {
                String address = data.getStringExtra("address");
                etAddress.setText(address);
            }
        }
    }
}