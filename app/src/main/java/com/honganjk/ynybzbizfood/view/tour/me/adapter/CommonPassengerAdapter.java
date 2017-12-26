package com.honganjk.ynybzbizfood.view.tour.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.CommonPassengerBean;
import com.honganjk.ynybzbizfood.utils.ui.ScreenInfoUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.tour.me.activity.CommonPassengerActivity;

import java.util.List;

/**
 *
 * Created by Administrator on 2017-12-06.
 */

public class CommonPassengerAdapter extends RecyclerView.Adapter{
    CommonPassengerActivity mActivity;
    List<CommonPassengerBean> commonPassengerBeanList;

    public CommonPassengerAdapter(CommonPassengerActivity activity,List<CommonPassengerBean> list){
        mActivity = activity;
        commonPassengerBeanList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_passenger,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return commonPassengerBeanList.size();
    }

    public void changeUI(List<CommonPassengerBean> list){
        commonPassengerBeanList = list;
        notifyDataSetChanged();
    }
    class MyHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView identityAndIdcard;
        public MyHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            identityAndIdcard = itemView.findViewById(R.id.identity_and_idcard);
        }

        public void bindView(int position){
            View view = itemView.findViewById(R.id.relativeLayout);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = new ScreenInfoUtils().getWidth();
            view.setLayoutParams(params);
            final CommonPassengerBean commonPassengerBean = commonPassengerBeanList.get(position);
            tvName.setText(commonPassengerBeanList.get(position).getName());
            final int mType = commonPassengerBeanList.get(position).getType();
            String identity = "成人";
            if (mType == 1) {
                identity = "儿童";
            }
            if (mType == 2) {
                identity = "军人";
            }
            identityAndIdcard.setText( identity + "  " + commonPassengerBean.getSn());
            itemView.findViewById(R.id.iv_reset).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //开启修改
                    ToastUtils.getToastShort("修改旅客");
                    //        EditAddressActivity.startForResultUi(CommonPassengerActivity.this, data);
   //                 EditPassengerActivity.startForResultUi(mActivity, BaseActivity.REQUEST_CODE, commonPassengerBean);
                }
            });
            itemView.findViewById(R.id.tv_dele).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                    ToastUtils.getToastShort("删除旅客");
                    //      presenter.deleAddress(dataBean.id);
                    mActivity.presenter.delePassenger(commonPassengerBean.getId());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //选择旅客
                    ToastUtils.getToastShort("选择旅客");
                }
            });
        }
    }
}
