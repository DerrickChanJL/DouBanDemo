package com.example.android.douban.fragment.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.douban.R;
import com.example.android.douban.adapter.DetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示账号模块的Fragment
 */

public class AccountFragment extends Fragment {


    private ViewPager mViewPager;

    private List<Fragment> mFragmentList;
    private Fragment mLoginFragment;
    private Fragment mRegisterFrament;
    private String[] mTitles = new String[]{"登录","注册"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        initData();
        initView(view);
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData(){

        mFragmentList = new ArrayList<>();
        mLoginFragment = new LoginFragment();
        mRegisterFrament = new RegisterFragment();

        mFragmentList.add(mLoginFragment);
        mFragmentList.add(mRegisterFrament);
    }
    /**
     * 初始化view
     */
    private void initView(View view){

        //ViewPageer
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new DetailAdapter(getFragmentManager(),mFragmentList,mTitles));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("登录"));
        tabLayout.addTab(tabLayout.newTab().setText("注册"));
        tabLayout.setupWithViewPager(mViewPager);

    }
}
