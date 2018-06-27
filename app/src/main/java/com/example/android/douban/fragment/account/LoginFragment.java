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
import com.example.android.douban.model.SectionModel;
import com.example.android.douban.model.UserBean;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.SharePreferencesUtil;
import com.example.android.douban.network.Transformer;

/**
 * Created by Derrick on 2018/6/21.
 */

public class LoginFragment extends Fragment {


    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mloginButton;

    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
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
        mloginButton = (Button) view.findViewById(R.id.login_button);

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUsernameET.getText().length() == 0 || mPasswordET.getText().length() == 0){
                    Toast.makeText(getContext(),"账号和密码不能为空",Toast.LENGTH_SHORT);
                }else {
                    String username = mUsernameET.getText().toString();
                    String password = mUsernameET.getText().toString();
                    RetrofitFactory.getInstance().API().login("login",username,password)
                            .compose(new Transformer<Response<UserBean>>())
                            .subscribe(new BaseObserver<Response<UserBean>>() {
                                @Override
                                protected void onSuccess(Response<UserBean> response) throws Exception {
                                        if(response.getCode().equals("success")){
                                            //用户数据保存到本地
                                            SharePreferencesUtil.getInstance(getContext()).saveUser("user",response.getData());
                                            getActivity().onBackPressed();
                                        }else {
                                            Toast.makeText(getContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();

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
