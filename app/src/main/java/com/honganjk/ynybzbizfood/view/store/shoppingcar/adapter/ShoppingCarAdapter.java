package com.honganjk.ynybzbizfood.view.store.shoppingcar.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.SelectListenerView;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarManagerData;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.activity.ShoppingCarActivity;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;
import com.honganjk.ynybzbizfood.widget.NumberSelectRect;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShoppingCarAdapter extends CommonAdapter<ShoppingcarData.ObjsBean> {

    BaseActivity mContext;

    List<List<ShoppingcarData.ObjsBean>> datas;

    ShoppingcarManagerData ShoppingcarManagerData;
    private viewHolder vh;
    private AnimCheckBox checkBox;
    private ImageView picture;
    private TextView name;
    private TextView type;
    private TextView money;
    private TextView price;
    private NumberSelectRect numberSelectRect;
    SelectListenerView mSelectListenerView;


    public ShoppingCarAdapter(BaseActivity context, List<List<ShoppingcarData.ObjsBean>> datas) {
        super(context, R.layout.store_item_shoppingcar_title, datas,9);
        mContext = context;
        this.datas=datas;
    }



    public void setmSelectListenerView(SelectListenerView mSelectListenerView) {

    }

    @Override
    public void convert(ViewHolder holder, ShoppingcarData.ObjsBean objsBean) {

    }
    @Override
    public void convert(ViewHolder holder, final List<ShoppingcarData.ObjsBean> mdata) {

        LinearLayout parent=holder.getView(R.id.parent);
        parent.removeAllViews();

            holder.setText(R.id.name,mdata.get(0).getFeature());
            holder.setImageBitmap(R.id.picture,mdata.get(0).getIcon());
        //动态加载,个数是有循环次数决定
        for (int i = 0; i < mdata.size(); i++) {
            View addView= LayoutInflater.from(mContext).inflate(R.layout.store_item_shoppingcar,null);
            checkBox = addView.findViewById(R.id.checkBox);
            picture = addView.findViewById(R.id.picture);
            name = addView.findViewById(R.id.name);
            type = addView.findViewById(R.id.type);
            money = addView.findViewById(R.id.money);
            price = addView.findViewById(R.id.price);
            numberSelectRect = addView.findViewById(R.id.numberSelectRect);
            GlideUtils.show(picture,mdata.get(i).getImg(),new GlideOptions.Builder().bulider());
            type.setText(mdata.get(i).getType()+"");
            name.setText(mdata.get(i).getTitle()+"");
            money.setText(String.valueOf(mdata.get(i).getMoney()));
            price.setText(String.valueOf(mdata.get(i).getPrice()));
            price.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            numberSelectRect.setSelectNum(mdata.get(i).getNum());
            final AnimCheckBox animCheckBox =checkBox;
            final int finalI = i;
            Observable.timer(50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            animCheckBox.setChecked(mdata.get(finalI).getIsSelect());
                        }
                    });

            numberSelectRect.setOnClickCallback(new NumberSelectRect.OnClickCallback() {
                @Override
                public boolean onClick(boolean addSubtract, int content) {
                    mdata.get(finalI).setNum(content);
                    mSelectListenerView.setNumber(content);

                    if (mContext instanceof ShoppingCarActivity) {
                        ((ShoppingCarActivity) mContext).presenter.addAndSubtractNumber(addSubtract, mdata.get(finalI).getBid(), mdata.get(finalI).getType(), 1);
                    }
                    return false;
                }
            });
            animCheckBox.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
                @Override
                public void onChange(AnimCheckBox checkBox, boolean checked) {
                    // listBean.setIsSelect(checked, mSelectListenerView);
                }
            });
                    parent.addView(addView);
        }
    }

    private void getViewInstance(View addView, List<ShoppingcarData.ObjsBean> mdata, ViewHolder holder) {

    }

    public static class viewHolder{
        private AnimCheckBox checkBox;
        private ImageView picture;
        private TextView name;
        private TextView type;
        private TextView money;
        private TextView price;
        private NumberSelectRect numberSelectRect;
    }
}
