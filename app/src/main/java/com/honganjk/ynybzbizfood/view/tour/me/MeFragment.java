package com.honganjk.ynybzbizfood.view.tour.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;

/**
 * Created by Administrator on 2017-11-07.
 */

public class MeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_tour_my,null);
        return view;
    }
}
