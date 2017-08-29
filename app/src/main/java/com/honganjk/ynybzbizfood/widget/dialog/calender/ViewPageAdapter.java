package com.honganjk.ynybzbizfood.widget.dialog.calender;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by admin on 2017/4/12.
 */

public class ViewPageAdapter extends PagerAdapter {
    ArrayList<View> mView;

    public ViewPageAdapter(ArrayList<View> arrayList) {
        mView = arrayList;
    }

    @Override
    public int getCount() {
        return mView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView(mView.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mView.get(position));
        return mView.get(position);

    }
}
