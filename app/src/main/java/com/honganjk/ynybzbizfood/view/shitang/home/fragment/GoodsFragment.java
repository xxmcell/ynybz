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

import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseMvpFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableListData;
import com.honganjk.ynybzbizfood.widget.SingleTitle;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 店家-商品
 */
public class GoodsFragment extends BaseMvpFragment implements CartBottomLayout.cartBottomLayoutListener {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.cartbottomlayout)
    CartBottomLayout cartBottomLayout;
    @BindView(R.id.SingleTitle)
    com.honganjk.ynybzbizfood.widget.SingleTitle SingleTitle;
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    @BindView(R.id.favorableContent)
    TextView favorableContent;

    private int id = -1;
    private List<GoodsListFragment> fragments = new ArrayList();
    private List<String> tabs = new ArrayList();
    private GoodsListFragment fragment1;
    private GoodsListFragment fragment2;
    private GoodsListFragment fragment3;
    private GoodsListFragment fragment4;

    public GoodsFragment() {

    }

    @SuppressLint("ValidFragment")
    public GoodsFragment(int id) {
        this.id = id;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_goods;
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

        }

        @Override
        public void changeFoodNum(FoodData.DishsBeanX.DishsBean data) {
            fragments.get(viewpager.getCurrentItem()).changeFoodNum(data);
        }

        @Override
        public void clearDatas() {
            fragments.get(viewpager.getCurrentItem()).clearDatas();
        }

        @Override
        public void statusChanged(boolean enable) {
            SingleTitle.setClickEnable(enable);
        }

        @Override
        public ArrayList getDatas() {
            return null;
        }

        @Override
        public int getMenuId() {
            return (fragments.get(viewpager.getCurrentItem())).getMenuId();
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
        for (GoodsListFragment fragment : fragments) {
            fragment.updateData(data);
        }
    }

    @Override
    public void initView() {
        cartBottomLayout.setListener(bottomListener);
        fragment1 = new GoodsListFragment(id, 1, this);
        fragments.add(fragment1);
        fragment2 = new GoodsListFragment(id, 2, this);
        fragments.add(fragment2);
        fragment3 = new GoodsListFragment(id, 3, this);
        fragments.add(fragment3);
        fragment4 = new GoodsListFragment(id, 4, this);
        fragments.add(fragment4);

        tabs.add(getResources().getString(R.string.tv_today_lunch));
        tabs.add(getResources().getString(R.string.tv_today_dinner));
        tabs.add(getResources().getString(R.string.tv_tomorrow_lunch));
        tabs.add(getResources().getString(R.string.tv_tomorrow_dinner));

        SingleTitle.setTitle(tabs.get(0), tabs.get(1), tabs.get(2), tabs.get(3));

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());

        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(adapter);

        SingleTitle.setOnClickCallback(new SingleTitle.OnClickCallback() {
            @Override
            public void onClick(String content, int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onErrorMsg(int position) {
                if (position == viewpager.getCurrentItem()) {
                    return;
                }
                MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
                        .title(getResources().getString(R.string.app_name_simple))
                        .titleColorRes(R.color.main_color)
                        .negativeText("确定")
                        .negativeColorRes(R.color.main_color)
                        .content(String.format(getResources().getString(R.string.tv_cart_nomore), tabs.get(viewpager.getCurrentItem())))
                        .build();
                materialDialog.show();

            }
        });
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


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
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
        cartBottomLayout.clearDatas();
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
