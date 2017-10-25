package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseMvpFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.BreakfastMenuPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.FoodDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class BreakFragment extends BaseMvpFragment<HomeParentInterface.breakfastMenu, BreakfastMenuPresenter> implements
        HomeParentInterface.breakfastMenu {

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int id = -1;
    private int timeType;//1:今天中午 2：今天晚上 3：明天中午 4：明天晚上 0：早餐
    private CartBottomLayout.cartBottomLayoutListener listener;
    private ArrayList<FoodData.DishsBeanX.DishsBean> datas = new ArrayList<>();
    private BusinessDetailsData businessData;
    private MenuAdapter adapter;

    public BreakFragment() {

    }

    @SuppressLint("ValidFragment")
    public BreakFragment(int id, int timeType, CartBottomLayout.cartBottomLayoutListener listener) {
        this.timeType = timeType;
        this.id = id;
        this.listener = listener;
    }

    public void updateFoods(FoodData.DishsBeanX.DishsBean data) {
        int position = datas.indexOf(data);
        adapter.notifyItemChanged(position, data);
// 1       linkedscrollview.updateItem(data);
    }

    public void updateData(BusinessDetailsData data) {
        this.businessData = data;
    }

    @Override
    public BreakfastMenuPresenter initPressenter() {
        return new BreakfastMenuPresenter();
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_break2;
    }

    @Override
    public void initView() {

        adapter = new MenuAdapter(getContext(), datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(activity).sizeResId(R.dimen.dp_1).colorResId(R.color.gray_ee).build());
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                ArrayList datas = listener.getDatas();
                FoodDetailActivity.startUIForResult(BreakFragment.this, (FoodData.DishsBeanX.DishsBean) data, businessData, datas, 1);
            }
        });

        presenter.getBreakfastMenu(id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void breakfastMenu(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {

        this.datas.clear();
        for (FoodData.DishsBeanX.DishsBean d : datas) {
            d.setTimeType(0);
        }
        this.datas.addAll(datas);
        adapter.notifyDataSetChanged();
// 2       linkedscrollview.setDatas(datas, timeType);
    }

    public void update(List<FoodData.DishsBeanX.DishsBean> teams) {
        for (FoodData.DishsBeanX.DishsBean item : datas) {
            if (teams.contains(item)) {
                item.setNum(teams.get(teams.indexOf(item)).getNum());
            } else {
                item.setNum(0);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void f(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {
        listener.clearDatas();
        listener.setDatas(datas);
        update(datas);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        if (requestCode == 0x10 && resultCode == FoodDetailActivity.RESULT_OK) {
            Bundle bundle = data1.getExtras();
            ArrayList<FoodData.DishsBeanX.DishsBean> datas = bundle.getParcelableArrayList("datas");
            f(datas);
        }
    }

    @Override
    public void clearData() {
        ArrayList<FoodData.DishsBeanX.DishsBean> datas = new ArrayList<>();
        f(datas);
    }

    public class MenuAdapter extends CommonAdapter<FoodData.DishsBeanX.DishsBean> {

        public MenuAdapter(Context context, List<FoodData.DishsBeanX.DishsBean> datas) {
            super(context, R.layout.item_food, datas);
        }

        @Override
        public void convert(final ViewHolder holder, final FoodData.DishsBeanX.DishsBean team) {
            holder.setVisible(R.id.tv_title, false);
            holder.setText(R.id.tv_name, team.getName());
            holder.setText(R.id.tv_sales, String.format(getContext().getString((int) R.string.tv_sales), team.getTotal()));
            holder.setText(R.id.tv_price, String.format(getContext().getString((int) R.string.tv_price), team.getPrice()));
            holder.setImageBitmapRound(R.id.imageview, team.getImg(),5);
            holder.setOnClickListener(R.id.iv_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    team.setNum(team.getNum() + 1);
                    setNum(team.getNum(), holder);
                    listener.changeFoodNum(team);
                }
            });
            holder.setOnClickListener(R.id.iv_reduce, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    team.setNum(team.getNum() - 1);
                    setNum(team.getNum(), holder);
                    listener.changeFoodNum(team);
                }
            });
            setNum(team.getNum(), holder);
        }

        @Override
        public void convert(ViewHolder holder, List<FoodData.DishsBeanX.DishsBean> t) {

        }

        private void setNum(int num, ViewHolder holder) {
            if (num == 0) {
                holder.setVisible(R.id.iv_reduce, false);
                holder.setVisible(R.id.tv_num, false);
            } else {
                holder.setVisible(R.id.iv_reduce, true);
                holder.setVisible(R.id.tv_num, true);
                holder.setText(R.id.tv_num, num);
            }

        }


    }


}
