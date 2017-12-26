package com.honganjk.ynybzbizfood.widget.dialog.tour;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alibaba.sdk.android.impl.KernelContext.context;

/**
 * 说明:旅游费用明细
 * 作者： 杨阳; 创建于：  2017-07-10  11:32
 */
public class DialogTourPriceDetail extends Dialog {

    int number = 1;
    int mAllPrice;
    int mSafePrice;
    int mDidcountPrice;
    int mOrderPrice;
    @BindView(R.id.all_price)
    TextView allPrice;
    @BindView(R.id.safe_price)
    TextView safePrice;
    @BindView(R.id.discount_price)
    TextView discountPrice;
    @BindView(R.id.order_price)
    TextView orderPrice;

    public DialogTourPriceDetail(@NonNull Context context, int allPrice, int safePrice, int discountPrice, int orderPrice) {
        this(context, R.style.Dialog_select, allPrice, safePrice, discountPrice, orderPrice);
    }

    public DialogTourPriceDetail(@NonNull Context context, @StyleRes int themeResId, int allPrice, int safePrice, int discountPrice, int orderPrice) {
        super(context, themeResId);
        mAllPrice = allPrice;
        mSafePrice = safePrice;
        mDidcountPrice = discountPrice;
        mOrderPrice = orderPrice;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tour_price_detail);
        ButterKnife.bind(this, getWindow().getDecorView().findViewById(R.id.dialog_select_root));

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        lp.width = (metric.widthPixels); //设置宽度

        lp.height = (ActionBar.LayoutParams.WRAP_CONTENT); //设置宽度
        getWindow().setAttributes(lp);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;

        init();
    }

    private void init() {
        allPrice.setText("￥"+mAllPrice);
        safePrice.setText("￥"+mSafePrice);
        discountPrice.setText("￥"+mDidcountPrice);
        orderPrice.setText("￥"+mOrderPrice);
    }
}
