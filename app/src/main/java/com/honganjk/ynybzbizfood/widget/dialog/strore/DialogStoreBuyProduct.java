package com.honganjk.ynybzbizfood.widget.dialog.strore;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsTypeData;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.view.store.home.activity.StoreSubscribeActivity;
import com.honganjk.ynybzbizfood.widget.CustomClassifySelectView;
import com.honganjk.ynybzbizfood.widget.NumberSelectRect;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.sdk.android.impl.KernelContext.context;

/**
 * 说明:商城立即购买产品
 * 作者： 杨阳; 创建于：  2017-07-10  11:32
 */
public class DialogStoreBuyProduct extends Dialog {
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.inventory)
    TextView inventory;
    @BindView(R.id.select)
    TextView select;
    @BindView(R.id.type)
    CustomClassifySelectView type;
    @BindView(R.id.selectNumber)
    NumberSelectRect selectNumber;
    @BindView(R.id.picture)
    ImageView picture;
    List<ProductDetailsTypeData> mDatas;
    ProductDetailsTypeData mData;
    int number = 1;
    int mId;
    String mTitle;

    public DialogStoreBuyProduct(@NonNull Context context, List<ProductDetailsTypeData> data, int id, String title) {
        this(context, R.style.Dialog_select, data, id, title);
    }

    public DialogStoreBuyProduct(@NonNull Context context, @StyleRes int themeResId, List<ProductDetailsTypeData> data, int id, String title) {
        super(context, themeResId);
        mDatas = data;
        mId = id;
        mTitle = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_strore_buy_product);
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
        if (mDatas != null) {
            String[] titles = new String[mDatas.size()];
            for (int i = 0; i < mDatas.size(); i++) {
                titles[i] = mDatas.get(i).getLabel();
            }
            type.setTitle(titles);
            checkType(mDatas.get(0).getLabel());
            mData = mDatas.get(0);
            type.setOnClickCallback(new CustomClassifySelectView.OnClickCallback() {
                @Override
                public void onClick(String content) {
                    checkType(content);
                }
            });
        }

        selectNumber.setOnClickCallback(new NumberSelectRect.OnClickCallback() {
            @Override
            public boolean onClick(boolean addSubtract,int content) {
                number = content;
                return false;
            }
        });

        AnimUtils.pressAnimationListener(findViewById(R.id.close));
        AnimUtils.pressAnimationListener(findViewById(R.id.commie));
    }

    private void checkType(String content) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getLabel().equals(content)) {
                mData = mDatas.get(i);
                GlideUtils.show(picture, mDatas.get(i).getImg());
                price.setText(mDatas.get(i).getPriceStr());
                select.setText(mDatas.get(i).getLabel());
                inventory.setText(mDatas.get(i).getStock());
            }

        }
    }

    @OnClick({R.id.close, R.id.commie})
    public void onViewClicked(final View view) {

        AnimUtils.pressAnimationListener(view, new AnimUtils.OnClickListenerCallback() {
            @Override
            public void clickCallback(boolean isOnClick) {

                switch (view.getId()) {
                    case R.id.close:
                        dismiss();
                        break;
                    case R.id.commie:
                        mData.setNumber(number);
                        mData.setId(mId);
                        mData.setTitle(mTitle);
                        if (mAddShoppingCarListener != null) {
                            mAddShoppingCarListener.addShoppingCar(mData);
                            dismiss();
                            return;
                        }

                        // TODO: 2017-09-08  下单
                        StoreSubscribeActivity.starUi(getContext(), mData);
                        break;
                }
            }
        });
    }


    AddShoppingCarListener mAddShoppingCarListener;

    public void setAddShoppingCarListener(AddShoppingCarListener addShoppingCarListener) {
        mAddShoppingCarListener = addShoppingCarListener;
    }

    public interface AddShoppingCarListener {
        void addShoppingCar(ProductDetailsTypeData mData);
    }

}
