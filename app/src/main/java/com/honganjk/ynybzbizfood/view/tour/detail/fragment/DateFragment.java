package com.honganjk.ynybzbizfood.view.tour.detail.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.view.tour.detail.adapter.CalendarAdapter;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2017-12-08.
 */

public class DateFragment extends BaseFragment {

    private String mMonth;
    private ArrayList<TourDetailBean.DataBean.Formats> mFormatsList;
    private CalendarAdapter adapter;

    public DateFragment() {

    }
    public static final DateFragment newInstance(String month,ArrayList<TourDetailBean.DataBean.Formats> formatsList) {
        DateFragment fragment = new DateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("month", month);
        bundle.putSerializable("formatsList",formatsList);
        fragment.setArguments(bundle);

        return fragment ;
    }
    @Override
    public int getContentView() {
        return R.layout.fragment_date;
    }

    @Override
    public void initView() {
        mMonth = getArguments().getString("month");
        mFormatsList = (ArrayList<TourDetailBean.DataBean.Formats>) getArguments().getSerializable("formatsList");

        String[] split = mMonth.split("-");
        GridView gridView = view.findViewById(R.id.gridView);
        String currentTime = DateUtils.getCurrentTime().replaceAll("-", "");
        adapter = new CalendarAdapter(getContext(), Integer.parseInt(split[0]), Integer.parseInt(split[1]), currentTime,mFormatsList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(TourDetailBean.DataBean.Formats formats:mFormatsList){
                    if (TextUtils.equals(formats.getTime(),adapter.getDateByClickItem(i))){
                        Intent intent = new Intent();
                        intent.putExtra("Formats",formats);
                        getActivity().setResult(Activity.RESULT_OK,intent);
                        getActivity().finish();
                    }
                }
            }
        });
    }
}
