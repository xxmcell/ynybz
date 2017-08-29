package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseMvpFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.FoodsMenuPresenter;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.FoodDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;
import com.honganjk.ynybzbizfood.widget.linkedscrollview.LinkedScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 菜单列表
 * Created by Administrator on 2017/3/7 0007.
 */
@SuppressLint("ValidFragment")
public class GoodsListFragment extends BaseMvpFragment<HomeParentInterface.foodsMenu, FoodsMenuPresenter> implements
        HomeParentInterface.foodsMenu {

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    @BindView(R.id.linkedscrollview)
    LinkedScrollView linkedscrollview;

    private int id = -1;
    private int timeType;//1:今天中午 2：今天晚上 3：明天中午 4：明天晚上，0：早餐
    private CartBottomLayout.cartBottomLayoutListener listener;
    private FoodData foodData;
    private BusinessDetailsData businessData;

    @SuppressLint("ValidFragment")
    public GoodsListFragment(int id, int timeType, CartBottomLayout.cartBottomLayoutListener listener) {
        this.timeType = timeType;
        this.id = id;
        this.listener = listener;
    }

    public void changeFoodNum(FoodData.DishsBeanX.DishsBean data) {
        linkedscrollview.changeFoodNum(data);
    }

    public void updateData(BusinessDetailsData data) {
        this.businessData = data;
    }

    @Override
    public FoodsMenuPresenter initPressenter() {
        return new FoodsMenuPresenter();
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {
        linkedscrollview.setCartListener(listener);
        linkedscrollview.getRecycleViewAdapter().setOnItemClickListener(new OnItemClickListener<FoodData.DishsBeanX.DishsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, FoodData.DishsBeanX.DishsBean data, int position) {
                ArrayList datas = listener.getDatas();
                FoodDetailActivity.startUIForResult(GoodsListFragment.this, data, businessData, datas, foodData.getId());
            }

        });
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int hour = t.hour; // 0-23
        if ((timeType == 1 && hour >= 12) || (timeType == 2 && hour >= 19)) {
            tvNoOrder.setVisibility(View.VISIBLE);
            linkedscrollview.setVisibility(View.GONE);
        } else {
            tvNoOrder.setVisibility(View.GONE);
            linkedscrollview.setVisibility(View.VISIBLE);
            presenter.getFoodMenu(id, timeType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void foodsMenu(FoodData data) {

        if (data == null || data.getDishs() == null || data.getDishs().size() == 0) {
            tvNoOrder.setVisibility(View.VISIBLE);
            tvNoOrder.setText(getResources().getText(R.string.tv_no_food));
            linkedscrollview.setVisibility(View.GONE);
        } else {
            this.foodData = data;
            tvNoOrder.setVisibility(View.GONE);
            linkedscrollview.setVisibility(View.VISIBLE);
            linkedscrollview.setDatas(data.getDishs(), timeType);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        if (requestCode == 0x10 && resultCode == FoodDetailActivity.RESULT_OK) {
            Bundle bundle = data1.getExtras();
            ArrayList<FoodData.DishsBeanX.DishsBean> datas = bundle.getParcelableArrayList("datas");
            listener.clearDatas();
            listener.setDatas(datas);
            linkedscrollview.update(datas);
        }
    }

    @Override
    public void clearData() {

    }

    public void clearDatas() {
        linkedscrollview.clearDatas();
    }

    public int getMenuId() {
        return foodData.getId();
    }
}
