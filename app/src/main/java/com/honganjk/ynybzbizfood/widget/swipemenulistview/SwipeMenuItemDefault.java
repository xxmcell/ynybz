package com.honganjk.ynybzbizfood.widget.swipemenulistview;

import android.content.Context;
import android.graphics.Color;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;


/**
 * Created by Administrator on 2016/5/23.
 */
public class SwipeMenuItemDefault extends SwipeMenuItem {
    public SwipeMenuItemDefault(Context context) {
        super(context);

    }

    public SwipeMenuItemDefault initDelete() {
        setBackground(R.color.red);
        // set item width
        setWidth(DimensUtils.dip2px(mContext, 60));
        // set item title
        setTitle("删除");
        // set item title fontsize
        setTitleSize(16);
        // set item title font color
        setTitleColor(Color.WHITE);
        setFlag(2);
        return this;
    }

    public SwipeMenuItemDefault initUpdata() {
        setBackground(R.color.main_color);
        // set item width
        setWidth(DimensUtils.dip2px(mContext, 60));
        // set item title
        setTitle("修改");
        // set item title fontsize
        setTitleSize(16);
        // set item title font color
        setTitleColor(Color.WHITE);
        setFlag(1);
        return this;
    }

}
