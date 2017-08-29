package com.honganjk.ynybzbizfood.view.shitang.order.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.pressenter.shitang.order.OrderDetailsPresenter;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.SuperSwipeRefreshLayout;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.code.Global.ST_ORDER_CANCEL;


/**
 * 说明:订单详情页
 * 2017/3/2-18:51
 */
public class OrderDetailsActivity extends BaseMvpActivity<OrderParentInterfaces.IOrderDetails, OrderDetailsPresenter>
        implements OrderParentInterfaces.IOrderDetails, OnRetryClickListion {
    @BindView(R.id.swipe)
    SuperSwipeRefreshLayout swipe;
    @BindView(R.id.tiJiao)
    TextView tiJiao;
    @BindView(R.id.tvTiJao)
    TextView tvTiJao;
    @BindView(R.id.jieDan)
    TextView jieDan;
    @BindView(R.id.tvJieDan)
    TextView tvJieDan;
    @BindView(R.id.peiSong)
    TextView peiSong;
    @BindView(R.id.tvPeiSong)
    TextView tvPeiSong;
    @BindView(R.id.wanCheng)
    TextView wanCheng;
    @BindView(R.id.tvWanCheng)
    TextView tvWanCheng;
    @BindView(R.id.orderStatus)
    TextView orderStatus;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.cancelOrder)
    TextView cancelOrder;
    @BindView(R.id.commitOrder)
    TextView commitOrder;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.peiSongFeiPrice)
    TextView peiSongFeiPrice;
    @BindView(R.id.youHuiPrice)
    TextView youHuiPrice;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.orderDetails)
    TextView orderDetails;
    @BindView(R.id.boundary1)
    View boundary1;
    @BindView(R.id.boundary2)
    View boundary2;
    @BindView(R.id.boundary3)
    View boundary3;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.cateringType)
    TextView cateringType;
    @BindView(R.id.bottomView)
    View bottomView;
    @BindView(R.id.oneselfTime)
    TextView oneselfTime;
    @BindView(R.id.oneselfAddress)
    TextView oneselfAddress;
    @BindView(R.id.oneselfCode)
    TextView oneselfCode;
    @BindView(R.id.oneselfParentView)
    View oneselfParentView;
    @BindView(R.id.orderParent)
    View orderParent;
    @BindView(R.id.packagingPrice)
    TextView packagingPrice;


    private int orderId;
    private OrderDetailsData mData;
    ArrayList<View> views = new ArrayList<>();
    String mUrl;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    /**
     * @param activity
     * @param orderId  订单id
     * @param url      商家的logo
     */
    public static void startUI(Activity activity, int orderId, String url) {
        Intent intent = new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentFilter.addAction(Global.ST_PAY_SUCCEED);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    public OrderDetailsPresenter initPressenter() {
        return new OrderDetailsPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_shit_order_details;
    }

    @Override
    public void initView() {
        toolbar.setTitle("订单详情");
        toolbar.setBack(this);
        toolbar.addAction(1, "联系", R.drawable.material_phone_white);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DeviceUtil.callByPhone(OrderDetailsActivity.this, mData.getContact());
                return false;
            }
        });

        swipe.setEnabled(false);

        views.add(tiJiao);
        views.add(tvTiJao);
        views.add(boundary1);
        views.add(jieDan);
        views.add(tvJieDan);
        views.add(boundary2);
        views.add(peiSong);
        views.add(tvPeiSong);
        views.add(boundary3);
        views.add(wanCheng);
        views.add(tvWanCheng);
        swipe.setOnRefreshListener(this);
        presenter.getHttpData(orderId);
    }

    @Override
    public void parseIntent(Intent intent) {
        orderId = intent.getIntExtra("orderId", orderId);
        mUrl = intent.getStringExtra("url");
    }

    @OnClick({R.id.cancelOrder, R.id.commitOrder, R.id.pay, R.id.shopParentName, R.id.oneselfPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            //取消订单
            case R.id.cancelOrder:
                //如果早已经支付需要，联系商家才可以取消订单
                if (mData.getState() != 0) {
                    MaterialDialog materialDialog = new MaterialDialog.Builder(OrderDetailsActivity.this)
                            .title((getResources().getString(R.string.app_name_simple)))
                            .positiveText("确定")
                            .negativeColorRes(R.color.gray)
                            .negativeText("取消")
                            .content((String.format(getResources().getString(R.string.cancelOrder), mData.getContact())))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    DeviceUtil.callByPhone(OrderDetailsActivity.this, mData.getContact());
                                }
                            })
                            .build();
                    materialDialog.show();

                    //没有支付直接调接口取消
                } else {
                    MaterialDialog materialDialog = new MaterialDialog.Builder(OrderDetailsActivity.this)
                            .title((getResources().getString(R.string.hint)))
                            .positiveText("确定")
                            .negativeColorRes(R.color.gray)
                            .negativeText("取消")
                            .content("您确定要取消订单吗？")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    presenter.cancelOrder(orderId);
                                }
                            })
                            .build();
                    materialDialog.show();
                }
                break;

            case R.id.commitOrder:
                //评价
                if (cancelOrder.getVisibility() == View.GONE) {
                    WritingEvaluationActivity.startUI(OrderDetailsActivity.this, mData.getBid(), mData.getId(), mUrl, REQUEST_CODE);
                    //确认收货
                } else {
                    presenter.commitOrder(mData.getId());
                }
                break;

            case R.id.pay:
                /**
                 * @param imgeUrl   订单的图片
                 * @param orderType 订单类型，1-餐饮订单，2-护理订单，默认值为1
                 * @param orderId   订单类型，1-餐饮订单，2-护理订单，默认值为1
                 * @param bizName   商家名称
                 * @param address   收货地址
                 * @param price     价格
                 */
                if (mData != null) {
                    String address = mData.getSelf() == 1 ? mData.getAddress() : mData.getAddress2();
                    OrderPayData payData = new OrderPayData(mData.getImg(), 1, mData.getId(), mData.getBname(), address,
                            mData.getPrice(), mData.getName(), mData.getMobile(), mData.getSelf() == 1 ? true : false);
                    PayActivity.startUI(OrderDetailsActivity.this, payData);
                }
                break;
            case R.id.shopParentName:
                CarteenDetailActivity.startUI(OrderDetailsActivity.this, mData.getBid());
                break;
            case R.id.oneselfPhone:
                DeviceUtil.callByPhone(OrderDetailsActivity.this, mData.getContact());
                break;
        }
    }

    @Override
    public void getHttpData(OrderDetailsData datas) {
        mData = datas;
        bottomView.setVisibility(View.GONE);
        orderStatus.setText(datas.getStateStr());
        if (datas.getState() != 0) {
            time.setText(datas.getDelivery_time());
        } else {
            time.setText("下单时间：" + datas.getCreate_timeStr());
        }
        name.setText(datas.getBname());
        peiSongFeiPrice.setText(datas.getFareStr());
        youHuiPrice.setText(datas.getAmount());
        sumPrice.setText(datas.getPriceStr());
        price.setText(datas.getPriceStr());
        orderDetails.setText(datas.getOrderDetails());
        cateringType.setText(datas.getTypeStr());
        showOrderNumber(datas.getOds());

        packagingPrice.setText(datas.getFeeStr());
        //是自提
        if (datas.getSelf() == 1) {
            oneselfParentView.setVisibility(View.VISIBLE);
            oneselfTime.setText(datas.getDelivery_time());
            oneselfAddress.setText(datas.getAddress());
            oneselfCode.setText(datas.getCode());
            orderParent.setBackgroundResource(R.mipmap.potate);

            tiJiao.setText("1");
            tiJiao.setBackgroundResource(R.drawable.bg_circle_order_graycolor);
            jieDan.setText("2");
            jieDan.setBackgroundResource(R.drawable.bg_circle_order_graycolor);
            peiSong.setText("3");
            tvPeiSong.setText("待自提");
            peiSong.setBackgroundResource(R.drawable.bg_circle_order_graycolor);
            wanCheng.setText("4");
            wanCheng.setBackgroundResource(R.drawable.bg_circle_order_graycolor);

        } else {
            oneselfParentView.setVisibility(View.GONE);
        }
        checkViewColor(datas.getState());

    }

    @Override
    public void commitOrder(boolean datas) {
        presenter.getHttpData(orderId);
    }

    @Override
    public void cancelOrder(boolean datas) {
        sendBroadcast(new Intent(ST_ORDER_CANCEL));
        showInforSnackbar("订单取消成功", true);
    }

    private void showOrderNumber(List<OrderDetailsData.OdsBean> ods) {
        CommonAdapter adapter = new CommonAdapter<OrderDetailsData.OdsBean>(OrderDetailsActivity.this, R.layout.item_order_details, ods) {
            @Override
            public void convert(ViewHolder holder, final OrderDetailsData.OdsBean data) {
                holder.setText(R.id.orderName, data.getName());
                holder.setText(R.id.orderNumber, data.getNum());
                holder.setText(R.id.orderPrice, data.getPriceStr());
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_1).colorResId(R.color.white).build());
    }

    /**
     * 改变订单状态的图标与文字颜色
     *
     * @param orderStatus //订单状态值，0-待支付;1-已接单；2-在配送；3-退款中；4-已完成；5-待评价; 7待自提；8自提过期
     */
    private void checkViewColor(int orderStatus) {
        int index = 0;
        if (orderStatus == 0) {
            index = 1;
            commitOrder.setVisibility(View.GONE);
            bottomView.setVisibility(View.VISIBLE);
        } else if (orderStatus == 1) {
            index = 4;
        } else if (orderStatus == 2) {
            index = 7;
        } else if (orderStatus == 4) {
            cancelOrder.setVisibility(View.GONE);
            commitOrder.setVisibility(View.GONE);
            time.setText("订单已完成，感谢光顾 " + mData.getBname());
            index = 10;
        } else if (orderStatus == 5) {
            index = 10;
            time.setText("写个评价吧，对其他的小伙伴帮助很大哦！");
            cancelOrder.setVisibility(View.GONE);
            commitOrder.setText("评价");
        } else if (orderStatus == 7) {
            index = 7;
            tvPeiSong.setText("待自提");
            time.setText(getResources().getString(R.string.oneselfHint));
            commitOrder.setText("待自提");
            commitOrder.setEnabled(false);
            toolbar.removeViewAt(2);//头部电话按键隐藏
        } else if (orderStatus == 8) {
            index = 7;
            tvPeiSong.setText("自提过期");
            time.setText(getResources().getString(R.string.dueHint));
            commitOrder.setVisibility(View.GONE);
            toolbar.removeViewAt(2);//头部电话按键隐藏
        }
        //改变颜色与背景
        for (int i = 0; i <= index; i++) {
            views.get(i).setSelected(true);
            if (views.get(i) instanceof TextView && mData.getSelf() == 1) {
                if (views.get(i).getId() == R.id.tiJiao || views.get(i).getId() == R.id.jieDan || views.get(i).getId() == R.id.peiSong || views.get(i).getId() == R.id.wanCheng) {
                    ((TextView) views.get(i)).setTextColor(getResources().getColor(R.color.main_color));
                    views.get(i).setBackgroundResource(R.drawable.bg_circle_order_maincolor);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            commitOrder.setText("已完成");
            commitOrder.setEnabled(false);
        }
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipe;
    }

    @Override
    public void onRefresh() {
        presenter.getHttpData(orderId);
    }

    @Override
    public void clearData() {
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getHttpData(orderId);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getHttpData(orderId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                presenter.getHttpData(orderId);
            }

        }
    }


}



