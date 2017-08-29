package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.abslistview.CommonAdapter;

import java.util.ArrayList;


/**
 * 说明：选择菜单
 * 作者　　: 杨阳
 * 创建时间: 2016/11/3 14:11
 */

public class PopupSelectMenu extends PopupWindow {
    private OnClickCallback onClickCallback = null;
    ArrayList<PullDownData> datas;
    private Context context;
    private GridView list;


    public PopupSelectMenu(Context context, ArrayList<PullDownData> content) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.popup_select, null);

        list = (GridView) view.findViewById(R.id.list);
        this.context = context;
        this.datas = content;

        //的一些设置
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


    private void addItem() {
        LayoutAnimationController ll = new LayoutAnimationController(AnimationUtils.loadAnimation(context, R.anim.poup_select_bank));
        ll.setOrder(LayoutAnimationController.ORDER_NORMAL);
        list.setLayoutAnimation(ll);
        list.startLayoutAnimation();
        list.setAdapter(new CommonAdapter<PullDownData>(context, R.layout.popup, datas) {
            @Override
            public void convert(ViewHolder holder, PullDownData pullDownData) {
                holder.setText(R.id.source, pullDownData.getTitle());


            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            int w1 = outMetrics.widthPixels;
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

    /**
     * 当设置监听时必须实现接口,通过接口参数把一些信息传递给调用者
     *
     * @param callback
     */
    public void setOnClickCallback(OnClickCallback callback) {
        onClickCallback = callback;
    }


    /**
     * 选择内容的实体类
     */
    public static class PullDownData extends Object {
        private int id;
        private String title;
        private boolean isSelect = false;

        public PullDownData(int id, String title) {
            super();
            this.id = id;
            this.title = title;
        }


        public boolean isSelect() {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect) {
            this.isSelect = isSelect;
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
