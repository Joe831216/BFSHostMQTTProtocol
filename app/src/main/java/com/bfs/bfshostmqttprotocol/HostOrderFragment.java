package com.bfs.bfshostmqttprotocol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * Created by Joe on 2017/11/24.
 */

public class HostOrderFragment extends Fragment {

    private View viewContent;
    private TabLayout tab_essence;
    private ViewPager vp_essence;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent =  inflater.inflate(R.layout.fragment_host_order, container, false);
        initContentView(viewContent);
        initData();

        return viewContent;
    }

    public void initContentView(View viewContent) {
        this.tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence);
        this.vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence);
    }

    public void initData() {
        // 獲取標籤資料
        String[] titles = getResources().getStringArray(R.array.host_order_tab);

        // 創建一個viewpager的adapter
        HostOrderFragmentAdapter adapter = new HostOrderFragmentAdapter(getFragmentManager(), Arrays.asList(titles));
        this.vp_essence.setAdapter(adapter);

        // 將TabLyout和ViewPager關聯起來
        this.tab_essence.setupWithViewPager(this.vp_essence);
    }
}
