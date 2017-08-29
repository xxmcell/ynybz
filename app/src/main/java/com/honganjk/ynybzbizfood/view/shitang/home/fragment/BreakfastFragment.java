package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseMvpFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableListData;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class BreakfastFragment extends BaseMvpFragment implements CartBottomLayout.cartBottomLayoutListener {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.cartbottomlayout)
    CartBottomLayout cartBottomLayout;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.favorableContent)
    TextView favorableContent;

    private int id = -1;
    private List<BreakFragment> fragments = new ArrayList();
    private BreakFragment fragment1;

    public BreakfastFragment() {

    }

    @SuppressLint("ValidFragment")
    public BreakfastFragment(int id) {
        this.id = id;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_break;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private CartBottomLayout.cartBottomLayoutListener bottomListener = new CartBottomLayout.cartBottomLayoutListener() {
        @Override
        public void setDatas(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {
            cartBottomLayout.setData(datas);
        }

        @Override
        public void changeFoodNum(FoodData.DishsBeanX.DishsBean data) {
            fragment1.updateFoods(data);
        }

        @Override
        public void clearDatas() {

        }

        @Override
        public void statusChanged(boolean enable) {
        }

        @Override
        public ArrayList getDatas() {
            return null;
        }

        @Override
        public int getMenuId() {
            return 1;
        }

    };

    public void update(BusinessDetailsData data) {
        StringBuffer noticeContent = new StringBuffer(data.getBulletin() + "  ");
        if (data.getFavors() != null && data.getFavors().size() != 0) {
            noticeContent.append(getResources().getString((int) R.string.tv_favors));
        }
        for (int i = 0; i < data.getFavors().size(); i++) {
            noticeContent.append(i + 1).append("、").append(data.getFavors().get(i).getTitle()).append("\n");
        }
        tvNotice.setText(noticeContent);
        cartBottomLayout.setProperty(data);
        fragment1.updateData(data);
    }

    @Override
    public void initView() {
        cartBottomLayout.setListener(bottomListener);
        fragment1 = new BreakFragment(id, 1, this);
        fragments.add(fragment1);

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());

        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(adapter);

        //购物车的价格改变监听
        cartBottomLayout.setPriceListener(new CartBottomLayout.PriceListener() {
            @Override
            public void changeCallback(double price) {
                changeFavorableInfo(price);
            }
        });
    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }


    @Override
    public void setDatas(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {
        cartBottomLayout.setData(datas);
    }


    @Override
    public void changeFoodNum(FoodData.DishsBeanX.DishsBean data) {
        cartBottomLayout.changeFoodNum(data, false);
    }

    @Override
    public void clearDatas() {
    }

    @Override
    public void statusChanged(boolean enable) {


    }

    @Override
    public ArrayList getDatas() {
        return cartBottomLayout.getData();
    }

    @Override
    public int getMenuId() {
        return 0;
    }

    /**
     * 优惠信息的集合
     */
    List<FavorableListData> mFavorableDatas = new ArrayList<>();

    /**
     * 设置优惠信息
     *
     * @param datas
     */
    public void setFavorableContent(List<FavorableListData> datas) {
        if (datas == null || datas.size() == 0) {
            favorableContent.setVisibility(View.GONE);
            return;
        } else {
            favorableContent.setVisibility(View.VISIBLE);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            sb.append(datas.get(i).getDescs() + "；");
            mFavorableDatas.add(datas.get(i));
        }
        favorableContent.setText(sb.toString());
    }

    /**
     * 通过价格改变优惠信息
     *
     * @param price
     */
    private void changeFavorableInfo(double price) {
        if (mFavorableDatas.size() == 0) {
            favorableContent.setVisibility(View.GONE);
            return;
        } else {
            favorableContent.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < mFavorableDatas.size(); i++) {
            if (price >= mFavorableDatas.get(i).getTotal()) {
                favorableContent.setText(mFavorableDatas.get(i).getDescs());
            }
        }
        if (price == 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mFavorableDatas.size(); i++) {
                sb.append(mFavorableDatas.get(i).getDescs() + "；");
            }
            favorableContent.setText(sb.toString());
        }
    }
}
