package com.honganjk.ynybzbizfood.view.store.order.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.MyApplication;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.order.fragment.StoreOrderFragment;

import java.util.ArrayList;
import java.util.List;

import static com.honganjk.ynybzbizfood.R.id.mrecyclerView;

/**
 * Created by Administrator on 2017-10-19.
 */

public class StoreOrderAdapters extends CommonAdapter<StoreOrderData2.ObjsBean> {

    StoreOrderFragment storeOrderFragments;
    private int type;
    private boolean changer;
    private int Type;
    private RecyclerView recylerView;

    public StoreOrderAdapters(StoreOrderFragment storeOrderFragment, List<StoreOrderData2.ObjsBean> mDatas, int mType) {
        super(storeOrderFragment.getContext(), R.layout.store_item_order,mDatas);
        storeOrderFragments=storeOrderFragment;
        Type=mType;
    }
    private StoreOrderData2.ObjsBean.DetailsBean datas;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean Data;

    private List<List<StoreOrderData2.ObjsBean.DetailsBean>> lists=new ArrayList<>();
    private List<StoreOrderData2.ObjsBean.DetailsBean> list = new ArrayList<>();
    private List<StoreOrderData2.ObjsBean.DetailsBean> detailslists = new ArrayList<>();

    @Override
    public void convert(ViewHolder holder, final StoreOrderData2.ObjsBean data) {
        recylerView = holder.getView(mrecyclerView);

        int thePrice=0;
        for (int i = 0; i < data.getDetails().size(); i++) {
            datas = data.getDetails().get(i);
            for (int i1 = 0; i1 < data.getDetails().get(i).getList().size(); i1++) {
                thePrice+=data.getDetails().get(i).getList().get(i1).getSum();
            }
        }
        if(datas==null){
            return;
        }else {
            for (int i1 = 0; i1 < datas.getList().size(); i1++) {
                Data = datas.getList().get(i1);
            }
        }
        for (int i = 0; i < data.getDetails().size(); i++) {
            detailslists.add(data.getDetails().get(i));
        }
        //将分类后的集合装进一个新的集合
        lists.clear();
        if(data.getDetails().size()==1){
            lists.add(data.details);
        }else {
            for (int i = 0; i < data.getDetails().size(); i++) {
                list= new ArrayList<>();
                list.add(data.getDetails().get(i));
                for (int i1 = 0; i1 < data.getDetails().size(); i1++) {
                    if(data.getDetails().get(i).getFeature().equals(data.getDetails().get(i1).getFeature())){
                        if(!list.contains(data.getDetails().get(i1))){
                            list.add(data.details.get(i1));
                        }
                    }
                }
                if(!lists.contains(list)){
                    if(lists.size()==0){
                        lists.add(list);
                    }
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

        holder.setText(R.id.price,"¥"+thePrice+".00");

        //按照供应商分类
        /**
         * 灰色按钮
         * 取消订单，删除订单,查看物流
         */
        final TextView statusGray = holder.getView(R.id.cancel);
        /**
         * 绿色按钮
         * 支付金额，确认收货，去评价
         */
        final TextView statusGreen = holder.getView(R.id.status);
        View boundary1 = holder.getView(R.id.boundary1);//分割线
        TextView price = holder.getView(R.id.price);//价格

        boundary1.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
//        "全部", "0待付款", "1待发货", "2待收货", "3待评价"
        type =data.getState();
        String str="";
        if(type ==0){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.VISIBLE);
            str="待付款";
        }else
        if(type ==1){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.GONE);
            str="待发货";
        }else
        if(type ==2){
            statusGray.setText("查看物流");
            statusGreen.setText("确认收货");
            statusGray.setVisibility(View.VISIBLE);
            statusGreen.setVisibility(View.VISIBLE);
            str="待收货";
        }else
        if(type ==3){
            statusGray.setText("查看物流");
            statusGreen.setText("去评价");
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.VISIBLE);
            str="待评价";
        }else
        if(Type==5){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.GONE);
        }else {
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.GONE);
        }

            setRecyclerView(lists,storeOrderFragments,str,holder);



        data.setViewShowStatus(statusGray, statusGreen, boundary1);
        final int finalThePrice = thePrice;
        //取消订单，删除订单,查看物流
                statusGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(storeOrderFragments.getActivity(),finalThePrice, statusGray.getText().toString(), data, Data,storeOrderFragments.presenter);
            }
        });

        //支付金额，确认收货，去评价

        statusGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(storeOrderFragments.getActivity(),finalThePrice, statusGreen.getText().toString(),data,Data,storeOrderFragments.presenter);
            }
        });
    }

    private void setRecyclerView(List<List<StoreOrderData2.ObjsBean.DetailsBean>> lists,
                                 final StoreOrderFragment storeOrderFragments, String str, ViewHolder holder) {


            recylerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
            recylerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(MyApplication.getContext()).sizeResId(R.dimen.dp_1).colorResId(R.color.gray_ee).build());
            MyAdapter myAdapter = new MyAdapter(storeOrderFragments, str, lists);
            recylerView.setAdapter(myAdapter);

    }

    @Override
    public void convert(ViewHolder holder, List<StoreOrderData2.ObjsBean> t) {

    }

    private class MyAdapter extends CommonAdapter<StoreOrderData2.ObjsBean.DetailsBean> {
        private String types;
        private LinearLayout parent;
        private TextView name;
        private RecyclerView thedatailrecyclerview;


        public MyAdapter(StoreOrderFragment storeOrderFragments, String str, List<List<StoreOrderData2.ObjsBean.DetailsBean>> detailslists) {
            super(storeOrderFragments.getContext(),R.layout.store_item_orders,detailslists,1);
            types=str;
        }

        @Override
        public void convert(ViewHolder holder, StoreOrderData2.ObjsBean.DetailsBean detailsBean) {

        }
        @Override
        public void convert(ViewHolder holder, List<StoreOrderData2.ObjsBean.DetailsBean> lists) {

                holder.setImageBitmapRound(R.id.businessLogo, lists.get(0).getIcon(), 5);
                holder.setText(R.id.name, lists.get(0).getFeature());
                holder.setText(R.id.orderStatus, types);
                name = holder.getView(R.id.name);
            thedatailrecyclerview = holder.getView(R.id.thedatailrecyclerview);

            thedatailrecyclerview.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
            thedatailrecyclerview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(MyApplication.getContext()).sizeResId(R.dimen.dp_1).colorResId(R.color.gray_ee).build());
            thedatailrecyclerview.setAdapter(new StoredetailsOrderAdapter(lists.get(0)));

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassifyActivity.startUI(storeOrderFragments.getActivity());
                }
            });
        }

    }
}
