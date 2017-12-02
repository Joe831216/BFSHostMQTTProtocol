package com.bfs.bfshostmqttprotocol;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Joe on 2017/11/24.
 */
// 繼承FragmentStatePagerAdapter
public class HostOrderFragmentAdapter extends FragmentStatePagerAdapter {

    public static final String TAB_TAG = "@dream@";

    private List<String> mTitles;
    Class<? extends Fragment> fragmentClass;


    public HostOrderFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        // 初始化Fragment資料
        HostOrderContentFragment fragment = new HostOrderContentFragment();

        //String[] title = mTitles.get(position).split(TAB_TAG);
        //fragment.setType(Integer.parseInt(title[0]));
        //fragment.setTitle(title[0]);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split(TAB_TAG)[0];
    }

}

