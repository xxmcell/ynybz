package com.honganjk.ynybzbizfood.view.tour.detail.fragment;

import android.webkit.WebView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;

/**
 * 费用及须知
 * Created by Administrator on 2017-11-24.
 */

public class InstructionsFragment extends BaseFragment {

    public InstructionsFragment() {
    }

    public static InstructionsFragment getInstance() {
        return new InstructionsFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.tour_detail_instructions;
    }


    @Override
    public void initView() {
        WebView webView = view.findViewById(R.id.instruction);
        webView.loadUrl(" file:///android_asset/Index.html ");
    }
}
