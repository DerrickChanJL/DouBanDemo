package com.example.android.douban.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.android.douban.R;
import com.example.android.douban.fragment.FunFragment;
import com.example.android.douban.fragment.LeaderFragment;
import com.example.android.douban.fragment.MovieFragment;
import com.example.android.douban.fragment.PersonalFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{


    private FragmentManager mFragmentManager;
    //Fragment
    private Fragment mMovieFragment;
    private Fragment mFunFragment;
    private Fragment mLeaderFragment;
    private Fragment mPersonalFragment;

    private List<Fragment> mFragmentList;

    //RadioGroup
    private RadioGroup mRadioGroup;
    //Toolbar
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Test();
        initView();
    }

    private void Test(){

        //https://movie.douban.com/subject/26309788/
        new Thread(new AvatorImageTask("https://movie.douban.com/subject/26309788/")).start();
    }
    private class AvatorImageTask implements Runnable{

        private String url;
        public AvatorImageTask(String url){
            this.url = url;
        }
        @Override
        public void run() {
            //获得数据
            try {
                Document doc = Jsoup.connect(url).get();
                Elements div = doc.select("div.avatar");
                for (Element element : div){
                    String value = div.attr("style");
                    Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
                    Matcher matcher = pattern.matcher(value);
                    while(matcher.find()){
                        Log.i("net",matcher.group());
                    }
                    Log.i("net",value);
                }



            } catch (IOException e) {
                e.printStackTrace();
                Log.w("net",e);
            }

        }
    }

    /**
     * 初始化视图
     */
    private void initView(){

        //去得FragmentManger
        mFragmentManager = getSupportFragmentManager();
        mFragmentList = new ArrayList<>();
        mRadioGroup = (RadioGroup) findViewById(R.id.navigation_bottom);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //监听radioGroup的checkChangedListener
        mRadioGroup.setOnCheckedChangeListener(this);
        //设置默认选中第一个
        ((RadioButton)(mRadioGroup.getChildAt(0))).setChecked(true);
    }

    /**
     * 底部导航栏选中回调
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        switch (checkedId){
            case R.id.movie_radiobutton:

                if(mMovieFragment == null){
                    mMovieFragment = new MovieFragment();
                    mFragmentList.add(mMovieFragment);
                    if (mFragmentManager.findFragmentByTag(mMovieFragment.getClass().getSimpleName()) == null){

                        ft.add(R.id.fragment_container,mMovieFragment,mMovieFragment.getClass().getSimpleName());
                    }
                }
                //设置标题
                mToolbar.setTitle("豆瓣电影");
                //隐藏其他Fragment
                hideFragmennts(ft,mMovieFragment);
                break;

            case R.id.leaderboard_radiobutton:

                if(mLeaderFragment == null){
                    mLeaderFragment = new LeaderFragment();
                    mFragmentList.add(mLeaderFragment);
                    if (mFragmentManager.findFragmentByTag(mLeaderFragment.getClass().getSimpleName()) == null){

                        ft.add(R.id.fragment_container,mLeaderFragment,mLeaderFragment.getClass().getSimpleName());
                    }
                }
                mToolbar.setTitle("Top250");
                //隐藏其他Fragment
                hideFragmennts(ft,mLeaderFragment);
                break;

            case R.id.personal_radiobutton:
                Log.i("test","checkId " + checkedId);
                if(mPersonalFragment == null){
                    mPersonalFragment = new PersonalFragment();
                    mFragmentList.add(mPersonalFragment);
                    if (mFragmentManager.findFragmentByTag(mPersonalFragment.getClass().getSimpleName()) == null){

                        ft.add(R.id.fragment_container,mPersonalFragment,mPersonalFragment.getClass().getSimpleName());
                    }
                }
                mToolbar.setTitle("个人中心");
                //隐藏其他Fragment
                hideFragmennts(ft,mPersonalFragment);
                break;
        }
    }

    /**
     * 隐藏除参数外的其他的Fragment
     * @param ft
     * @param fragment
     */
    private void hideFragmennts(FragmentTransaction ft, Fragment fragment){
        for (Fragment fg : mFragmentList){
            if(fg != fragment){
                ft.hide(fg);
            }else {
                ft.show(fragment);
            }
        }

        ft.commit();
    }
}
