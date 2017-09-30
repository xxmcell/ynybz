package com.honganjk.ynybzbizfood.view.store.refund.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.honganjk.ynybzbizfood.R;

/**
 * Created by Administrator on 2017-09-19.
 */

public class RefundOrderFragment extends Fragment {
    private LinearLayout l;
    //    int mType;
//
//    public RefundOrderFragment(){}
//
//    @SuppressLint("ValidFragment")
//    public RefundOrderFragment(int type) {
//        this.mType=type;
//    }
//
//    @Override
//    public int getContentView() {
//        return R.layout.store_fragment_refundorder;
//    }
//    public static RefundOrderFragment getInstance(int type) {
//        return new RefundOrderFragment(type);
//    }
//    @Override
//    public void initView() {
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStat ){
        return inflater.inflate(R.layout.store_fragment_refundorder,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        l = getActivity().findViewById(R.id.linearLayout2);
    }
}
