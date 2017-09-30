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

import com.google.gson.Gson;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundProgress;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;
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

import static com.honganjk.ynybzbizfood.R.id.recyclerView;

/**
 * 商城-订单详情
 * <p>
 */
public class StoreOrderDetailsActivity extends BaseMvpActivity<StoreOrderParentInterfaces.IOrderDetails, StoreOrderDetailsPresenter> implements StoreOrderParentInterfaces.IOrderDetails {
    //订单信息
    @BindView(recyclerView)
    RecyclerView recyclerView1;
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
    private StoreOrderData2.ObjsBean.DetailsBean details;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean listBean;
    private StoreOrderData2.ObjsBean data;
    private String json;
    private String jsonRefund;


    /**
     * @param context
     */
    public static void starUi(Fragment context, int resultCode, String data) {
        Intent intent = new Intent(context.getContext(), StoreOrderDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        intent.putExtra("data",data);
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
        json = intent.getStringExtra("data");
        Gson gson=new Gson();
        data = gson.fromJson(json,StoreOrderData2.ObjsBean.class);

        for (int i = 0; i < data.getDetails().size(); i++) {
            details = data.getDetails().get(i);
        }
        if(details==null){
            return;
        }
        for (int i = 0; i < details.getList().size(); i++) {
            listBean=details.getList().get(i);
        }
        if (listBean != null) {
            GlideUtils2.show(listBean.getImg(), picture);
            type.setText(listBean.getTitle());
            price.setText(listBean.getSumMoneyStr());
            presentPrice.setText(listBean.getMoneyStr());
            listBean.getPriceStr(originalPrice);
            number.setText(listBean.getNumStr());
            mOrderId=data.getId();
            if (data.getState() != 0||data.getState() != 1||data.getState() != 2||data.getState()!=3||data.getState()!=4 ) {
                refund.setVisibility(View.VISIBLE);
            } else {
                refund.setVisibility(View.GONE);
            }
            if(data.getState()==0){
                refund.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
                statusGreen.setText("支付金额");
                statusGray.setText("");
            }else if(data.getState()==1){
                statusGreen.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
            }else if(data.getState()==2){
                statusGreen.setText("确认收货");
                statusGray.setText("查看物流");
                statusGreen.setVisibility(View.VISIBLE);
                statusGray.setVisibility(View.VISIBLE);
            }else if(data.getState()==3){
                statusGreen.setText("去评价");
                statusGray.setText("查看物流");
                statusGreen.setVisibility(View.VISIBLE);
                statusGray.setVisibility(View.VISIBLE);
            }

            //订单的业务
            presenter.getData(mOrderId);
            //退单的业务
            presenter.getDatas(mOrderId);
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
    }
    StoreOrderDetailsData storeData;
    @Override
    public void getData(StoreOrderDetailsData data) {
        if(data.getCode()!=null){ expressNumber.setText(data.getCode()+"");}

        addredd.setText(data.getAddress());
        name.setText(data.getName() + "\t" + data.getMobile());

        mDatas.add("订单号：" + data.getSn());
        mDatas.add("创建时间：" + DateUtils.dateToString(data.getCreateTime(), DateUtils.TIME_HH));
        mDatas.add("付款时间：" + DateUtils.dateToString(data.getPayTime(), DateUtils.TIME_HH));
        if(DateUtils.dateToString(String.valueOf(data.getDeliveryTime()), DateUtils.TIME_HH)!=null){
            mDatas.add("发货时间：" + DateUtils.dateToString(String.valueOf(data.getDeliveryTime()), DateUtils.TIME_HH));
        }else {
            mDatas.add("发货时间：代发货");
        }

        if(data.getRemark()!=null){ mDatas.add("备注：" + data.getRemark());}

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_0_5).colorResId(R.color.gray_ee).build());
        recyclerView1.setAdapter(new CommonAdapter<String>(this, R.layout.item_string, mDatas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.item, s);

            }
        });


    }
    RefundRequestData datas;
    @Override
    public void getHttpDataRefund(RefundRequestData data) {
            this.datas=data;

    }

    @Override
    public void RefundProgress(RefundProgress data) {

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
                DeviceUtil.callByPhone(mActivity, String.valueOf(storeData.getContact()));
                break;

            case R.id.refund:
                RefundActivity.startUI(mActivity, json,jsonRefund);
                break;
            //灰色按钮
            case R.id.statusGray:
                //他使用取得按钮的字段,来做收货,取消的判断
                data.buttonClickEvent(mActivity, statusGray.getText().toString(), data,listBean, presenter);
                break;
            //绿色按键
            case R.id.statusGreen:
                data.buttonClickEvent(mActivity, statusGreen.getText().toString(), data,listBean, presenter);
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
