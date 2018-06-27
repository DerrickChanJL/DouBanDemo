package com.example.android.douban.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.douban.R;

/**
 * 公共Activity,主要用来显示Fragment页面
 *
 */
public class CommonActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private static Fragment mFragment;
    //Toolbar的标题
    private static String mTitle;

    /**
     * 传递数据的intent
     * @param packContext
     * @param title
     * @param fragment
     * @return
     */
    public static Intent newIntent(Context packContext, String title, Fragment fragment){

        Intent intent = new Intent(packContext,CommonActivity.class);
        mFragment = fragment;
        mTitle = title;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(mFragment != null)
        {
            fragmentManager.beginTransaction().add(R.id.fragment_container,mFragment).commit();
        }

        //设置toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
