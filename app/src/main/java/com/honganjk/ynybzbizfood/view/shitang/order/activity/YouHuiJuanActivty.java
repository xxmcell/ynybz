package com.honganjk.ynybzbizfood.view.shitang.order.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.YouHuiBean;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/13.
 */

public class YouHuiJuanActivty extends BaseListActivity {
    private ArrayList<YouHuiBean> mList;
    private CommonAdapter mAdpter;
    public static void startUiForesult(Activity context, ArrayList<YouHuiBean> list,int YOUHUIREQUSETCODE) {
        Intent intent = new Intent(context,YouHuiJuanActivty.class);
        intent.putParcelableArrayListExtra("youhui",list);
        context.startActivityForResult(intent,YOUHUIREQUSETCODE);
    }
    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_10).build();
    }

    @Override
    public void initView() {
        super.initView();
        toolbar.setBack(this);
        toolbar.setTitle("优惠劵");
        mAdpter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                Intent intent = getIntent();
                intent.putExtra("id",mList.get(position).getId());
                intent.putExtra("youhuiprice",mList.get(position).getAmount());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdpter = new CommonAdapter<YouHuiBean>(this, R.layout.item_yuhuijuan, mList) {
            @Override
            public void convert(ViewHolder holder, final YouHuiBean o) {
                holder.setText(R.id.youhui_money, o.getAmount()+"");
                int type = o.getType();
                String typeStr = "满减优惠";
                if(type == 1) {
                    typeStr = "店铺首单立减优惠";
                }
                holder.setText(R.id.youhui_type,"优惠劵");
                holder.setText(R.id.youhui_chendu, o.getDescs());
                holder.setText(R.id.yuhui_name,typeStr);

            }
        };
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_youhuijuan;
    }

    @Override
    public void parseIntent(Intent intent) {
        mList = intent.getParcelableArrayListExtra("youhui");
    }

    @Override
    public void onLoadding() {

    }

    @Override
    public void onRetryClick(ContextData data) {

    }

    @Override
    public void onEmptyClick(ContextData data) {

    }
}
