package com.honganjk.ynybzbizfood.view.store.shoppingcar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.SelectAllListenerView;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarManagerData;
import com.honganjk.ynybzbizfood.pressenter.store.shoppingcar.ShoppingCarPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.base.activity.BaseStoreMainActivity;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.adapter.ShoppingCarAdapter;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.interfaces.IShoppingCarParentInterfaces;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;
import com.honganjk.ynybzbizfood.widget.autoloadding.LoadState;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:商城-购物车
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class ShoppingCarActivity extends BaseStoreMainActivity<IShoppingCarParentInterfaces.IShoppingCarInterface, ShoppingCarPresenter>
        implements IShoppingCarParentInterfaces.IShoppingCarInterface, SuperRecyclerView.ListSwipeViewListener, SelectAllListenerView {

    ArrayList<ShoppingcarData.ObjsBean> mDatas = new ArrayList<>();
    ShoppingCarAdapter adapter;
    ShoppingcarManagerData mShoppingcarManagerData;
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    @BindView(R.id.allSelect)
    AnimCheckBox allSelect;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.rl)
    RelativeLayout rl;


    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, ShoppingCarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_shoppingcar;
    }


    @Override
    public void initView() {
        super.initView();
        toolbar.setTitle(getResources().getString(R.string.shoppingCar));
        toolbar.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitleColor(R.color.black);
        toolbar.setNavigationIcon(R.drawable.material_arrwos_white_green);

        // TODO: 2017-09-08
        if (mDatas.size() < 1) {
            toolbar.addAction(0, "");
        } else {
            toolbar.addAction(0, "编辑");
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) { //编辑
                // TODO: 2017-09-08
                ToastUtils.getToastLong("888");
                return false;
            }
        });


        Showformstate();   //根据状态显示

        adapter = new ShoppingCarAdapter(this, mDatas);
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 1));
        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_1).colorResId(R.color.white).build());
        switchRoot.setAdapter(adapter);
        presenter.getData(true);

        allSelect.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
            @Override
            public void onChange(AnimCheckBox checkBox, boolean checked) {
                if (mShoppingcarManagerData != null) {
                    mShoppingcarManagerData.setmIsAllSelect(checked);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void Showformstate() {
//        if (mDatas.size() < 1) {
//            rl.setVisibility(View.GONE);
//        } else {
//            rl.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public ShoppingCarPresenter initPressenter() {
        return new ShoppingCarPresenter();
    }

    @Override
    public int currentItem() {
        return 2;
    }

    @Override
    public void getData(List<ShoppingcarData.ObjsBean> datas) {
        mShoppingcarManagerData = new ShoppingcarManagerData(datas, this);
        adapter.setmSelectListenerView(mShoppingcarManagerData);
        mDatas.clear();
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        switchRoot.getRecyclerView().getLoaddFooterView().setAutoloaddingCompleData("共为你加载" + mDatas.size() + "条数据");
        switchRoot.getRecyclerView().getLoaddFooterView().setStatus(LoadState.NoData);
    }

    @Override
    public void addAndSubtractNumber(boolean isAddNumber) {

    }

    @Override
    public void clearData() {
        mDatas.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public StatusChangListener getStatusChangListener() {
        return switchRoot.getStatusChangListener();
    }

    @Override
    public <T> Collection<T> getValidData(Collection<T> c) {
        return switchRoot.getPagingHelp().getValidData(c);
    }

    @Override
    public void clearPagingData() {
        switchRoot.getPagingHelp().clear();
    }

    @Override
    public int getPageindex() {
        return switchRoot.getPagingHelp().getPageindex();
    }

    @Override
    public int getPageCount() {
        return switchRoot.getPagingHelp().getPageCount();
    }


    @Override
    public void onRefresh() {
        presenter.getData(true);
    }

    @Override
    public void onLoadding() {
        presenter.getData(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getData(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getData(true);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return switchRoot.getSwipeRefreshLayout();
    }

    @Override
    public void isAllSelect(boolean isSelect, int selectCount, double selectPrice) {
        allSelect.setChecked(isSelect);
        sumPrice.setText("¥" + selectPrice);
        commit.setText("结算(" + selectCount + ")");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
