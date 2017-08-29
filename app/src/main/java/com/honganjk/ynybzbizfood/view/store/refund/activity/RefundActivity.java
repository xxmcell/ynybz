package com.honganjk.ynybzbizfood.view.store.refund.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData;
import com.honganjk.ynybzbizfood.pressenter.store.refund.RefundPresenter;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.view.store.refund.fragment.RefundFragment;
import com.honganjk.ynybzbizfood.view.store.refund.view.IRefundParent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 说明:商城-退款
 * <p>
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class RefundActivity extends BaseMvpActivity<IRefundParent.IRefund, RefundPresenter>
        implements IRefundParent.IRefund {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    @BindView(R.id.viewPage2)
    ViewPager viewPage;
    String[] titles;
    public StoreOrderData.ObjsBean mData;


    public static void startUI(Activity activity, StoreOrderData.ObjsBean mData) {
        Intent intent = new Intent(activity, RefundActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("data", mData);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_refund;
    }

    @Override
    public void parseIntent(Intent intent) {
        mData = intent.getParcelableExtra("data");
    }

    @Override
    public void initView() {
        toolbar.setTitle("退款申请");
        toolbar.setBack(mActivity);
        toolbar.setTitleColor(R.color.black);
        toolbar.setNavigationIcon(R.drawable.material_arrwos_white_green);

        if (mData != null && mData.getState() == 1) {
            fragmentDatas.add(RefundFragment.getInstance(0));
            titles = new String[]{"仅退款"};
        } else if (mData != null && mData.getState() == 2) {
            fragmentDatas.add(RefundFragment.getInstance(0));
            fragmentDatas.add(RefundFragment.getInstance(1));
            titles = new String[]{"仅退款", "退货退款"};
        }

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);

    }

    @Override
    public RefundPresenter initPressenter() {
        return new RefundPresenter();
    }


    public void clearData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


}
