package com.bfs.bfshostmqttprotocol;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HostMainActivity extends AppCompatActivity {

    private List<TabItem> mTabItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);
        initTabData();
        initHost();
    }

    // 初始化tab資料
    private void initTabData() {
        mTabItemList = new ArrayList<>();
        // 添加tab
        mTabItemList.add(new TabItem(R.drawable.main_bottom_home_normal, R.drawable.main_bottom_home_press, R.string.host_menu_text, HostMenuFragment.class));
        mTabItemList.add((new TabItem(R.drawable.main_bottom_order_normal, R.drawable.main_bottom_order_press, R.string.host_order_text, HostOrderFragment.class)));
    }

    // 初始化選項卡視圖
    private void initHost() {
        // 實例化實例化FragmentTabHost對象
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        // 去掉分割線
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mTabItemList.size(); i++) {
            TabItem tabItem = mTabItemList.get(i);
            // 實例化一個TabSpec, 設置tab的名稱和視圖
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), null);

            // 給tab按鈕設置背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.main_bottom_bg));

            // 默認選中第一個tab
            if (i == 0) {
                tabItem.setChecked(true);
            }
        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // 重置tab樣式
                for (int i = 0; i < mTabItemList.size(); i++) {
                    TabItem tabItem = mTabItemList.get(i);
                    if (tabId.equals(tabItem.getTitleString())) {
                        tabItem.setChecked(true);
                    } else {
                        tabItem.setChecked(false);
                    }
                }
            }
        });
 }

    class TabItem {
        // 正常情況下顯示的圖片
        private int imageNormal;
        // 選中情況下顯示的圖片
        private int imagePress;
        // tab的名字
        private int title;
        private String titleString;

        // tab對應的fragment
        public Class<? extends Fragment> fragmentClass;

        public View view;
        public ImageView imageView;
        public TextView textView;

        public TabItem(int imageNormal, int imagePress, int title, Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass = fragmentClass;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public int getImageNormal() {
            return imageNormal;
        }

        public int getImagePress() {
            return imagePress;
        }

        public  int getTitle() {
            return title;
        }

        public String getTitleString() {
            if (title == 0) {
                return "";
            }
            if (TextUtils.isEmpty(titleString)) {
                titleString = getString(title);
            }
            return titleString;
        }

        public View getView() {
            if (this.view == null) {
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
                this.imageView = (ImageView) this.view.findViewById(R.id.tab_iv_image);
                this.textView = (TextView) this.view.findViewById(R.id.tab_tv_text);
                if (this.title == 0) {
                    this.textView.setVisibility(View.GONE);
                } else {
                    this.textView.setVisibility(View.VISIBLE);
                    this.textView.setText(getTitleString());
                }
                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }

        // 切換tab的方法
        public void setChecked(boolean isChecked) {
            if (imageView != null) {
                if (isChecked) {
                    imageView.setImageResource(imagePress);
                } else {
                    imageView.setImageResource(imageNormal);
                }
            }
            if (textView != null && title != 0) {
                if (isChecked) {
                    textView.setTextColor(getResources().getColor(R.color.main_botton_text_select));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_normal));
                }
            }
        }
    }
}

