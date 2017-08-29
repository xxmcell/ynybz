package com.honganjk.ynybzbizfood.view.peihu.collect.fragment;

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
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.pressenter.peihu.collect.CollectPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.peihu.collect.activity.CollectActivity;
import com.honganjk.ynybzbizfood.view.peihu.collect.adapter.NurserHomeAdapter;
import com.honganjk.ynybzbizfood.view.peihu.collect.interfaces.CollectParentInterfaces;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryDetailsActivity;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:陪护-收藏
 * 360621904@qq.com 杨阳 2017/3/24  13:31
 */
@SuppressLint("ValidFragment")
public class CollectFragment extends BaseListFragment<CollectParentInterfaces.ICollect, CollectPresenter> implements CollectParentInterfaces.ICollect {
    /**
     * 收藏的护理人员类型，1-全天工，2-钟点工
     */
    private int mType;
    ArrayList<NurserCommendData> mDatas = new ArrayList<>();
    NurserHomeAdapter adapter;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @SuppressLint("ValidFragment")
    public CollectFragment() {
    }

    public CollectFragment(int type) {
        this.mType = type;
    }

    public static CollectFragment getInstance(int type) {
        return new CollectFragment(type);
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

        presenter.getHttpData(true, mType);
        adapter.setOnItemClickListener(new OnItemClickListener<NurserCommendData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NurserCommendData data, int position) {
                NursingRecoveryDetailsActivity.startUI(activity,data.getId(),data.getType());
            }
        });


    }

    @Override
    public CommonAdapter getAdapter() {
        return adapter = new NurserHomeAdapter(activity, mDatas);
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
    public CollectPresenter initPressenter() {
        return new CollectPresenter();
    }

    @Override
    public void getHttpData(List<NurserCommendData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        if (activity instanceof CollectActivity) {
            ((CollectActivity) activity).setCount(mType == 1 ? 0 : 1, mDatas.size());
        }
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
            if (intent.getAction().equals(Global.ST_ORDER_CANCEL) ) {
                presenter.getHttpData(true, mType);
            }
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED) ) {
                presenter.getHttpData(true, mType);
            }

            //登录成功成功
            if (intent.getAction().equals(Global.LOGIN_SUCCEED) ) {
                presenter.getHttpData(true, mType);
            }

        }
    }
}
