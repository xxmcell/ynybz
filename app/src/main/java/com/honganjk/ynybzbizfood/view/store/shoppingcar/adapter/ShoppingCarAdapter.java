package com.honganjk.ynybzbizfood.view.store.shoppingcar.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.SelectListenerView;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.activity.ShoppingCarActivity;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;
import com.honganjk.ynybzbizfood.widget.NumberSelectRect;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-06-29  15:41
 */
public class ShoppingCarAdapter extends CommonAdapter<ShoppingcarData.ObjsBean> {
    BaseActivity mContext;

    public ShoppingCarAdapter(BaseActivity context, List<ShoppingcarData.ObjsBean> datas) {
        super(context, R.layout.store_item_shoppingcar_title, datas);
        mContext = context;
    }

    SelectListenerView mSelectListenerView;

    public void setmSelectListenerView(SelectListenerView mSelectListenerView) {
        this.mSelectListenerView = mSelectListenerView;
    }


    @Override
    public void convert(ViewHolder holder, final ShoppingcarData.ObjsBean data) {
        holder.setImageBitmap(R.id.picture, data.getIcon());
        holder.setText(R.id.name, data.getFeature());
        holder.getView(R.id.recyclerView);
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        setData(recyclerView, data.getList());
    }

    private void setData(RecyclerView recyclerView, List<ShoppingcarData.ObjsBean.ListBean> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).sizeResId(R.dimen.dp_1).colorResId(R.color.gray_ee).build());
        recyclerView.setAdapter(new CommonAdapter<ShoppingcarData.ObjsBean.ListBean>(mContext, R.layout.store_item_shoppingcar, data) {
            @Override
            public void convert(ViewHolder holder, final ShoppingcarData.ObjsBean.ListBean listBean) {
                holder.setImageBitmap(R.id.picture, listBean.getImg());
                holder.setText(R.id.name, listBean.getTitle());
                holder.setText(R.id.type, listBean.getLable());
                holder.setText(R.id.money, listBean.getMoneyStr());
                listBean.getPriceStr((TextView) holder.getView(R.id.price));

                NumberSelectRect numberSelectRect = (NumberSelectRect) holder.getView(R.id.numberSelectRect);
                numberSelectRect.setSelectNum(listBean.getNum());

                final AnimCheckBox animCheckBox = holder.getView(R.id.checkBox);
                Observable.timer(50, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                animCheckBox.setChecked(listBean.getIsSelect());
//                                LogUtils.e(listBean.getIsSelect());
                            }
                        });
                numberSelectRect.setOnClickCallback(new NumberSelectRect.OnClickCallback() {
                    @Override
                    public boolean onClick(boolean addSubtract, int content) {
                        listBean.setNum(content);
                        mSelectListenerView.setNumber(content);

                        if (mContext instanceof ShoppingCarActivity) {
                            ((ShoppingCarActivity) mContext).presenter.addAndSubtractNumber(addSubtract, listBean.getBid(), listBean.getType(), 1);
                        }
                        return false;
                    }
                });
                animCheckBox.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onChange(AnimCheckBox checkBox, boolean checked) {
                        listBean.setIsSelect(checked, mSelectListenerView);
                    }
                });

            }


        });
    }


}
