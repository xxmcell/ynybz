package com.honganjk.ynybzbizfood.view.store.shoppingcar.frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;

/**
 * Created by Administrator on 2017-09-30.
 */

public class ShoppingCarFrament extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.store_shoppingcarfragment,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static Fragment getInstance() {
        return new ShoppingCarFrament();
    }
}
