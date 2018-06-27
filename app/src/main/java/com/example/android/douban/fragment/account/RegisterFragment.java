package com.example.android.douban.fragment.account;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.douban.R;
import com.example.android.douban.model.Response;
import com.example.android.douban.model.UserBean;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.SharePreferencesUtil;
import com.example.android.douban.network.Transformer;

/**
 * Created by Derrick on 2018/6/21.
 */

public class RegisterFragment extends Fragment {

    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mRegisterButton;

    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initView(view);
        mHandler = new Handler();
        return view;
    }

    /**
     * 初始化view
     */
    private void initView(View view){

        mUsernameET = (EditText) view.findViewById(R.id.username_edit);
        mPasswordET = (EditText) view.findViewById(R.id.password_edit);
        mRegisterButton = (Button) view.findViewById(R.id.register_button);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUsernameET.getText().length() == 0 || mPasswordET.getText().length() == 0){
                    Toast.makeText(getContext(),"账号和密码不能为空",Toast.LENGTH_SHORT);
                }else {
                    String username = mUsernameET.getText().toString();
                    String password = mUsernameET.getText().toString();
                    RetrofitFactory.getInstance().API().register("register",username,password)
                            .compose(new Transformer<Response<UserBean>>())
                            .subscribe(new BaseObserver<Response<UserBean>>() {
                                @Override
                                protected void onSuccess(Response<UserBean> response) throws Exception {
                                    if(response.getCode().equals("success")){

                                        Toast.makeText(getContext(),"注册成功",Toast.LENGTH_SHORT).show();

                                    }else {
                                        Toast.makeText(getContext(),"注册失败",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onComplete() {
                                    d.dispose();
                                }

                                @Override
                                protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                    Toast.makeText(getContext(),"网络错误",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
