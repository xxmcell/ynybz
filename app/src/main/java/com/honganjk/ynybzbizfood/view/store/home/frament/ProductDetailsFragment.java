package com.honganjk.ynybzbizfood.view.store.home.frament;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:产品详情的图片展示
 * 作者： 杨阳; 创建于：  2017-07-07  16:01
 */
public class ProductDetailsFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<ProductDetailsData.DetailsBean> mDatas = new ArrayList<>();
    CommonAdapter adapter;

    public ProductDetailsFragment() {
    }

    public static ProductDetailsFragment getInstance() {
        return new ProductDetailsFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.store_fragment_product_details;
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setAdapter(adapter = new CommonAdapter<ProductDetailsData.DetailsBean>(activity, R.layout.store_item_prioduct_details, mDatas) {
            @Override
            public void convert(ViewHolder holder, ProductDetailsData.DetailsBean s) {

                ImageView view=   (ImageView) holder.getView(R.id.picture);
//                holder.setImageBitmap(R.id.picture, s.getImg());
//                GlideUtils.show(holder.getView(R.id.picture), s.getImg());
//                Glide.with(ProductDetailsFragment.this)
//                        .load(s.getImg())
//                        .error(R.mipmap.logo)
//                        .placeholder(R.mipmap.fail_picture)
//                        .into((ImageView) holder.getView(R.id.picture));

                GlideUtils2.show(s.getImg(),view);
            }
        });
    }

    public void setAdapter(List<ProductDetailsData.DetailsBean> details) {
        mDatas.addAll(details);
        adapter.notifyDataSetChanged();
    }

}
