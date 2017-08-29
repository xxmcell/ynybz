package com.honganjk.ynybzbizfood.view.store.order.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderDetailsData;
import com.honganjk.ynybzbizfood.pressenter.store.order.StoreOrderDetailsPresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils2;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;
import com.honganjk.ynybzbizfood.view.store.refund.activity.RefundActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商城-订单详情
 * <p>
 */
public class StoreOrderDetailsActivity extends BaseMvpActivity<StoreOrderParentInterfaces.IOrderDetails, StoreOrderDetailsPresenter> implements StoreOrderParentInterfaces.IOrderDetails {
    //订单信息
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //订单信息，数据
    ArrayList<String> mDatas = new ArrayList<>();
    /**
     * 返回是不是要刷新
     */
    boolean mRefresh = false;
    @BindView(R.id.icExpressage)
    View icExpressage;
    @BindView(R.id.expressNumberHint)
    TextView expressNumberHint;
    @BindView(R.id.expressNumber)
    TextView expressNumber;
    @BindView(R.id.rlExpressNumber)
    RelativeLayout rlExpressNumber;
    @BindView(R.id.iconAddress)
    View iconAddress;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.addredd)
    TextView addredd;
    @BindView(R.id.rlIconAddress)
    RelativeLayout rlIconAddress;
    @BindView(R.id.businessLogo)
    ImageView businessLogo;
    @BindView(R.id.businessName)
    TextView businessName;
    @BindView(R.id.orderStatus)
    TextView orderStatus;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.presentPrice)
    TextView presentPrice;
    @BindView(R.id.originalPrice)
    TextView originalPrice;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.refund)
    TextView refund;
    @BindView(R.id.llExpressage)
    LinearLayout llExpressage;
    @BindView(R.id.llPrice)
    LinearLayout llPrice;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.boundary3)
    View boundary3;
    @BindView(R.id.statusGray)
    TextView statusGray;
    @BindView(R.id.statusGreen)
    TextView statusGreen;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.buttonParent)
    LinearLayout buttonParent;
    @BindView(R.id.llPhone)
    LinearLayout llPhone;
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    private int mOrderId;
    StoreOrderData.ObjsBean mData;
    StoreOrderDetailsData mDetailsData;

    /**
     * @param context
     */
    public static void starUi(Fragment context, int resultCode, StoreOrderData.ObjsBean data) {
        Intent intent = new Intent(context.getContext(), StoreOrderDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("data", data);
        context.startActivityForResult(intent, resultCode);
    }

    @Override
    public StoreOrderDetailsPresenter initPressenter() {
        return new StoreOrderDetailsPresenter(REQUEST_CODE);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_order_details;
    }

    @Override
    public void parseIntent(Intent intent) {
        mData = intent.getParcelableExtra("data");
        if (mData != null) {
            GlideUtils2.show(mData.getImg(), picture);
            GlideUtils2.show(mData.getIcon(), businessLogo);
            businessName.setText(mData.getFeature());
            type.setText(mData.getTitle());
            price.setText(mData.getSumMoneyStr());
            orderStatus.setText(mData.getStateStr());
            presentPrice.setText(mData.getMoneyStr());
            mData.getPriceStr(originalPrice);
            number.setText(mData.getNumStr());
            mData.setViewShowStatus(statusGray, statusGreen, boundary3);

            presenter.getData(mData.getId());
        } else {
            finish();
        }
    }

    @Override
    public void initView() {
        intentFilter.addAction(Global.ST_PAY_SUCCEED);
        registerReceiver(myBroadcastReceiver, intentFilter);
        toolbar.setBack(this);
        toolbar.setTitle("订单详情");

        if (mData.getState() == 1 || mData.getState() == 2) {
            refund.setVisibility(View.VISIBLE);
        } else {
            refund.setVisibility(View.GONE);
        }
    }

    @Override
    public void getData(StoreOrderDetailsData data) {
        if (data == null) return;
        mDetailsData = data;
        expressNumber.setText(data.getCode());
        addredd.setText(data.getAddress());
        name.setText(data.getName() + "\t" + data.getMobile());

        mDatas.add("订单号：" + data.getSn());
        mDatas.add("创建时间：" + DateUtils.dateToString(data.getCreateTime(), DateUtils.TIME_HH));
        mDatas.add("付款时间：" + DateUtils.dateToString(data.getPayTime(), DateUtils.TIME_HH));
        mDatas.add("发货时间：" + DateUtils.dateToString(data.getDeliveryTime(), DateUtils.TIME_HH));
        mDatas.add("备注：" + data.getRemark());
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
        ButterKnife.bind(this);
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

    @OnClick({R.id.rlExpressNumber, R.id.refund, R.id.statusGreen, R.id.statusGray, R.id.llPhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlExpressNumber:
                Uri uri = Uri.parse("https://m.kuaidi100.com/result.jsp?com=zhongtong&nu=445034874095");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.llPhone:
                DeviceUtil.callByPhone(mActivity, mDetailsData.getContact());
                break;

            case R.id.refund:
                RefundActivity.startUI(mActivity, mData);
                break;

            //灰色按钮
            case R.id.statusGray:
                mData.buttonClickEvent(mActivity, statusGray.getText().toString(), mData, presenter);
                break;
            //绿色按键
            case R.id.statusGreen:
                mData.buttonClickEvent(mActivity, statusGreen.getText().toString(), mData, presenter);
                break;
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
