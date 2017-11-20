package com.honganjk.ynybzbizfood.view.tour.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-11-07.
 */

public class OrderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView tv=new TextView(getContext());
        tv.setText("订单");
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);
        return tv;
    }
}
