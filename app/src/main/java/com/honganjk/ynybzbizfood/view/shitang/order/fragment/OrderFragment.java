package com.honganjk.ynybzbizfood.view.shitang.order.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.pressenter.shitang.order.OrderPresenter;
import com.honganjk.ynybzbizfood.utils.ui.DrawableUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.OrderActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.OrderDetailsActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.PayActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.WritingEvaluationActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:我的订单的列表
 * 360621904@qq.com 杨阳 2017/3/6  20:07
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends BaseListFragment<OrderParentInterfaces.IOrder, OrderPresenter> implements OrderParentInterfaces.IOrder {
    /**
     * 可选,int	要查询的订单状态,
     * 0:待付款; 2:待收货; 5:待评价;4:已完成;
     */
    private int mType;
    ArrayList<OrderData> mDatas = new ArrayList<>();
    CommonAdapter adapter;
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @SuppressLint("ValidFragment")
    public OrderFragment() {
    }

    public OrderFragment(int type) {
        this.mType = type;
    }

    public static OrderFragment getInstance(int type) {
        return new OrderFragment(type);
    }

    @Override
    public int getContentView() {
        return R.layout.widget_superrecyclerview;
    }

    @Override
    public void initView() {
        super.initView();
        intentFilter.addAction(Global.ST_ORDER_CANCEL);
        intentFilter.addAction(Global.ST_PAY_SUCCEED);
        activity.registerReceiver(myBroadcastReceiver, intentFilter);

        presenter.getHttpData(true, mType);
        adapter.setOnItemClickListener(new OnItemClickListener<OrderData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, OrderData data, int position) {
                OrderDetailsActivity.startUI(activity, data.getId(), data.getImg());
            }
        });


    }

    @Override
    public CommonAdapter getAdapter() {
        return adapter = new CommonAdapter<OrderData>(activity, R.layout.item_order, mDatas) {
            @Override
            public void convert(ViewHolder holder, final OrderData o) {
                holder.setText(R.id.title, o.getBname());
                holder.setImageBitmapRound(R.id.picture, o.getImg(), 5);

                holder.setText(R.id.name, o.getSummary());
                holder.setText(R.id.price, o.getPriceS());
                holder.setText(R.id.data, o.getDelivery_time());
                holder.setText(R.id.payStatus, o.getState());
                holder.getView(R.id.payStatus).setBackgroundDrawable(DrawableUtils.getGradientDrawable(R.color.white, R.color.red_fd5a5a, 2, 1));
                //跳转到店面
                holder.setOnClickListener(R.id.title, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CarteenDetailActivity.startUI(activity, o.getBid());
                    }
                });
                //各个状态的跳转
                holder.setOnClickListener(R.id.payStatus, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 订单状态值，0-待支付;1-已接单；2-在配送；3-退款中；4-已完成；5-待评价
                         */

                        //支付
                        if (o.getStateInt() == 0) {

                            /**
                             * @param imgeUrl   订单的图片
                             * @param orderType 订单类型，1-餐饮订单，2-护理订单，默认值为1
                             * @param orderId   订单类型，1-餐饮订单，2-护理订单，默认值为1
                             * @param bizName   商家名称
                             * @param address   收货地址
                             * @param price     价格
                             */
                            OrderPayData payData = new OrderPayData(o.getImg(), 1, o.getId(), o.getBname(), o.getAddress(),
                                    o.getPrice(), o.getName(), o.getMobile(),false);
                            PayActivity.startUI(activity, payData);

                            //确定收货
                        } else if (o.getStateInt() == 1 || o.getStateInt() == 2) {

                            MaterialDialog materialDialog = new MaterialDialog.Builder(activity)
                                    .title((getResources().getString(R.string.hint)))
                                    .positiveText("确定")
                                    .negativeColorRes(R.color.gray)
                                    .negativeText("取消")
                                    .content("您确定要收货吗？")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            presenter.commitOrder(o.getId());
                                        }
                                    })
                                    .build();
                            materialDialog.show();

                            //退款还没有这个功能
                        } else if (o.getStateInt() == 3) {
                            showWarningSnackbar("功能添加中");

                            //删除订单
                        } else if (o.getStateInt() == 4) {

                            MaterialDialog materialDialog = new MaterialDialog.Builder(activity)
                                    .title((getResources().getString(R.string.hint)))
                                    .positiveText("确定")
                                    .negativeColorRes(R.color.gray)
                                    .negativeText("取消")
                                    .content("您确定要删除订单记录吗？")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            presenter.cancelOrder(o.getId());
                                        }
                                    })
                                    .build();
                            materialDialog.show();

                            //评价
                        } else if (o.getStateInt() == 5) {
                            WritingEvaluationActivity.startUI(OrderFragment.this, o.getBid(), o.getId(), o.getImg(), activity.REQUEST_CODE);
                        }
                    }
                });


            }

            @Override
            public void convert(ViewHolder holder, List<OrderData> t) {

            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == activity.REQUEST_CODE && resultCode == activity.RESULT_OK) {
            presenter.getHttpData(true, mType);
        }
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_10).build();
    }

    @Override
    public void onRefresh() {
        presenter.getHttpData(true, mType);
    }

    @Override
    public OrderPresenter initPressenter() {
        return new OrderPresenter();
    }

    @Override
    public void getHttpData(List<OrderData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        ((OrderActivity) activity).setCount(mType == 0 ? 0 : mType == 2 ? 1 : mType == 5 ? 2 : 3, mDatas.size());
    }

    @Override
    public void commitOrder(boolean datas) {
        presenter.getHttpData(true, mType);
    }

    @Override
    public void cancelOrder(boolean datas) {
        presenter.getHttpData(true, mType);
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getHttpData(false, mType);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getHttpData(true, mType);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getHttpData(true, mType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(myBroadcastReceiver);
    }


    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //取消订单成功
            if (intent.getAction().equals(Global.ST_ORDER_CANCEL) && mType == 0) {
                presenter.getHttpData(true, mType);
            }
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED) && mType == 0) {
                presenter.getHttpData(true, mType);
            }

        }
    }
}
