package com.example.tab;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutMainActivity extends AppCompatActivity {
    //Tab标题
    private String[] title = new String[]{"自动", "手动", "状态", "设置","系统"};
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabLayout.Tab tabAtOne;
    private TabLayout.Tab tabAttwo;
    private TabLayout.Tab tabAtthree;
    private TabLayout.Tab tabAtfour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_content_view);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_view);

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        //将TabLayout与ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {

    }



    //自定义适配器
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new SecondFragment();//娱乐
            } else if (position == 2) {
                return new ThirtlyFragment();//游戏
            } else if (position == 3) {
                return new FourthlyFragment();//我的
            }else if (position==4){
                return new FifthlyFragment();
            }
            return new FristFragment();//首页
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }



}

