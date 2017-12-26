package com.honganjk.ynybzbizfood.view.tour.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.order.OrderTourPresent;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.tour.order.activity.MyOrderDetailActivity;
import com.honganjk.ynybzbizfood.view.tour.order.adapter.TourOrderAdapter;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;

/**
 * 订单列表
 * Created by Administrator on 2017-11-27.
 */

public class OrderListFragment extends BaseListFragment<OrderTourPresentInterface.IOrderInterface, OrderTourPresent>
        implements OrderTourPresentInterface.IOrderInterface {

    private int state = 0;  //要查询的订单状态,0-待付款，1-待出行，2-已完成
    ArrayList<OrderTourBean.Data.Objs> mDatas = new ArrayList<>();
    TourOrderAdapter adapter;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    public OrderListFragment() {
    }


    public static OrderListFragment getInstance(int type) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLoadding() {
        presenter.getOrder(true, state);
    }

    @Override
    public void initView() {
        super.initView();
        state = getArguments().getInt("type");
        intentFilter.addAction(Global.ST_ORDER_CANCEL);
        intentFilter.addAction(Global.ST_PAY_SUCCEED);
        intentFilter.addAction(Global.LOGIN_SUCCEED);
        activity.registerReceiver(myBroadcastReceiver, intentFilter);
        if (UserInfo.isSLogin()) {
            presenter.getOrder(true, state);
        }
        adapter.setOnItemClickListener(new OnItemClickListener<OrderTourBean.Data.Objs>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, OrderTourBean.Data.Objs data, int position) {
                //             OrderDetailsActivity.starUi(OrderFragment.this, data.getId(), data.getType(), REQUEST_CODE);
                //跳转到订单详情界面
                MyOrderDetailActivity.startUi(getContext(), Integer.parseInt(data.getSn()), position);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getOrder(true, state);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getOrder(true, state);
    }

    @Override
    public CommonAdapter getAdapter() {
        return adapter = new TourOrderAdapter(getContext(), mDatas);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_10).build();
    }

    @Override
    public void onRefresh() {
        presenter.getOrder(true, state);
    }

    @Override
    public void getHttpData(OrderTourBean.Data data) {
        if (data != null && data.getObjs().size() != 0) {
            mDatas.addAll(data.getObjs());
            adapter.notifyDataSetChanged();
            ((OrderFragment) getParentFragment()).setCount(state == 0 ? 0 : state == 2 ? 2 : 1, mDatas.size());
        } else {
            for (int i = 0; i < 7; i++) {
                OrderTourBean.Data.Objs objs = new OrderTourBean.Data.Objs();
                objs.setId(i);
                objs.setPrice(i * 100);
                objs.setOutsetTime(DateUtils.getCurrentTime(DateUtils.TIME));
                objs.setSn(i * 1000 + "");
                objs.setState(i);
                objs.setTitle("测试" + i);
                mDatas.add(objs);
            }
            adapter.notifyDataSetChanged();
            ((OrderFragment) getParentFragment()).setCount(state == 0 ? 0 : state == 2 ? 2 : 1, mDatas.size());
        }

    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public OrderTourPresent initPressenter() {
        return new OrderTourPresent();
    }

    @Override
    public int getContentView() {
        return R.layout.widget_superrecyclerview;
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //取消订单成功
            if (intent.getAction().equals(Global.ST_ORDER_CANCEL)) {
                presenter.getOrder(true, state);
            }
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                presenter.getOrder(true, state);
            }

            //登录成功成功
            if (intent.getAction().equals(Global.LOGIN_SUCCEED)) {
                presenter.getOrder(true, state);
            }
        }
    }
}
