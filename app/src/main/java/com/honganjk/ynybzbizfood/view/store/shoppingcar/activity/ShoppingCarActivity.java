package com.honganjk.ynybzbizfood.view.store.shoppingcar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.SelectAllListenerView;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarManagerData;
import com.honganjk.ynybzbizfood.pressenter.store.shoppingcar.ShoppingCarPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.base.activity.BaseStoreMainActivity;
import com.honganjk.ynybzbizfood.view.store.home.activity.StoreSubscribeActivity;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.adapter.ShoppingCarAdapterOrigen;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.interfaces.IShoppingCarParentInterfaces;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 说明:商城-购物车
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class ShoppingCarActivity extends BaseStoreMainActivity<IShoppingCarParentInterfaces.IShoppingCarInterface, ShoppingCarPresenter>
        implements IShoppingCarParentInterfaces.IShoppingCarInterface, SuperRecyclerView.ListSwipeViewListener, SelectAllListenerView, ShoppingCarAdapterOrigen.ShoppingCarSelectListener, ShoppingCarAdapterOrigen.ShoppingDeleteListener {

    List<ShoppingcarData.ObjsBean> mDatas = new ArrayList<>();

    ShoppingCarAdapterOrigen adapter;
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
    @BindView(R.id.bottom_navigation_bar)
    AHBottomNavigation bottomNavigationBar;
    @BindView(R.id.ril)
    RelativeLayout ril;
    @BindView(R.id.deleteall)
    TextView deleteall;
    @BindView(R.id.details)
    LinearLayout details;
    @BindView(R.id.contant)
    LinearLayout contant;

    private List<ShoppingcarData.ObjsBean> list = new ArrayList<>();
    private List<List<ShoppingcarData.ObjsBean>> lists = new ArrayList<>();
    private boolean changer;
    private boolean toolbarState = false;
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
        toolbar.setSubtitleTextColor(R.color.black);
        // TODO: 2017-09-08
        toolbar.setSubtitleTextColor(R.color.black);

        toolbar.addAction(0, "编辑");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (toolbarState == false) {
                    deleteall.setVisibility(View.GONE);
                    commit.setVisibility(View.VISIBLE);
                    details.setVisibility(View.VISIBLE);
                    toolbarState = true;
                } else {
                    deleteall.setVisibility(View.VISIBLE);
                    commit.setVisibility(View.GONE);
                    details.setVisibility(View.GONE);
                    toolbarState = false;
                }

                return false;
            }
        });
        //测量高度,并设置边距(外)
        measureRl();

        adapter = new ShoppingCarAdapterOrigen(this, lists);
        adapter.setListener(ShoppingCarActivity.this);
        adapter.setDeleteListener(ShoppingCarActivity.this);
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

    private void measureRl() {
        int high = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        bottomNavigationBar.measure(width, high);
        ril.measure(width, high);
        int h = bottomNavigationBar.getMeasuredHeight();

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ril.getLayoutParams();

        layoutParams.setMargins(0, 0, 0, h);

        ril.setLayoutParams(layoutParams);

    }

    private void Showformstate() {
        if (mDatas.size() < 1) {
            rl.setVisibility(View.GONE);
        } else {
            rl.setVisibility(View.VISIBLE);
        }

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
    public void getHttpData(List<ShoppingcarData.ObjsBean> datas) {
        mShoppingcarManagerData = new ShoppingcarManagerData(datas, this);
        adapter.setmSelectListenerView(mShoppingcarManagerData);
        lists.clear();
        for (int i = 0; i < datas.size(); i++) {
            if (!mDatas.contains(datas.get(i))) {
                mDatas.add(datas.get(i));
            }
        }//遍历集合,取出feature一样的数据,让入一个集合里
        if(datas.size()==1){
            lists.add(datas);
        }else {
            for (int i = 0; i < mDatas.size(); i++) {
                list = new ArrayList<>();
                list.add(mDatas.get(i));
                for (int i1 = i + 1; i1 < mDatas.size(); i1++) {
                    if (mDatas.get(i).getFeature().equals(mDatas.get(i1).getFeature())) {
                        if (!list.contains(mDatas.get(i1))) {
                            list.add(mDatas.get(i1));
                        }
                    }
                }
                if (!lists.contains(list)) {
                    if (lists.size() == 0) {
                        lists.add(list);
                    }
                    for (int i2 = 0; i2 < lists.size(); i2++) {
                        for (int i1 = 0; i1 < lists.get(i2).size(); i1++) {
                            for (int i3 = 0; i3 < list.size(); i3++) {
                                if (lists.get(i2).get(i1).getFeature().equals(list.get(i3).getFeature())) {
                                    changer = false;
                                }
                            }
                        }
                    }
                    if (changer == true) {
                        lists.add(list);
                    }
                    changer = true;
                }
            }
        }
        adapter.notifyDataSetChanged();
        //   Showformstate();   //根据状态显示
        // switchRoot.getRecyclerView().getLoaddFooterView().setAutoloaddingCompleData("共为你加载" + mDatas.size() + "条数据");
//        switchRoot.getRecyclerView().getLoaddFooterView().setStatus(LoadState.NoData);
    }


    @Override
    public void addAndSubtractNumberHttp(boolean isAddNumber) {

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
        sumPrice.setText("¥" + selectPrice);
        commit.setText("结算(" + selectCount + ")");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //把最后获得的数据放入集合
    List<ShoppingcarData.ObjsBean> Mlist = new ArrayList<>();

    @OnClick({R.id.commit, R.id.rl,R.id.deleteall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commit:
                if (Mlist.size() != 0) {
                    StoreSubscribeActivity.starUi(this, null, Mlist);
                    Mlist.clear();
                    lists.clear();
                    allSelect.setChecked(false);
                    commit.setText("结算(" + 0 + ")");
                    sumPrice.setText("¥" + 0);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.getToastShort("请选择商品");
                }
                break;
            case R.id.deleteall:
                String deteleItems="";
                for (int i = 0; i < Mlist.size(); i++) {
                    if(Mlist.get(i).getIsSelect()){
                        deteleItems=deteleItems+Mlist.get(i).getBid()+"-"+Mlist.get(i).getType()+";";
                    }
                }
                presenter.deleteCarts(0,0,deteleItems);
                deleteall.setVisibility(View.GONE);
                commit.setVisibility(View.VISIBLE);
                details.setVisibility(View.VISIBLE);
                allSelect.setChecked(false);
                sumPrice.setText("¥"+0);
                commit.setText("结算(0)");
                refresh();
                break;
        }
    }


    //回调接口,来获取数据
    @Override
    public void beSelected(ShoppingcarData.ObjsBean listBean) {
        if (listBean != null) {
            if (listBean.getIsSelect()) {
                Mlist.add(listBean);
            } else if (!listBean.getIsSelect() && Mlist.contains(listBean)) {
                Mlist.remove(listBean);
            }
        }
    }

    @Override
    public void deleteItem(boolean isdelete) {
        if (isdelete) {
            presenter.getData(true);
            refresh();
        }
    }

    private void refresh() {
        finish();
        Intent intent = new Intent(ShoppingCarActivity.this, ShoppingCarActivity.class);
        startActivity(intent);
    }
}
