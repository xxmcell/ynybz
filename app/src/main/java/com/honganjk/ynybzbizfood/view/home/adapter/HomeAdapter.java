package com.honganjk.ynybzbizfood.view.home.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.home.HomeData;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.utils.ui.divider.VerticalDividerItemDecoration;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;

import java.util.List;

/**
 * Created by yang on 2016/10/27.
 */

public class HomeAdapter extends CommonAdapter<HomeData> {
    BaseActivity mActivity;

    public HomeAdapter(BaseActivity context, List datas, SuperRecyclerView superView) {
        super(context, R.layout.item_home, datas);
        mActivity = context;


        superView.getRecyclerView().setLayoutManager(new GridLayoutManager(context, 3));
        superView.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .colorResId(R.color.gray).sizeResId(R.dimen.dp_1).build());

        superView.getRecyclerView().addItemDecoration(new VerticalDividerItemDecoration.Builder(context)
                .colorResId(R.color.gray).sizeResId(R.dimen.dp_1).showFooterDivider().build());
    }

    @Override
    public void convert(ViewHolder holder, final HomeData o) {
        holder.setImageBitmapCircle(R.id.picture, o.getIcon_url());
        holder.setText(R.id.comtent, o.getTitle());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityUi(o);
            }
        });
    }


    /**
     * 首页宫格启动对应的UI
     */
    public void startActivityUi(HomeData data) {

    }

}
