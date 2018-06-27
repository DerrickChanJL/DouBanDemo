package com.example.android.douban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.douban.R;
import com.example.android.douban.activity.CommonActivity;
import com.example.android.douban.fragment.account.AccountFragment;
import com.example.android.douban.model.UserBean;
import com.example.android.douban.network.SharePreferencesUtil;

/**
 * Created by Derrick on 2018/6/19.
 * 个人中心fragment
 */

public class PersonalFragment extends Fragment {


    private Button mAccountButton;
//    private Button mCollectionButton;
    private Button mExitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenet_personal,container,false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData(){

        //获取本地用户信息
        UserBean user = SharePreferencesUtil.getInstance(getContext()).getUser("user");
        Log.i("test","is null " + user);
        //判断是否登录
        if(user == null){
            mAccountButton.setText("登录/注册");
            //设置按钮不可点击
            mAccountButton.setEnabled(true);
            mExitButton.setEnabled(false);
        }else {
            mAccountButton.setText(user.getUsername());
            //设置按钮可以点击
            mAccountButton.setEnabled(false);
            mExitButton.setEnabled(true);
        }
    }

    /**
     * 初始化视图
     */
    private void initView(View view){

        mAccountButton = (Button) view.findViewById(R.id.account_button);
//        mCollectionButton = (Button) view.findViewById(R.id.collection_button);
        mExitButton = (Button) view.findViewById(R.id.exit_button);

        //登陆注册按钮
        mAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CommonActivity.newIntent(getContext(),"个人中心",new AccountFragment());
                startActivity(intent);
            }
        });

//        mCollectionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取本地用户信息
                UserBean user = SharePreferencesUtil.getInstance(getContext()).getUser("user");
                if(user != null){
                    SharePreferencesUtil.getInstance(getContext()).clearUser("user");
                    //重置数据
                    initData();
                }

            }
        });
    }
}
