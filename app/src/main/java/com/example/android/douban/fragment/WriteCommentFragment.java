package com.example.android.douban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.android.douban.R;
import com.example.android.douban.activity.CommonActivity;
import com.example.android.douban.database.CommentUtil;
import com.example.android.douban.fragment.account.LoginFragment;
import com.example.android.douban.model.Comment;
import com.example.android.douban.model.UserBean;
import com.example.android.douban.network.SharePreferencesUtil;

/**
 * Created by Derrick on 2018/6/22.
 */

public class WriteCommentFragment extends Fragment {

    //控件
    private RatingBar mRatingBar;
    private EditText mCommentET;
    private Button mOkButton;

    //电影id
    private static String mMovieId;

    public static WriteCommentFragment newInstance(String movieId){

        mMovieId = movieId;
        return new WriteCommentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_comment,container,false);

        initView(view);
        return view;
    }

    /**
     * 初始化视图
     */
    private void initView(View view){

        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mCommentET = (EditText) view.findViewById(R.id.comment_text_view);
        mOkButton = (Button) view.findViewById(R.id.ok_button);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating = mRatingBar.getRating();
                String comment = mCommentET.getText().toString();

                if(rating == 0){

                    //显示提示
                    Toast.makeText(getContext(),"请评星!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(comment)){
                    //显示提示
                    Toast.makeText(getContext(),"请输入评论!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Comment model = new Comment();
                //获取当前用户信息
                UserBean userBean = SharePreferencesUtil.getInstance(getContext()).getUser("user");
                //判断是否登录
                if(userBean == null){
                    //跳转到登陆页面
                    Intent intent = CommonActivity.newIntent(getContext(),"个人中心",new LoginFragment());
                    startActivity(intent);
                }

                model.setUserId(userBean.getId());
                model.setUsername(userBean.getUsername());
                model.setMovieId(mMovieId);
                model.setContent(comment);

                //保存到数据库
                CommentUtil.getCommentUtil(getContext()).addComment(model);
                getActivity().onBackPressed();
            }
        });
    }
}
