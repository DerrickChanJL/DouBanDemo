package com.example.android.douban.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.douban.R;
import com.example.android.douban.adapter.DetailAdapter;
import com.example.android.douban.fragment.WriteCommentFragment;
import com.example.android.douban.fragment.account.LoginFragment;
import com.example.android.douban.fragment.viewpager.CastFragment;
import com.example.android.douban.fragment.viewpager.CommentFragment;
import com.example.android.douban.fragment.viewpager.IntroductionFragment;
import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.model.UserBean;
import com.example.android.douban.network.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 电影显示内容的Activity，负责显示电影内容和一些逻辑操作（比如显示图片信息、抓取简介，写评论等）
 */
public class DetailActivity extends AppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ViewPager mViewPager;
    private RatingBar mRatingBar;
    private TextView mRatingText;
    private TextView mCollectionCountText;
    private LinearLayout mWriteLinearLayout;

    //集合
    private List<Fragment> mFragmentList;
    private Fragment mCastFragment;
    private Fragment mIntroductionFragment;
    private Fragment mCommentFragment;
    private String[] mTitles = null;



    private static MovieReponse.Subjects mSubjects;

    /**
     * 数据传递初始化
     * @param packContext
     * @param subjects
     * @return
     */
    public static Intent newIntent(Context packContext,MovieReponse.Subjects subjects){

        Intent intent = new Intent(packContext,DetailActivity.class);
        mSubjects = subjects;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //判断是否保存上一次实例化页面的数据
        if(savedInstanceState != null){
            mSubjects = savedInstanceState.getParcelable("sub");
        }
        initData();
        initView();
    }


    /**
     * 暂存数据，因为重新实例化页面的时候数据会清空
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("sub",mSubjects);

    }

    /**
     * 初始化数据
     */
    private void initData(){

        mFragmentList = new ArrayList<>();
        mTitles = new String[]{"简介",
                "主演/导演","评论"};

        //初始化viewpager中的fragment
        mCastFragment = CastFragment.newInstance(mSubjects.getCasts(),mSubjects.getDirectors());
        mIntroductionFragment = IntroductionFragment.newInstance(mSubjects.getAlt());
        mCommentFragment =  CommentFragment.newInstance(mSubjects.getId());
        //添加到fragment集合中
        mFragmentList.add(mIntroductionFragment);
        mFragmentList.add(mCastFragment);
        mFragmentList.add(mCommentFragment);
    }
    /**
     * 初始化view
     */
    private void initView( ){

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        mRatingBar = (RatingBar) findViewById(R.id.main_ratingBar);
        mRatingText = (TextView) findViewById(R.id.rating_text);
        mCollectionCountText = (TextView) findViewById(R.id.collection_count_text);
        mWriteLinearLayout = (LinearLayout) findViewById(R.id.write_comment_layout);

        //设置数据
        mRatingBar.setRating((float) mSubjects.getRating().getAverage()/2);
        mCollectionCountText.setText(mSubjects.getCollect_count()+"人评论");
        mRatingText.setText(mSubjects.getRating().getAverage()+"");

        //监听评论按钮
        mWriteLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录了
                UserBean userBean = SharePreferencesUtil.getInstance(DetailActivity.this).getUser("user");
                if(userBean == null){
                    //跳转到登陆
                    Intent intent = CommonActivity.newIntent(DetailActivity.this,"登陆",new LoginFragment());
                    startActivity(intent);
                }else {
                    //跳转到写评论
                    Intent intent = CommonActivity.newIntent(DetailActivity.this,"点评",WriteCommentFragment.newInstance(mSubjects.getId()));
                    startActivity(intent);
                }
            }
        });


        //设置图片
        Glide.with(this).load(mSubjects.getImages().getMedium()).into(imageView);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //设置标题
        mCollapsingToolbar.setTitle(mSubjects.getTitle());
        //ViewPageer
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new DetailAdapter(getSupportFragmentManager(),mFragmentList,mTitles));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("简介"));
        tabLayout.addTab(tabLayout.newTab().setText("主演/导演"));
        tabLayout.addTab(tabLayout.newTab().setText("评论"));
        tabLayout.setupWithViewPager(mViewPager);
    }


}
