package com.honganjk.ynybzbizfood.view.peihu.order.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ServiceBean;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.SubscribeOrderBean;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ValideDataBean;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.pressenter.peihu.order.SubscribePresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.utils.other.Validators;
import com.honganjk.ynybzbizfood.view.peihu.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.SelectAddressActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.BeizhuActivity;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;
import com.honganjk.ynybzbizfood.widget.dialog.calender.DialogCalendarSelect;
import com.honganjk.ynybzbizfood.widget.dialog.calender.TimeSelectData;
import com.honganjk.ynybzbizfood.widget.dialog.therapisttime.DialogTherapistTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 陪护-预约下单
 */
public class SubscribeActivity extends BaseMvpActivity<OrderParentInterfaces.ISubscribe, SubscribePresenter> implements OrderParentInterfaces.ISubscribe {
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.endTime)
    TextView endTime;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.text)
    TextView text;
    //    @BindView(R.id.price)
//    TextView price;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.self)
    TextView self;
    @BindView(R.id.number)
    TextView number;
    //请求的实体对象
    private SubscribeOrderBean mData = new SubscribeOrderBean();
    //护工可服务的有效日期
    private int[] mNursingValidTimes;
    private EditHelper editHelper = new EditHelper(this);
    //广播,监听支付成功
    private MyBroadcastReceiver mMyBroadcastReceiver;
    ValideDataBean mTherpistData;

    private int templeWeek = -1, templeTime = -1;

    /**
     * @param context
     * @param id      服务人员Id
     * @param type    1:护工；2康复师
     * @param imgUrl  服务人员头像
     */
    public static void starUi(Context context, int id, int type, String imgUrl) {
        Intent intent = new Intent(context, SubscribeActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("imgUrl", imgUrl);
        context.startActivity(intent);
    }

    @Override
    public SubscribePresenter initPressenter() {
        return new SubscribePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_subscribe;
    }

    @Override
    public void parseIntent(Intent intent) {
        mData.setUserId(intent.getIntExtra("id", -1));
        mData.setType(getIntent().getIntExtra("type", -1));
        mData.setPortrait(intent.getStringExtra("imgUrl"));
//        mData.setServicePhone(getIntent().getStringExtra("servicePhone"));
    }

    @Override
    public void initView() {
        //注册广播
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intent = new IntentFilter(Global.ST_PAY_SUCCEED);
        registerReceiver(mMyBroadcastReceiver, intent);

        toolbar.setBack(this);
        toolbar.setTitle("预约下单");
        editHelper.addEditHelperData(new EditHelper.EditHelperData(startTime, Validators.getLenghMinRegex(1), "请选择开始时间"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(endTime, Validators.getLenghMinRegex(1), "请选择结束时间"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(self, Validators.getLenghMinRegex(1), "请选择自理能力"));

        presenter.getDefaultAddress();
        presenter.getData(mData.getType());
        presenter.getSubscribeTime(mData.getUserId());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //地址选择的回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mData.setAddress(data.getStringExtra("address"));
            mData.setName(data.getStringExtra("name"));
            mData.setSex(data.getStringExtra("sex"));
            mData.setPhone(data.getStringExtra("phone"));
            mData.setAddressid(data.getIntExtra("addressid", -1));
            address.setText(mData.getInfor(this));
        }

        //备注的回调
        if (requestCode == REQUEST_CODE + 1 && resultCode == RESULT_OK && data != null) {
            mData.setRemark(data.getStringExtra("beizhu"));
            remark.setText(mData.getRemark());
        }
    }

    @Override
    public void getDefaultAddress(DefaultAddressData data) {
        mData.setAddress(data.getAddress());
        mData.setName(data.getName());
        mData.setSex(data.getSexStr());
        mData.setPhone(data.getContact());
        mData.setAddressid(data.getId());
        address.setText(mData.getInfor(this));
    }

    @Override
    public void getData(ServiceBean data) {
        GlideUtils.showCircle(picture, mData.getPortrait());
        name.setText("项目：" + data.getContent());
        text.setText("参考价格：¥" + data.getPriceStr());
        mData.setPrice(data.getPrice());
        mData.setServiceId(data.getId());
        mData.setImg(data.getImg());
        mData.setServiceName(data.getContent());


        if (mData.getType() == 2) {
            endTime.setCompoundDrawables(null, null, null, null);
            endTime.setText(getResources().getString(R.string.therapist_service_hint));
            endTime.setTextColor(getResources().getColor(R.color.gray_99));

            findViewById(R.id.endTimeHint).setVisibility(View.GONE);
        }


    }

    @Override
    public void getNursingValidTime(int[] data) {
        mNursingValidTimes = data;
    }

    @Override
    public void getTherapistValidTime(ValideDataBean data) {
        mTherpistData = data;
    }

    /**
     * @param isSucceed 是否成功生成订单
     * @param orderId   生成的订单Id
     */
    @Override
    public void placeTheOrderIsSucceed(boolean isSucceed, int orderId) {
        String serviceTime = "";
        if (mData.getType() == 1) {
            serviceTime = startTime.getText().toString().substring(0, startTime.getText().length() - 2) + "起，共" + mData.getmNursingServiceTime().length + "天";
        } else {
            serviceTime = startTime.getText().toString();
        }

        OrderPayData data = new OrderPayData(
                mData.getImg(),
                2,
                orderId,
                mData.getServiceName(),
                mData.getSumPrice(),
                serviceTime

        );

        //跳转到支付页面
//        PayActivity.startUI(this, data);

        mData.setServiceTime(data.getServiceTime());
        SubscribeSucceedActivity.startUI(this, mData);

        finish();
    }

    @Override
    public void clearData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    @OnClick({R.id.address, R.id.startTimeParent, R.id.endTimeParent, R.id.remarkParent, R.id.commit, R.id.selfParent})
    public void onClick(View view) {
        int[] aa1 = {1, 2, 3, 4, 6, 5, 22, 23, 25, 15, 16, 17, 18, 19, 20, 29, 30, 28, 13, 12};
        switch (view.getId()) {
            //地址选择
            case R.id.address:
                SelectAddressActivity.startForResultUi(this, REQUEST_CODE);
                break;
            //开始时间
            case R.id.startTimeParent:

                //护工的选择时间
                if (mData.getType() == 1) {
                    if (mNursingValidTimes == null) {
                        return;
                    }
                    DialogCalendarSelect d1 = new DialogCalendarSelect(this, mNursingValidTimes, "开始时间", false);
                    d1.show();
                    d1.setOnClickCallback(new DialogCalendarSelect.OnClickCallback() {
                        @Override
                        public void onClick(ArrayList<TimeSelectData> selectData) {
                            startTime.setText(selectData.get(0).getDataStr() + "\t开始");

                            mData.setmNursingStartTime(selectData.get(0).getData());
                            mData.setmNursingStartTimeStr(selectData.get(0).getDataStr());

                            if (!StringUtils.isBlank(endTime.getText().toString())) {
                                mData.setmNursingServiceTime(presenter.getSelectValidNumber(mData.getmNursingStartTime(), mData.getmNursingEndTime()));
                                addTimeData();
                            }

                        }
                    });
                    //康复师的选择时间
                } else {
                    if (mTherpistData != null) {
                        final DialogTherapistTime dialogThreapistTime = new DialogTherapistTime(this, mTherpistData, false);
                        dialogThreapistTime.setOnClickCallback(new DialogTherapistTime.OnClickCallback() {
                            @Override
                            public void onClick(HashMap<Integer, ArrayList<Integer>> selectData) {

                                StringBuffer sb = new StringBuffer();
                                Iterator iter = selectData.entrySet().iterator();
                                while (iter.hasNext()) {
                                    Map.Entry entry = (Map.Entry) iter.next();
                                    Integer key = (Integer) entry.getKey();
                                    ArrayList val = (ArrayList) entry.getValue();
//                                    sb.append((key +"周"+ val.toString()) + "\n");
                                    sb.append((dialogThreapistTime.getWeekDateString(key) + "\t"));
                                    sb.append(dialogThreapistTime.getTimeDataString((int) val.get(0)));
                                    mData.setWeek(key);
                                    mData.setTime((int) val.get(0));
                                    templeWeek = mData.getWeek();
                                    templeTime = mData.getTime();
                                }
                                addTimeData();
                                startTime.setText(sb.toString());
                                mData.setmNursingStartTimeStr(sb.toString());
                            }
                        });
                        dialogThreapistTime.show();
                        if (templeWeek != -1 && templeTime != -1) {
                            dialogThreapistTime.setItemSelected(templeWeek, templeTime);
                        }

                    } else {
                        showWarningMessage("该康复师没有可预约时间");
                    }
                }
                break;
            //结束时间（只有护工才有）
            case R.id.endTimeParent:
                if (mNursingValidTimes == null) {
                    return;
                }
                if (mData.getType() == 2) {
                    return;
                }
                if (mData.getmNursingStartTime() == -1) {
                    showWarningMessage("请先选择开始时间");
                    return;
                }
                //结束的有效时间
                int[] validTime = presenter.getEndNursingValidTime(mNursingValidTimes, mData.getmNursingStartTime());

                DialogCalendarSelect d = new DialogCalendarSelect(this, validTime, "结束时间", false);
                d.show();
                d.setOnClickCallback(new DialogCalendarSelect.OnClickCallback() {
                    @Override
                    public void onClick(ArrayList<TimeSelectData> selectData) {
                        endTime.setText((selectData.get(0).getDataStr() + "\t结束"));
                        mData.setmNursingEndTime(selectData.get(0).getData());
                        mData.setmNursingServiceTime(presenter.getSelectValidNumber(mData.getmNursingStartTime(), mData.getmNursingEndTime()));
                        addTimeData();
                    }
                });
                break;
            //备注
            case R.id.remarkParent:
                BeizhuActivity.startUiForresult(this, REQUEST_CODE + 1, mData.getRemark());
                break;
            //自理能力
            case R.id.selfParent:
                //，1-完全自理；2-半自理；3-完全不自理
                ArrayList<PopupPulldown.PullDownData> datas = new ArrayList<>();
                datas.add(new PopupPulldown.PullDownData(1, "全自理"));
                datas.add(new PopupPulldown.PullDownData(2, "半自理"));
                datas.add(new PopupPulldown.PullDownData(3, "不能自理"));
                PopupPulldown pp = new PopupPulldown(this, datas);
                pp.showPopupWindow(findViewById(R.id.selfParent));
                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        self.setText(content);
                        mData.setSelfType(id);
                    }
                });
                break;
            //提交
            case R.id.commit:
                if (editHelper.check()) {
                    presenter.commitOrder(mData);
                }
                break;
        }
    }

    /**
     * 价格与数量赋值
     */
    private void addTimeData() {
        if (mData != null) {
            if (mData.getType() == 1) {
                number.setText("数量：" + mData.getmNursingServiceTime().length + "天");
            } else {
                number.setText("数量：2小时");
            }
//            price.setText(("参考价合计：¥" + mData.getSumPrice()));
            sumPrice.setText(("参考价合计：¥" + mData.getSumPrice()));
            sumPrice.setVisibility(View.GONE);

        }
    }

    /**
     * 广播
     */
    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 支付成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                finish();
            }
        }
    }
}
