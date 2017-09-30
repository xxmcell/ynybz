package com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017-09-25.
 */

public interface OnItemsClickListener<T,Y,W> {
    void onItemsClick(ViewGroup parent, View view, T data1, Y data2, W data3, int position);
}
