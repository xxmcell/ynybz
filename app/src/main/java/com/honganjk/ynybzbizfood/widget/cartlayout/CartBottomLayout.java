package com.honganjk.ynybzbizfood.widget.cartlayout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.QueRenXiaDanActivity;
import com.honganjk.ynybzbizfood.widget.BadgeViewNew;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 购物的底部栏
 */
public class CartBottomLayout extends LinearLayout {
    @BindView(R.id.tv_cart_num)
    ImageView tvCartNum;
    @BindView(R.id.tv_cart_price)
    TextView tvCartPrice;
    @BindView(R.id.tv_send_money)
    TextView tvSendMoney;
    @BindView(R.id.tv_send_open)
    TextView tvSendOpen;

    private cartBottomLayoutListener listener;

    private ArrayList<FoodData.DishsBeanX.DishsBean> datas = new ArrayList<>();
    private double openPrice, sendPrice, sum;
    private int total;
    private BadgeViewNew badgeView;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private View view;

    private BusinessDetailsData data;

    public interface cartBottomLayoutListener {
        void setDatas(ArrayList<FoodData.DishsBeanX.DishsBean> datas);

        void changeFoodNum(FoodData.DishsBeanX.DishsBean data);

        void clearDatas();

        void statusChanged(boolean enable);

        ArrayList getDatas();

        int getMenuId();

    }

    public void setListener(cartBottomLayoutListener listener) {
        this.listener = listener;
    }


    public CartBottomLayout(Context context) {
        super(context);
        init();
    }

    public CartBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CartBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        UiUtils.getInflaterView(getContext(), R.layout.widget_cart, this, true);
        ButterKnife.bind(this);
        badgeView = new BadgeViewNew(getContext(), tvCartNum);
    }

    public void setProperty(BusinessDetailsData data) {
        this.data = data;
        this.openPrice = data.getLowest();
        this.sendPrice = data.getFare();
        initView();
    }

    private void initView() {
        badgeView.setBadgePosition(BadgeViewNew.POSITION_TOP_RIGHT);
        badgeView.setTextSize(DimensUtils.px2dip(getContext(), getResources().getDimension(R.dimen.text_m)));
        tvSendOpen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum >= openPrice && datas.size() > 0) {
                    if (UserInfo.userData.isLogin()) {
                        QueRenXiaDanActivity.starUi(getContext(), datas, data, listener.getMenuId()
                        );
                    } else {
                        LoginActivity.startUI(getContext());
                        ToastUtils.getToastShort("请登录");
                    }

                }
            }
        });
        tvCartNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        view = UiUtils.getInflaterView(getContext(), R.layout.popup_cart);
        view.findViewById(R.id.iv_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList arrayList = new ArrayList();
                clearDatas();
                setData(arrayList);
                listener.clearDatas();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        adapter = new CartAdapter(getContext(), datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        updateView();
    }

    public void setData(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {
        this.datas = datas;
        updateView();
    }

    public ArrayList getData() {
        return datas;
    }

    //notice: true 自己操作更新    false外部操作更新
    public void changeFoodNum(FoodData.DishsBeanX.DishsBean data, boolean notice) {

        boolean isNew = true;

        for (FoodData.DishsBeanX.DishsBean item : datas) {

            if (data.getTimeType() == item.getTimeType()) {
                //判断相同菜品
                if (item.getId() == data.getId()) {
                    isNew = false;
                    if (data.getNum() == 0) {
                        adapter.notifyItemRemoved(datas.indexOf(item));
                        datas.remove(item);
                    } else {
                        item.setNum(data.getNum());
                        adapter.notifyItemChanged(datas.indexOf(item), item);
                    }
                    break;
                }
            }
        }
        if (isNew) {
            datas.add(data);
        }
        updateView();
        if (notice) {
            listener.changeFoodNum(data);
        }


    }

    public void clearDatas() {
        ArrayList<FoodData.DishsBeanX.DishsBean> copyDatas = new ArrayList<>();
        copyDatas.addAll(datas);
        for (FoodData.DishsBeanX.DishsBean item : copyDatas) {
            item.setNum(0);
            changeFoodNum(item, true);
        }
        copyDatas = null;
    }


    private void updateView() {

        sum = 0;
        total = 0;
        for (FoodData.DishsBeanX.DishsBean data : datas) {
            sum += data.getPrice() * data.getNum();
            total += data.getNum();
        }
        if (sum >= openPrice && datas.size() > 0) {
            tvSendOpen.setText(getResources().getString((int) R.string.tv_cart_pay));
            tvSendOpen.setTextColor(getResources().getColor(R.color.white));
            tvSendOpen.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            tvSendOpen.setText(String.format(getResources().getString((int) R.string.tv_cart_open_send), openPrice));
            tvSendOpen.setTextColor(getResources().getColor(R.color.black_20));
            tvSendOpen.setBackground(null);
        }

        tvCartPrice.setText(String.format(getResources().getString((int) R.string.tv_price), StringUtils.dataFilter(sum,2)));
        tvSendMoney.setText(String.format(getResources().getString((int) R.string.tv_cart_send_cost), sendPrice));


        if (datas.size() == 0) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setText(total + "");
            badgeView.setVisibility(View.VISIBLE);
            badgeView.show();
        }
        listener.statusChanged(datas.size() == 0);
        //价格的回调
        if (mPriceListener != null) {
            mPriceListener.changeCallback(sum);
        }
    }

    private void showPopupWindow() {
        adapter = new CartAdapter(getContext(), datas);
        recyclerView.setAdapter(adapter);

        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(this, Gravity.BOTTOM, 0, ((int)(this.getHeight()*1.8)));






    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class CartAdapter extends CommonAdapter<FoodData.DishsBeanX.DishsBean> {
        public CartAdapter(Context context, List datas) {
            super(context, R.layout.item_cart, datas);
        }

        @Override
        public void convert(final ViewHolder holder, final FoodData.DishsBeanX.DishsBean data) {
            holder.setText(R.id.tv_name, data.getName());
            holder.setOnClickListener(R.id.iv_add, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.setNum(data.getNum() + 1);
                    setNum(data, holder);
                    changeFoodNum(data, true);
                }
            });
            holder.setOnClickListener(R.id.iv_reduce, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.setNum(data.getNum() - 1);
                    setNum(data, holder);
                    changeFoodNum(data, true);
                }
            });
            setNum(data, holder);
        }

        private void setNum(FoodData.DishsBeanX.DishsBean data, ViewHolder holder) {
            if (data.getNum() == 0) {
                holder.setVisible(R.id.iv_reduce, false);
                holder.setVisible(R.id.tv_num, false);
            } else {
                holder.setVisible(R.id.iv_reduce, true);
                holder.setVisible(R.id.tv_num, true);
                holder.setText(R.id.tv_num, data.getNum());
            }
            holder.setText(R.id.tv_price, String.format(getContext().getString((int) R.string.tv_price), data.getPrice() * data.getNum()));


        }
    }


    /**
     * 价格变动的监听
     */
    PriceListener mPriceListener;

    /**
     * 设置监听价格的改变
     *
     * @param listener
     */
    public void setPriceListener(PriceListener listener) {
        mPriceListener = listener;
    }

    public interface PriceListener {
        void changeCallback(double price);
    }


}
