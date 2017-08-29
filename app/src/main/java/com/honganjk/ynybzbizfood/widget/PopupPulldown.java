package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.PopupWindow;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;


/**
 * 说明：下拉选择
 * 作者　　: 杨阳
 * 创建时间: 2016/11/22 14:38
 */

public class PopupPulldown extends PopupWindow {
    private OnClickCallback onClickCallback = null;
    ArrayList<PullDownData> datas;
    private Context context;
    private RecyclerView list;
    CommonAdapter<PullDownData> adapter;


    public PopupPulldown(Context context, ArrayList<PullDownData> content) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.popup_pulldown, null);

        list = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.context = context;
        this.datas = content;


        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        // 刷新状态
        this.update();
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        this.setOutsideTouchable(true);

        addItem();
    }


    public void setMaxHeight(int count) {
        setHeight(DimensUtils.dip2px(myApp, count));
    }

    private void addItem() {
        //设置布局动画
        LayoutAnimationController ll = new LayoutAnimationController(AnimationUtils.loadAnimation(context, R.anim.poup_select_bank));
        ll.setOrder(LayoutAnimationController.ORDER_NORMAL);
        list.setLayoutAnimation(ll);
        list.startLayoutAnimation();
        //添加适配器
        list.setLayoutManager(new LinearLayoutManager(context));
        list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).sizeResId(R.dimen.dp_1).colorResId(R.color.white).build());
        list.setAdapter(adapter = new CommonAdapter<PullDownData>(context, R.layout.popup_pulldown_item, datas) {
            @Override
            public void convert(ViewHolder holder, PullDownData pullDownData) {

                holder.setText(R.id.source, pullDownData.getTitle());


            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                if (onClickCallback != null) {
                    onClickCallback.onClick(datas.get(position).getId(), datas.get(position).getTitle());
                    dismiss();
                } else {
                    dismiss();
                }
            }
        });


    }


    /**
     * 对话框的显示位置
     *
     * @param parent 父控件
     */
    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            //屏幕的宽度
            int w1 = parent.getWidth();
            int w2 = parent.getWidth();
            float res = w1 > w2 ? -w1 / 2.0f + w2 / 2.0f : w2 / 2.0f - w1 / 2.0f;
            setWidth(w1);
            this.showAsDropDown(parent, Math.round(res), 0);

        } else {
            this.dismiss();
        }

    }


    /**
     * 接口
     */
    public interface OnClickCallback {
        void onClick(int id, String content);
    }


    public void setOnClickCallback(OnClickCallback callback) {
        onClickCallback = callback;
    }


    /**
     * 选择内容的实体类
     */
    public static class PullDownData {
        private int id;
        private String title;

        public PullDownData(int id, String title) {
            super();
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
