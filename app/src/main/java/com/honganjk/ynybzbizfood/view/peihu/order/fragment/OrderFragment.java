package com.honganjk.ynybzbizfood.view.peihu.order.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.NurserOrderdData;
import com.honganjk.ynybzbizfood.pressenter.peihu.order.OrderPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.peihu.order.activity.OrderActivity;
import com.honganjk.ynybzbizfood.view.peihu.order.activity.OrderDetailsActivity;
import com.honganjk.ynybzbizfood.view.peihu.order.adapter.NurserOrderAdapter;
import com.honganjk.ynybzbizfood.view.peihu.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 说明:陪护-我的订单
 * 360621904@qq.com 杨阳 2017/3/24  14:16
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends BaseListFragment<OrderParentInterfaces.IOrder, OrderPresenter> implements OrderParentInterfaces.IOrder {
    /**
     * 0:待支付; 1:待服务; 2:服务中;4:已完成;
     */
    private int mType;
    ArrayList<NurserOrderdData.ObjsBean> mDatas = new ArrayList<>();
    NurserOrderAdapter adapter;

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
        intentFilter.addAction(Global.LOGIN_SUCCEED);
        activity.registerReceiver(myBroadcastReceiver, intentFilter);

        presenter.getHttpData(0, true, mType);

        adapter.setOnItemClickListener(new OnItemClickListener<NurserOrderdData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NurserOrderdData.ObjsBean data, int position) {
                OrderDetailsActivity.starUi(OrderFragment.this, data.getId(), data.getType(), REQUEST_CODE);
            }
        });


    }

    @Override
    public CommonAdapter getAdapter() {
        return adapter = new NurserOrderAdapter(this, mDatas);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //详情页面操作后列表页要刷新
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data.getBooleanExtra("isRefresh", false)) {
                    presenter.getHttpData(0, true, mType);
                }
            }
        }
        //评价成功
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK + 1) {
            presenter.getHttpData(0, true, mType);
        }
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_10).build();
    }

    @Override
    public void onRefresh() {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public OrderPresenter initPressenter() {
        return new OrderPresenter();
    }

    @Override
    public void getHttpData(int total, List<NurserOrderdData.ObjsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        // 0:待支付; 1:待服务; 2:服务中;4:已完成;
        int postiont  = mType == 1 ? 0 : mType == 2 ? 1 : 2;
        ((OrderActivity) activity).setCount(postiont, mDatas.size());
    }

    @Override
    public void confirmCompleted(boolean data) {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public void deleteOrder(boolean data) {
        presenter.getHttpData(0, true, mType);
        showInforSnackbar("删除成功");
    }

    @Override
    public void cancleOrder(boolean data) {
        presenter.getHttpData(0, true, mType);
        showInforSnackbar("取消成功成功");
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getHttpData(0, false, mType);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(myBroadcastReceiver);
    }

    /**
     * 显示不同的订单状态
     */
    public void setOrderStatus(int type) {
        presenter.getHttpData(type, true, mType);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //取消订单成功
            if (intent.getAction().equals(Global.ST_ORDER_CANCEL)) {
                presenter.getHttpData(0, true, mType);
            }
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                presenter.getHttpData(0, true, mType);
            }

            //登录成功成功
            if (intent.getAction().equals(Global.LOGIN_SUCCEED)) {
                presenter.getHttpData(0, true, mType);
            }

        }
    }
}
