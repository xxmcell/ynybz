package com.honganjk.ynybzbizfood.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;

import java.util.List;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;


/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/21 11:50
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：viewPage adapter
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] title) {
        super(fm);
        if (fragments.size() < 1 || title.length < 1 || fragments.size() != title.length) {
            ToastUtils.showShort(myApp, "数据不对参数");
            return;
        }
        this.title = title;
        this.fragments = fragments;
    }


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null && title.length != 0) {
            return title[position];
        } else {
            return fragments.get(position).getTag().toLowerCase();
        }
    }
}