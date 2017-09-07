package com.honganjk.ynybzbizfood.view.peihu.order.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.enumeration.OrderClickStatus;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.OrderDetailsBean;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.pressenter.peihu.order.OrderDetailsPresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryDetailsActivity;
import com.honganjk.ynybzbizfood.view.peihu.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.PayActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.WritingEvaluationActivity;
import com.honganjk.ynybzbizfood.widget.dialog.DialogSelectCause;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 陪护-订单详情
 * <p>
 */
public class OrderDetailsActivity extends BaseMvpActivity<OrderParentInterfaces.IOrderDetails, OrderDetailsPresenter> implements OrderParentInterfaces.IOrderDetails {
    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.endTime)
    TextView endTime;

    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.projectName)
    TextView projectName;
    //    @BindView(R.id.text)
//    TextView text;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.self)
    TextView self;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.orderStatus)
    TextView orderStatus;
    //订单Id
    int mOrderId;
    //订单类型：1:护工；2康复师
    int mType;
    //订单状态的提示
    OrderDetailsBean mData;
    @BindView(R.id.orderStatusHint)
    TextView orderStatusHint;
    @BindView(R.id.statusGray)
    TextView statusGray;
    @BindView(R.id.statusGreen)
    TextView statusGreen;
    @BindView(R.id.serviceName)
    TextView serviceName;

    //订单信息
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //订单信息，数据
    ArrayList<String> mDatas = new ArrayList<>();
    /**
     * 返回是不是要刷新
     */
    boolean mRefresh = false;
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    /**
     * @param context
     * @param id      服务人员Id
     * @param type    1:护工；2康复师
     */
    public static void starUi(Fragment context, int id, int type, int resultCode) {
        Intent intent = new Intent(context.getContext(), OrderDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, resultCode);
    }

    @Override
    public OrderDetailsPresenter initPressenter() {
        return new OrderDetailsPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_ph_order_details;
    }

    @Override
    public void parseIntent(Intent intent) {
        mOrderId = intent.getIntExtra("id", mOrderId);
        mType = intent.getIntExtra("type", mType);
    }

    @Override
    public void initView() {
        intentFilter.addAction(Global.ST_PAY_SUCCEED);
        registerReceiver(myBroadcastReceiver, intentFilter);
        toolbar.setBack(this);
        toolbar.setTitle("订单详情");
        presenter.getData(mOrderId);
    }


    @Override
    public void getData(OrderDetailsBean data) {
        mData = data;
        orderStatus.setText(data.getStateStr(this));
        address.setText(data.getContacts() + "," + data.getMobile() + "\n" + data.getAddress());
        endTime.setText(data.getServiceTime());
        self.setText("处理能力：" + data.getSelfStr());
        GlideUtils.setImageBitmapRound(picture, data.gettImg(), 2);
        projectName.setText(data.getTitle());
        number.setText(data.getNumStr());
        serviceName.setText(data.getNurse());

        /**
         * 	//状态0待支付;1已接单；2在服务；3介入中；4已完成；5待评价；6用户删除；7待接单；8用户取消；9护工取消；10不接单；11服务中（平台处理中）；
         */
        orderStatusHint.setVisibility(View.GONE);


        if (StringUtils.isBlank(data.getMoney())) {
//            text.setText(data.getPriceStr());
            price.setText(data.getSumPrice());
        } else {
//            text.setText("价格：" + data.getMoney());
            price.setText("合计：¥" + data.getMoney());
        }


        /**
         *0待支付；1已接单；2在服务；3待服务介入中；4已完成；5待评价；6用户删除；7待接单；8用户取消；9护工取消；10不接单；11服务中介入
         */
        switch (data.getState()) {

            /**
             * 待服务
             */
            case 0:
            case 1:
            case 3:
            case 7:
                statusGray.setText(OrderClickStatus.QU_XIAO_DING_DAN.getValue());
                statusGreen.setText(OrderClickStatus.ZHI_FU_JIN_E.getValue());

                if (data.getState() == 1) {
                    statusGreen.setVisibility(View.GONE);

                } else if (data.getState() == 3) {
                    statusGray.setVisibility(View.GONE);
                    statusGreen.setVisibility(View.GONE);
                    orderStatusHint.setText("处理状态：平台正在处理您的订单，请耐心等待：" + "\n介入时间：" + mData.getCancelTimeStr());
                    orderStatusHint.setVisibility(View.VISIBLE);
                    price.setVisibility(View.GONE);

                } else if (data.getState() == 7) {
                    statusGreen.setVisibility(View.GONE);

                }
                break;
            /**
             * 服务中
             */
            case 2:
                statusGreen.setText(OrderClickStatus.LIAN_XI_PING_TAI.getValue());
                statusGray.setVisibility(View.GONE);
                break;
            case 11:
                statusGreen.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
                orderStatusHint.setText("处理状态：平台正在处理您的订单，请耐心等待：" + "\n介入时间：" + mData.getCancelTimeStr());
                orderStatusHint.setVisibility(View.VISIBLE);
                break;
            /**
             * 已完成
             */
            case 4:

            case 8:
            case 9:
            case 10:
                statusGray.setText(OrderClickStatus.SHAN_CHU_DING_DAN.getValue());
                statusGreen.setVisibility(View.GONE);
                break;
            case 5:
                statusGreen.setText(OrderClickStatus.QU_PING_JIA.getValue());
                break;
            case 6:
                break;
        }




        mDatas.add("订单号：" + mData.getSn());
        mDatas.add("下单时间：" + mData.getCreateTimeStr());
        mDatas.add("开始时间：" + mData.getStartTime());
        mDatas.add("结束时间：" + mData.getEndTime());
        mDatas.add( mData.getPayStr());
        mDatas.add("备注：" + mData.getRemark());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_0_5).colorResId(R.color.gray_ee).build());
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_string, mDatas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.test, s);
            }
        });

    }

    @Override
    public void deleteOrder(boolean data) {
        mRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("isRefresh", mRefresh);
        setResult(RESULT_OK, intent);
        //取消订单成功关闭页面
        showInforSnackbar("订单删除成功", true);
    }

    @Override
    public void cancleOrder(boolean data) {
        mRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("isRefresh", mRefresh);
        setResult(RESULT_OK, intent);
        //取消订单成功关闭页面
        showInforSnackbar("订单取消成功", true);
    }


    @Override
    public void clearData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.statusGray, R.id.statusGreen, R.id.serviceName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //灰色按钮
            case R.id.statusGray:
                buttonClickEvent(statusGray.getText().toString(), mData);
//
//                //取消订单
//                if (mData.getState() == 1 || mData.getState() == 7) {
//                    DialogSelectCause dialog = new DialogSelectCause(this);
//                    dialog.show();
//                    dialog.setClickCallback(new DialogSelectCause.OnClickCallback() {
//                        @Override
//                        public void onClick(String type) {
//                            presenter.cancleOrder(mData.getId(), type);
//                        }
//                    });
//
//                    //mData
//                } else if (mData.getState() == 0 || mData.getState() == 4 || mData.getState() == 5 || mData.getState() == 8 || mData.getState() == 9 || mData.getState() == 10) {
//                    String str = mData.getState() == 0 ? "确定要取消订单吗？" : "确定要删除订单吗？";
//                    MaterialDialog materialDialog = new MaterialDialog.Builder(this)
//                            .title("提示")
//                            .positiveText("确定")
//                            .negativeColorRes(R.color.gray)
//                            .negativeText("取消")
//                            .content(str)
//                            .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                    presenter.deleteOrder(mData.getId());
//                                }
//                            })
//                            .build();
//                    materialDialog.show();
//                }

                break;
            //绿色按键
            case R.id.statusGreen:

                buttonClickEvent(statusGreen.getText().toString(), mData);

//
//                //联系平台
//                if (mData.getState() == 2) {
//                    DeviceUtil.callByPhone(this, Global.SERVER_PHONE);
//                }
//                if (mData.getState() == 1 && mData.getAppointTimeStr().equals("联系平台")) {
//                    DeviceUtil.callByPhone(this, Global.SERVER_PHONE);
//                }
//
//                //去评价
//                if (mData.getState() == 5) {
//                    WritingEvaluationActivity.startUI(this, mData.getId(), mData.gettImg(), REQUEST_CODE);
//                }
//
//                //去支付
//                if (mData.getState() == 0) {
//                    OrderPayData opd = new OrderPayData(
//                            mData.gettImg(),
//                            2,
//                            mData.getId(),
//                            mData.getNurse(),
//                            mData.getPrice() * mData.getNum(),
//                            mData.getServiceTime()
//                    );
//                    //跳转到支付页面
//                    PayActivity.startUI(this, opd);
//                }
                break;

            //跳转到服务人员的详情页
            case R.id.serviceName:
                NursingRecoveryDetailsActivity.startUI(this, mData.getNid(), mData.getType());
                break;
        }
    }


    /**
     * @param str 按键的点击内容
     */
    private void buttonClickEvent(String str, final OrderDetailsBean data) {

        //取消订单
        if (str.equals(OrderClickStatus.QU_XIAO_DING_DAN.getValue())) {
            DialogSelectCause dialog = new DialogSelectCause(OrderDetailsActivity.this);
            dialog.show();
            dialog.setClickCallback(new DialogSelectCause.OnClickCallback() {
                @Override
                public void onClick(String type) {
                    presenter.cancleOrder(data.getId(), type);
                }
            });

            //删除订单
        } else if (str.equals(OrderClickStatus.SHAN_CHU_DING_DAN.getValue())) {
            MaterialDialog materialDialog = new MaterialDialog.Builder(OrderDetailsActivity.this)
                    .title("提示")
                    .positiveText("确定")
                    .negativeColorRes(R.color.gray)
                    .negativeText("取消")
                    .content("确定要删除订单吗？")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            presenter.deleteOrder(data.getId());
                        }
                    })
                    .build();
            materialDialog.show();


            //支付金额(跳转到支付页面)
        } else if (str.equals(OrderClickStatus.ZHI_FU_JIN_E.getValue())) {
            OrderPayData opd = new OrderPayData(
                    data.gettImg(),
                    2,
                    data.getId(),
                    data.getName(),
                    Double.parseDouble(data.getMoney()),
                    data.getServiceTime()
            );
            PayActivity.startUI(OrderDetailsActivity.this, opd);

            //联系平台
        } else if (str.equals(OrderClickStatus.LIAN_XI_PING_TAI.getValue())) {
            DeviceUtil.callByPhone(OrderDetailsActivity.this, Global.SERVER_PHONE);

            //去评价
        } else if (str.equals(OrderClickStatus.QU_PING_JIA.getValue())) {
            WritingEvaluationActivity.startUI(OrderDetailsActivity.this, data.getId(), data.gettImg(), REQUEST_CODE + 1);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //评价成功
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            presenter.getData(mOrderId);
            mRefresh = true;
            Intent intent = new Intent();
            intent.putExtra("isRefresh", mRefresh);
            setResult(RESULT_OK, intent);
            showInforSnackbar("评价成功");
        }
    }


    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                presenter.getData(mOrderId);
            }
        }
    }
}
