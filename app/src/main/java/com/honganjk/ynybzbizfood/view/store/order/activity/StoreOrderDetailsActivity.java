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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;
import com.honganjk.ynybzbizfood.view.store.refund.activity.RefundActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商城-订单详情
 * <p>
 */
public class StoreOrderDetailsActivity extends BaseMvpActivity<StoreOrderParentInterfaces.IOrderDetails, StoreOrderDetailsPresenter> implements StoreOrderParentInterfaces.IOrderDetails {
    //订单信息
    //订单信息，数据
    ArrayList<String> mDatas = new ArrayList<>();
    /**
     * 返回是不是要刷新
     */
    boolean mRefresh = false;

    @BindView(R.id.myrecyclerView)
    RecyclerView myrecyclerView;
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
    @BindView(R.id.refund)
    TextView refund;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.llExpressage)
    LinearLayout llExpressage;
    @BindView(R.id.boundary0)
    View boundary0;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.llPrice)
    LinearLayout llPrice;
    @BindView(R.id.boundary1)
    View boundary1;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.llPhone)
    LinearLayout llPhone;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.boundary3)
    View boundary3;
    @BindView(R.id.statusGray)
    TextView statusGray;
    @BindView(R.id.statusGreen)
    TextView statusGreen;
    @BindView(R.id.buttonParent)
    LinearLayout buttonParent;
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    private int mOrderId;
    private StoreOrderData2.ObjsBean.DetailsBean details;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean listBean;
    private StoreOrderData2.ObjsBean data;
    private String json;
    private String jsonRefund;

    private List<List<StoreOrderData2.ObjsBean.DetailsBean>> lists = new ArrayList<>();
    private List<StoreOrderData2.ObjsBean.DetailsBean> list = new ArrayList<>();

    private boolean changer;

    /**
     * @param context
     */
    public static void starUi(Fragment context, int resultCode, String data) {
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
    int  thePrice;
    @Override
    public void parseIntent(Intent intent) {
        json = intent.getStringExtra("data");
        Gson gson = new Gson();
        data = gson.fromJson(json, StoreOrderData2.ObjsBean.class);

        for (int i = 0; i < data.getDetails().size(); i++) {
            details = data.getDetails().get(i);
        }
        if (details == null) {
            return;
        }
        for (int i = 0; i < details.getList().size(); i++) {
            listBean = details.getList().get(i);
            thePrice=listBean.getMoney()*listBean.getNum();
        }
        //为适配器做好逻辑
        lists.clear();
        if (data.getDetails().size() == 1) {
            lists.add(data.details);
        } else {

            for (int i = 0; i < data.getDetails().size(); i++) {
                list = new ArrayList<>();
                list.add(data.getDetails().get(i));
                for (int i1 = 0; i1 < data.getDetails().size(); i1++) {
                    if (data.getDetails().get(i).getFeature().equals(data.getDetails().get(i1).getFeature())) {
                        if (!list.contains(data.getDetails().get(i1))) {
                            list.add(data.details.get(i1));
                        }
                    }
                }
                if (!lists.contains(list)) {
                    if (lists.size() == 0) {
                        lists.add(list);
                    }
                }
                for (int i2 = 0; i2 < lists.size(); i2++) {
                    for (int i1 = 0; i1 < lists.get(i2).size(); i1++) {
                        for (int i3 = 0; i3 < list.size(); i3++) {
                            if (lists.get(i2).get(i1).getFeature().equals(list.get(i3).getFeature())) {
                                changer = false;
                            }
                        }
                    }
                }
                if (changer == true) {
                    lists.add(list);
                }
                changer = true;
            }
        }
        if (listBean != null) {

            setMyAdapter(lists);

            mOrderId = data.getId();
            if (data.getState() != 0 || data.getState() != 1 || data.getState() != 2 || data.getState() != 3 || data.getState() != 4) {
                refund.setVisibility(View.VISIBLE);
            } else {
                refund.setVisibility(View.GONE);
            }
            if (data.getState() == 0) {
                refund.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
                statusGreen.setText("支付金额");
                statusGray.setText("");
            } else if (data.getState() == 1) {
                statusGreen.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
            } else if (data.getState() == 2) {
                statusGreen.setText("确认收货");
                statusGray.setText("查看物流");
                statusGreen.setVisibility(View.VISIBLE);
                statusGray.setVisibility(View.VISIBLE);
            } else if (data.getState() == 3) {
                statusGreen.setText("去评价");
                statusGray.setText("查看物流");
                statusGreen.setVisibility(View.VISIBLE);
                statusGray.setVisibility(View.VISIBLE);
            }else {
                statusGreen.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
            }
            //订单的业务
            presenter.getData(mOrderId);
            //退单的业务
            presenter.getDatas(mOrderId);
        } else {
            finish();
        }
    }

    private void setMyAdapter(List<List<StoreOrderData2.ObjsBean.DetailsBean>> lists) {
        //适配器最后展示
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myrecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_0_5).colorResId(R.color.gray_ee).build());
        myrecyclerView.setAdapter(new myAdapter(lists));
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
        storeData=data;
        if (data.getCode() != null) {
            expressNumber.setText(String.valueOf(data.getCode()));
        }else {
            expressNumber.setText("暂无物流信息");
        }
        addredd.setText(data.getAddress());
        freight.setText(String.valueOf(data.getFare()));
        name.setText(data.getName() + "\t" + data.getMobile());
        mDatas.add("订单号：" + data.getSn());
        mDatas.add("创建时间：" + DateUtils.dateToString(data.getCreateTime(), DateUtils.TIME_HH));
        mDatas.add("付款时间：" + DateUtils.dateToString(data.getPayTime(), DateUtils.TIME_HH));
        if (DateUtils.dateToString(String.valueOf(data.getDeliveryTime()), DateUtils.TIME_HH) != null) {
            mDatas.add("发货时间：" + DateUtils.dateToString(String.valueOf(data.getDeliveryTime()), DateUtils.TIME_HH));
        } else {
            mDatas.add("发货时间：代发货");
        }

        if (data.getRemark() != null) {
            mDatas.add("备注：" + data.getRemark());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_0_5).colorResId(R.color.gray_ee).build());
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_string, mDatas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.item, s);

            }

            @Override
            public void convert(ViewHolder holder, List<String> t) {

            }
        });


    }

    RefundRequestData datas;

    @Override
    public void getHttpDataRefund(RefundRequestData data) {
        this.datas = data;

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
                if(data.getCode()!=null){
                    Uri uri = Uri.parse("https://m.kuaidi100.com/result.jsp?com=zhongtong&nu="+data.getCode());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                ToastUtils.getToastShort("暂未发货");
                break;

            case R.id.llPhone:
                DeviceUtil.callByPhone(mActivity, String.valueOf("4008939973"));
                break;

            case R.id.refund:
                RefundActivity.startUI(mActivity, json, jsonRefund);
                break;
            //灰色按钮
            case R.id.statusGray:
                //他使用取得按钮的字段,来做收货,取消的判断
                data.buttonClickEvent(mActivity,thePrice, statusGray.getText().toString(), data, listBean, presenter);
                break;
            //绿色按键
            case R.id.statusGreen:
                data.buttonClickEvent(mActivity, thePrice,statusGreen.getText().toString(), data, listBean, presenter);
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

    private class myAdapter extends RecyclerView.Adapter<myAdapter.MyRecyclerViewHolder> {

        private String types;
        private LinearLayout titlecontant;
        private TextView type;
        private TextView text;
        private TextView presentPrice;
        private TextView orginalPrice;
        private TextView number;
        private ImageView picture;

        List<List<StoreOrderData2.ObjsBean.DetailsBean>> mLists;
        private View addView;

        public myAdapter(List<List<StoreOrderData2.ObjsBean.DetailsBean>> lists) {
            this.mLists=lists;
        }

        @Override
        public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyRecyclerViewHolder holder=
                    new MyRecyclerViewHolder(LayoutInflater.from(StoreOrderDetailsActivity.this).inflate(R.layout.store_item_orders,null,false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {

            holder.orderStatus.setVisibility(View.GONE);
            LinearLayout parent=holder.contants;
            parent.removeAllViews();

                for (int i1 = 0; i1 < mLists.get(position).size(); i1++) {
                    if(i1==0){
                        holder.titlecontant.setVisibility(View.VISIBLE);
                        holder.name.setText(mLists.get(position).get(i1).getFeature());
                        GlideUtils.show(holder.businessLogo,mLists.get(position).get(i1).getIcon());
                    }
                    for (int i2 = 0; i2 < mLists.get(position).get(i1).getList().size(); i2++) {
                        addView = LayoutInflater.from(StoreOrderDetailsActivity.this).inflate(R.layout.store_item_order_items,null);
                        picture = addView.findViewById(R.id.picture);
                        type = addView.findViewById(R.id.type);
                        text = addView.findViewById(R.id.text);
                        presentPrice = addView.findViewById(R.id.presentPrice);
                        orginalPrice = addView.findViewById(R.id.originalPrice);
                        number = addView.findViewById(R.id.number);

                        GlideUtils.show(picture, mLists.get(position).get(i1).getList().get(i2).getImg(), new GlideOptions.Builder().bulider());
                        type.setText(mLists.get(position).get(i1).getList().get(i2).getTitle());
                        text.setText("规格" + mLists.get(position).get(i1).getList().get(i2).getLabel());
                        presentPrice.setText(mLists.get(position).get(i1).getList().get(i2).getMoneyStr());
                        mLists.get(position).get(i1).getList().get(i2).getPriceStr(orginalPrice);
                        number.setText(mLists.get(position).get(i1).getList().get(i2).getNumStr());
                        parent.addView(addView);
                    }
                }
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClassifyActivity.startUI(StoreOrderDetailsActivity.this);
                    }
                });
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

            private final TextView orderStatus;
            private final TextView name;
            private final ImageView businessLogo;
            private final LinearLayout contants;
            private final LinearLayout titlecontant;

            public MyRecyclerViewHolder(View view) {
               super(view);
                orderStatus = view.findViewById(R.id.orderStatus);
                name = view.findViewById(R.id.name);
                businessLogo = view.findViewById(R.id.businessLogo);
                contants = view.findViewById(R.id.contants);
                titlecontant = view.findViewById(R.id.titlecontant);
           }
       }
    }
}
