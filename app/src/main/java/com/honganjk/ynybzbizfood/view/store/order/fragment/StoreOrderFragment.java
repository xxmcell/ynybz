package com.honganjk.ynybzbizfood.view.store.order.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.pressenter.store.order.StoreOrderPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.order.activity.StoreOrderDetailsActivity;
import com.honganjk.ynybzbizfood.view.store.order.adapter.StoreOrderAdapter;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 说明:商城-我的订单
 * 作者： 杨阳; 创建于：  2017-07-11  15:27
 */
@SuppressLint("ValidFragment")
public class StoreOrderFragment extends BaseListFragment<StoreOrderParentInterfaces.IOrder, StoreOrderPresenter>
        implements StoreOrderParentInterfaces.IOrder {
    /**
     * 0:待支付; 1:待服务; 2:服务中;4:已完成;
     */
    private int mType;
    ArrayList<StoreOrderData2.ObjsBean> mDatas = new ArrayList<>();
    StoreOrderAdapter adapter;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    private List<Integer> list;


    @SuppressLint("ValidFragment")
    public StoreOrderFragment() {
    }

    public StoreOrderFragment(int type) {

        this.mType = type;
    }

    public static StoreOrderFragment getInstance(int type) {

        return new StoreOrderFragment(type);
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

        presenter.getHttpData( true, mType);

        adapter.setOnItemClickListener(new OnItemClickListener<StoreOrderData2.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, StoreOrderData2.ObjsBean data, int position) {

                Gson g=new Gson();
                String resoult=g.toJson(data);

                StoreOrderDetailsActivity.starUi(StoreOrderFragment.this, REQUEST_CODE,resoult);
            }

        });
    }
    @Override
    public CommonAdapter getAdapter() {
        return adapter = new StoreOrderAdapter(this, mDatas,mType);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //详情页面操作后列表页要刷新
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data.getBooleanExtra("isRefresh", false)) {
                    presenter.getHttpData(true, mType);
                }
            }
        }
        //评价成功
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK + 1) {
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
    public StoreOrderPresenter initPressenter() {
        return new StoreOrderPresenter(REQUEST_CODE);
    }

    @Override
    public void getHttpData(int total, List<StoreOrderData2.ObjsBean> datas) {

        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        // 0:待支付; 1:待服务; 2:服务中;4:已完成;


    }

    @Override
    public void confirmCompleted(boolean data) {
        presenter.getHttpData(true, mType);
    }

    @Override
    public void deleteOrder(boolean data) {
        presenter.getHttpData( true, mType);
        showInforSnackbar("删除成功");
    }

    @Override
    public void cancleOrder(boolean data) {
        presenter.getHttpData( true, mType);
        showInforSnackbar("取消成功成功");
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
        presenter.getHttpData( true, mType);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getHttpData( true, mType);
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
        presenter.getHttpData( true, mType);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //取消订单成功
            if (intent.getAction().equals(Global.ST_ORDER_CANCEL)) {
                presenter.getHttpData( true, mType);
            }
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                presenter.getHttpData( true, mType);
            }

            //登录成功
            if (intent.getAction().equals(Global.LOGIN_SUCCEED)) {
                presenter.getHttpData( true, mType);
            }

        }
    }
}
