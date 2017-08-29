package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.PromotionInfoBean;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.PromotionInfoPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 充值优惠界面
 */

public class ChongZhiActivity extends BaseListActivity<MyParentInterfaces.IPromotionInfo, PromotionInfoPresenter> implements MyParentInterfaces.IPromotionInfo {
    private List<PromotionInfoBean> list = new ArrayList();
    private CommonAdapter adapter;
    private int mPosition;

    public static void startUi(Context context) {
        Intent intent = new Intent(context, ChongZhiActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        super.initView();
        toolbar.setBack(this);
        toolbar.setTitle("去充值");
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.getRecyclerView().setLoadMoreEnabled(false);
        presenter.getPromotionInfo(true);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build();
    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter = new CommonAdapter<PromotionInfoBean>(this, R.layout.item_promotioninfo, list) {
            @Override
            public void convert(final ViewHolder holder, final PromotionInfoBean promotionInfoBean) {

                if (promotionInfoBean.getExtra() != 0) {
                    holder.setText(R.id.tv_promotion, "充值" + promotionInfoBean.getReality() + "送" + promotionInfoBean.getExtra() + "元");
                } else {
                    holder.setText(R.id.tv_promotion, "充值" + promotionInfoBean.getReality() + "元");
                }


                if (holder.getmPosition() == mPosition) {
                    holder.setImageResource(R.id.iv_check, R.mipmap.iv_pay_choice_up);
                } else {
                    holder.setImageResource(R.id.iv_check, R.mipmap.iv_pay_choice_down);
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        presenter.getPromotionInfo(true);
    }

    @OnClick(R.id.tv_commit)
    public void Onclick() {
        if (isLogin(true)) {
            RechargeActivity.startUi(ChongZhiActivity.this, list.get(mPosition));
        }
    }

    @Override
    public PromotionInfoPresenter initPressenter() {
        return new PromotionInfoPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_promotioninfo;
    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public void onLoadding() {
        presenter.getPromotionInfo(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getPromotionInfo(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getPromotionInfo(true);
    }

    @Override
    public void showYouHui(final ArrayList<PromotionInfoBean> list) {
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                //开启充值界面
                mPosition = position;
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void clearData() {
        list.clear();
    }
}
