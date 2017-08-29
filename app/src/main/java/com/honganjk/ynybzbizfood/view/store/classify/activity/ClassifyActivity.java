package com.honganjk.ynybzbizfood.view.store.classify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.mode.javabean.store.classify.ClassifyRequestBean;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.pressenter.store.classify.ClassifyPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.store.base.activity.BaseStoreMainActivity;
import com.honganjk.ynybzbizfood.view.store.classify.adapter.StoreClassifyAdapter;
import com.honganjk.ynybzbizfood.view.store.classify.interfaces.IClassifyParentInterfaces;
import com.honganjk.ynybzbizfood.view.store.home.activity.ProductDetailsActivity;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明:商城-分类页面
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 * <p>
 * 黑空星云
 */
public class ClassifyActivity extends BaseStoreMainActivity<IClassifyParentInterfaces.IClassifyInterface, ClassifyPresenter>
        implements IClassifyParentInterfaces.IClassifyInterface, SuperRecyclerView.ListSwipeViewListener {
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    ArrayList<StoreHomeData.ObjsBean> mDatas = new ArrayList<>();
    StoreClassifyAdapter adapter;
    @BindView(R.id.filtrateClassify)
    TextView filtrateClassify;
    @BindView(R.id.filtrateBrand)
    TextView filtrateBrand;
    @BindView(R.id.filtrateSynthesize)
    TextView filtrateSynthesize;
    @BindView(R.id.searchView)
    SearchView searchView;
    ArrayList<PopupPulldown.PullDownData> mFiltrareDatas = new ArrayList<>();
    PopupPulldown pp;
    ClassifyRequestBean requestBean;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, ClassifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_classify;
    }

    @Override
    public void initView() {
        super.initView();
        toolbar.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.material_arrwos_white_green);

        adapter = new StoreClassifyAdapter(this, mDatas);
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 2));
//        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_8).colorResId(R.color.gray_ee).build());
        switchRoot.setAdapter(adapter);

        pp = new PopupPulldown(mActivity, mFiltrareDatas);
        requestBean = new ClassifyRequestBean();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtils.getToastLong(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ToastUtils.getToastLong(newText);
                return false;
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<StoreHomeData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, StoreHomeData.ObjsBean data, int position) {
                ProductDetailsActivity.startUI(mActivity, data.getId());
            }
        });


        reruestData(true);
    }

    @Override
    public ClassifyPresenter initPressenter() {
        return new ClassifyPresenter();
    }

    @Override
    public int currentItem() {
        return 1;
    }


    @Override
    public void onRefresh() {
        reruestData(true);
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
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return switchRoot.getSwipeRefreshLayout();
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }


    @Override
    public void onLoadding() {
        reruestData(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        reruestData(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        reruestData(true);
    }

    @Override
    public void getHttpData(List<StoreHomeData.ObjsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void filtrateClassify(List<PopupPulldown.PullDownData> datas) {
        mFiltrareDatas.clear();
        mFiltrareDatas.addAll(datas);
        pp.showPopupWindow((View) filtrateClassify.getParent());
    }

    @Override
    public void filtrateBrand(List<PopupPulldown.PullDownData> datas) {
        mFiltrareDatas.clear();
        mFiltrareDatas.addAll(datas);
        pp.showPopupWindow((View) filtrateBrand.getParent());
    }

    @Override
    public void addShoppingCar(boolean datas) {

    }


    @OnClick({R.id.filtrateClassify, R.id.filtrateBrand, R.id.filtrateSynthesize})
    public void onViewClicked(View view) {
        ArrayList<PopupPulldown.PullDownData> datas = new ArrayList<>();

        PopupPulldown.OnClickCallback callback = null;
        switch (view.getId()) {
            case R.id.filtrateClassify:
                presenter.filtrateClassify();
                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        requestBean.setType(id);
                        reruestData(filtrateClassify, content);
                    }
                });
                break;
            case R.id.filtrateBrand:
                presenter.filtrateBrand();
                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        requestBean.setBrand(id);
                        reruestData(filtrateBrand, content);
                    }
                });
                break;
            case R.id.filtrateSynthesize:
                presenter.getIntegetionPullDownListData(mFiltrareDatas);
                pp.showPopupWindow((View) filtrateSynthesize.getParent());
                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        requestBean.setSort(id);
                        reruestData(filtrateSynthesize, content);
                    }
                });
                break;
            default:
                if (callback != null) {
                    pp.setOnClickCallback(callback);
                }
                break;
        }
    }

    private void reruestData(TextView view, String content) {
        view.setText(content);
        requestBean.setFirstRequest(true);
        presenter.getData(requestBean);
    }

    private void reruestData(boolean isFirst) {
        requestBean.setFirstRequest(isFirst);
        presenter.getData(requestBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
