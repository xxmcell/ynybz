package com.honganjk.ynybzbizfood.view.store.home.frament;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:产品详情的-评价
 * 作者： 杨阳; 创建于：  2017-07-07  16:01
 */
public class ProductDetailsEvaluateFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<ProductDetailsData.CommentsBean> mDatas = new ArrayList<>();
    CommonAdapter adapter;

    public ProductDetailsEvaluateFragment() {
    }

    public static ProductDetailsEvaluateFragment getInstance() {
        return new ProductDetailsEvaluateFragment();
    }
    @Override
    public int getContentView() {

        return R.layout.store_fragment_product_details;
    }


    @Override
    public void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build());

        if (mDatas.size()<1||mDatas==null){  //数据空

                ArrayList<String> strings = new ArrayList<>();
                strings.add("暂无评价");
                recyclerView.setAdapter(adapter = new CommonAdapter<String>(activity, R.layout.store_empty,strings ) {
                    @Override
                    public void convert(ViewHolder holder, String s) {
                        TextView view = (TextView) holder.getView(R.id.tv_empty);
                        view.setTextColor(getResources().getColor(R.color.main_red));
                        holder.setText(R.id.tv_empty, s);
                    }

                    @Override
                    public void convert(ViewHolder holder, List<String> t) {

                    }
                });
        }else {

                recyclerView.setAdapter(adapter = new CommonAdapter<ProductDetailsData.CommentsBean>(activity, R.layout.item_evaluate_2, mDatas) {
                    @Override
                    public void convert(ViewHolder holder, ProductDetailsData.CommentsBean data) {
                        holder.setImageBitmapCircle(R.id.avatar, data.getImg());
                        holder.setText(R.id.name, data.getName());
                        holder.setText(R.id.time, data.getTimeStr());
                        holder.setText(R.id.content, data.getContent());
                        SimpleRatingBar sb = holder.getView(R.id.grade);
                        sb.setRating(data.getScore());
                    }

                    @Override
                    public void convert(ViewHolder holder, List<ProductDetailsData.CommentsBean> t) {

                    }
                });
        }
    }

    public void setAdapter(List<ProductDetailsData.CommentsBean> details) {
        mDatas.addAll(details);
        adapter.notifyDataSetChanged();
    }

}
