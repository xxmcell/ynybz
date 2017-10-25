package com.honganjk.ynybzbizfood.view.store.order.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.MyApplication;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.order.activity.StoreOrderActivity;
import com.honganjk.ynybzbizfood.view.store.order.fragment.StoreOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:我的订单-适配器
 * 360621904@qq.com 杨阳 2017/3/24  14:17
 */
public class StoreOrderAdapter extends CommonAdapter<StoreOrderData2.ObjsBean> {
    BaseActivity context;
    StoreOrderFragment mContext;
    int type;
    private boolean changer;



    public StoreOrderAdapter(StoreOrderFragment context, List<StoreOrderData2.ObjsBean> datas, int mtype) {
        super(context.getContext(), R.layout.store_item_order, datas);
        mContext=context;
    }

    private StoreOrderData2.ObjsBean.DetailsBean datas;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean Data;

    private List<List<StoreOrderData2.ObjsBean.DetailsBean>> lists=new ArrayList<>();
    private List<StoreOrderData2.ObjsBean.DetailsBean> list = new ArrayList<>();
    private List<StoreOrderData2.ObjsBean.DetailsBean> detailslists = new ArrayList<>();

    @Override
    public void convert(final ViewHolder holder, final StoreOrderData2.ObjsBean data) {
//        LinearLayout linearlayout=holder.getView(R.id.linearcontant);
        int thePrice=0;
        for (int i = 0; i < data.getDetails().size(); i++) {
            datas = data.getDetails().get(i);
        }
        if(datas==null){
            return;
        }else {
            for (int i1 = 0; i1 < datas.getList().size(); i1++) {
                Data = datas.getList().get(i1);
                thePrice=thePrice+Data.getPrice();
            }
        }
//        lists.clear();
//        if(data.getDetails().size()==1){
//            lists.add(data.details);
//        }else {
//            linearlayout.setVisibility(View.GONE);
//            for (int i = 0; i < data.getDetails().size(); i++) {
//                list= new ArrayList<>();
//                list.add(data.getDetails().get(i));
//                for (int i1 = 0; i1 < data.getDetails().size(); i1++) {
//                    if(data.getDetails().get(i).getFeature().equals(data.getDetails().get(i1).getFeature())){
//                        if(!list.contains(data.getDetails().get(i1))){
//                            list.add(data.details.get(i1));
//                        }
//                    }
//                }
//                if(!lists.contains(list)){
//                    if(lists.size()==0){
//                        lists.add(list);
//                    }
//                }
//                for (int i2 = 0; i2 < lists.size(); i2++) {
//                    for (int i1 = 0; i1 < lists.get(i2).size(); i1++) {
//                        for (int i3 = 0; i3 < list.size(); i3++) {
//                            if (lists.get(i2).get(i1).getFeature().equals(list.get(i3).getFeature())) {
//                                changer = false;
//                            }
//                        }
//                    }
//                }
//                if (changer == true) {
//                    lists.add(list);
//                }
//                changer = true;
//            }
//        }
        for (int i = 0; i < data.getDetails().size(); i++) {
            detailslists.add(data.getDetails().get(i));
        }


        holder.setText(R.id.price,thePrice);
        //按照供应商分类

        setRecyclerView(detailslists,data,Data,holder);

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
       type=data.getState();
        if(type==0){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.VISIBLE);
        }
        if(type==1){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.GONE);
        }
        if(type==2){
            statusGray.setText("查看物流");
            statusGreen.setText("确认收货");
            statusGray.setVisibility(View.VISIBLE);
            statusGreen.setVisibility(View.VISIBLE);
        }
        if(type==3){
            statusGray.setText("查看物流");
            statusGreen.setText("去评价");
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.VISIBLE);
        }
        data.setViewShowStatus(statusGray, statusGreen, boundary1);

        //取消订单，删除订单,查看物流
        statusGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(mContext.getActivity(), 0,statusGray.getText().toString(), data,Data, mContext.presenter);
            }
        });

        //支付金额，确认收货，去评价
        statusGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(mContext.getActivity(),0, statusGreen.getText().toString(),data,Data, mContext.presenter);
            }
        });




    }
    @Override
    public void convert(ViewHolder holder, List<StoreOrderData2.ObjsBean> t) {

    }
    private void setRecyclerView(List<StoreOrderData2.ObjsBean.DetailsBean> lists, StoreOrderData2.ObjsBean data, StoreOrderData2.ObjsBean.DetailsBean.ListBean data1, ViewHolder holder) {
        RecyclerView  recyclerView=holder.getView(R.id.mrecyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(MyApplication.getContext()).sizeResId(R.dimen.dp_1).colorResId(R.color.gray_ee).build());
        recyclerView.setAdapter(new myAdapter(mContext,lists,data,Data));
    }


    private class myAdapter extends CommonAdapter <StoreOrderData2.ObjsBean.DetailsBean>{
        Context context;
        private StoreOrderData2.ObjsBean mData;
        private StoreOrderData2.ObjsBean.DetailsBean.ListBean Datas;
        private TextView type;
        private TextView text;
        private TextView presentPrice;
        private TextView orginalPrice;
        private TextView number;
        private ImageView picture;

        public myAdapter(StoreOrderFragment mContext, List<StoreOrderData2.ObjsBean.DetailsBean> lists, StoreOrderData2.ObjsBean data, StoreOrderData2.ObjsBean.DetailsBean.ListBean listBean) {
            super(StoreOrderActivity.getStoreOrderActivity(),R.layout.store_item_orders,lists);
            this.mData=data;

        }

        @Override
        public void convert(ViewHolder holder, List<StoreOrderData2.ObjsBean.DetailsBean> detailsBean) {
            LinearLayout parent=holder.getView(R.id.contants);
//            parent.removeAllViews();
                holder.setImageBitmapRound(R.id.businessLogo, detailsBean.get(0).getIcon(), 5);
                holder.setText(R.id.orderStatus, mData.getStateStr());
                holder.setText(R.id.name,detailsBean.get(0).getFeature());

                for (int i1 = 0; i1 < detailsBean.get(0).getList().size(); i1++) {
                    View addView= LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.store_item_order_items,null);
                    picture = addView.findViewById(R.id.picture);
                    type = addView.findViewById(R.id.type);
                    text = addView.findViewById(R.id.text);
                    presentPrice = addView.findViewById(R.id.presentPrice);
                    orginalPrice = addView.findViewById(R.id.originalPrice);
                    number = addView.findViewById(R.id.number);
                    GlideUtils.show(picture,detailsBean.get(0).getList().get(i1).getImg(),new GlideOptions.Builder().bulider());
                    type.setText(detailsBean.get(0).getList().get(i1).getTitle());
                    text.setText("规格"+detailsBean.get(0).getList().get(i1).getLabel());
                    presentPrice.setText(detailsBean.get(0).getList().get(i1).getMoneyStr());
                    detailsBean.get(0).getList().get(i1).getPriceStr(orginalPrice);
                    number.setText(detailsBean.get(0).getList().get(i1).getNumStr());
                    parent.addView(addView);
                }
        }
        @Override
        public void convert(ViewHolder holder, StoreOrderData2.ObjsBean.DetailsBean detailsBean) {
            LinearLayout titlecontant=holder.getView(R.id.titlecontant);
            for (int i = 0; i < detailsBean.getList().size(); i++) {
                if(i==0){
                    titlecontant.setVisibility(View.VISIBLE);
                    holder.setImageBitmapRound(R.id.businessLogo, detailsBean.getIcon(), 5);
                    holder.setText(R.id.name, detailsBean.getFeature());
                    holder.setText(R.id.presentPrice, detailsBean.getList().get(i).getMoneyStr());
                    holder.setText(R.id.orderStatus, mData.getStateStr());
                }
                    holder.setImageBitmapRound(R.id.picture, detailsBean.getList().get(i).getImg(), 5);
                    holder.setText(R.id.type, detailsBean.getList().get(i).getTitle());
                    holder.setText(R.id.text,"规格"+detailsBean.getList().get(i).getLabel());
                    detailsBean.getList().get(i).getPriceStr((TextView) holder.getView(R.id.originalPrice));
                    holder.setText(R.id.number, detailsBean.getList().get(i).getNumStr());
            }
            //进入分类
            holder.setOnClickListener(R.id.name, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassifyActivity.startUI(StoreOrderActivity.getStoreOrderActivity());
                }
            });
        }
//            LinearLayout parent=holder.getView(R.id.contants);
//            holder.setImageBitmap(R.id.businessLogo,detailsBean.getIcon());
//            holder.setText(R.id.orderStatus,mData.getStateStr());
//            holder.setText(R.id.name,detailsBean.getFeature());
//            for (int i = 0; i < detailsBean.getList().size(); i++) {
//                View addView= LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.store_item_order_items,null);
//                picture = addView.findViewById(R.id.picture);
//                type = addView.findViewById(R.id.type);
//                text = addView.findViewById(R.id.text);
//                presentPrice = addView.findViewById(R.id.presentPrice);
//                orginalPrice = addView.findViewById(R.id.originalPrice);
//                number = addView.findViewById(R.id.number);
//                GlideUtils.show(picture,detailsBean.getList().get(i).getImg(),new GlideOptions.Builder().bulider());
//                type.setText(detailsBean.getList().get(i).getTitle());
//                text.setText("规格"+detailsBean.getList().get(i).getLabel());
//                presentPrice.setText(detailsBean.getList().get(i).getMoneyStr());
//                detailsBean.getList().get(i).getPriceStr(orginalPrice);
//                number.setText(detailsBean.getList().get(i).getNumStr());
//                parent.addView(addView);
//            }
//        }
    }
}
