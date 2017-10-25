package com.honganjk.ynybzbizfood.view.store.shoppingcar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarManagerData;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;
import com.honganjk.ynybzbizfood.widget.NumberSelectRect;
import com.honganjk.ynybzbizfood.widget.slips.SWSlipsLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-10-09.
 */

public class SecondLevelAdapter extends RecyclerView.Adapter<SecondLevelAdapter.MyHolder> {
    ArrayList<ShoppingcarData.ObjsBean> data;

    BaseActivity mContext;

    ShoppingcarManagerData shoppingcarManagerData;
    @BindView(R.id.checkBox)
    AnimCheckBox checkBox;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.numberSelectRect)
    NumberSelectRect numberSelectRect;
    @BindView(R.id.delete)
    TextView delete;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.sll)
    SWSlipsLayout sll;

    public SecondLevelAdapter(ArrayList<ShoppingcarData.ObjsBean> data, BaseActivity mContext, ShoppingcarManagerData shoppingcarManagerData) {
        this.data = data;
        this.mContext = mContext;
        this.shoppingcarManagerData = shoppingcarManagerData;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_shoppingcar, parent, false);//解决宽度不能铺满
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        SWSlipsLayout sll;
        AnimCheckBox checkBox;
        ImageView picture;
        TextView name;
        TextView type;
        TextView money;
        TextView price;
        TextView delete;
        NumberSelectRect numberSelectRect;
        LinearLayout linear;
        public MyHolder(View itemView) {
            super(itemView);
            sll=itemView.findViewById(R.id.sll);
            checkBox=itemView.findViewById(R.id.checkBox);
            picture=itemView.findViewById(R.id.picture);
            name=itemView.findViewById(R.id.name);
            type=itemView.findViewById(R.id.type);
            money=itemView.findViewById(R.id.money);
            price=itemView.findViewById(R.id.price);
            numberSelectRect=itemView.findViewById(R.id.numberSelectRect);
            delete=itemView.findViewById(R.id.delete);
            linear=itemView.findViewById(R.id.linear);
        }
    }
}
