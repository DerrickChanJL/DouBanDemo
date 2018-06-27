package com.example.android.douban.fragment.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.douban.R;
import com.example.android.douban.SpaceItemDecoration;
import com.example.android.douban.database.CommentUtil;
import com.example.android.douban.model.Comment;
import com.example.android.douban.model.UserBean;
import com.example.android.douban.network.SharePreferencesUtil;

import java.util.List;

/**
 * Created by Derrick on 2018/6/20.
 */

public class CommentFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CommentAdapter mCommentAdapter;

    private static String mMovieId;
    //评论集合
    private List<Comment> mCommentList;

    public static CommentFragment newInstance(String movieId){
        mMovieId = movieId;
        return new CommentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment,container,false);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * 更新评论
     */
    private void updateUI(){
        initData();
        if(mCommentAdapter == null){
            mCommentAdapter = new CommentAdapter();
        }
        mCommentAdapter.notifyDataSetChanged();
    }
    /**
     * 初始化视图
     */
    private void initView(View view){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentAdapter = new CommentAdapter();
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mCommentAdapter);
    }

    /**
     * 重数据库获取数据
     */
    private void initData(){
        Log.i("test","movieId " + mMovieId);
        mCommentList = CommentUtil.getCommentUtil(getContext()).getComments(mMovieId);
    }

    /**
     * 评论适配器
     */
    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item,parent,false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            Comment comment = mCommentList.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return mCommentList.size();
        }

        public class CommentViewHolder extends RecyclerView.ViewHolder{

            private TextView mUsernameText;
            private TextView mCommentText;
            private Button mDeleteButton;
            private Comment mComment;

            public CommentViewHolder(View itemView) {
                super(itemView);

                mUsernameText = itemView.findViewById(R.id.username_text_view);
                mCommentText = itemView.findViewById(R.id.comment_text_view);
                mDeleteButton = itemView.findViewById(R.id.delete_button);

            }

            public void bind(final Comment comment){

                mComment = comment;
                UserBean user = SharePreferencesUtil.getInstance(getContext()).getUser("user");
                if(user != null){
                    if(comment.getUserId() == user.getId()){
                        mDeleteButton.setVisibility(View.VISIBLE);
                    }else {
                        mDeleteButton.setVisibility(View.GONE);
                    }
                }else {
                    mDeleteButton.setVisibility(View.GONE);
                }

                mDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("test","id "+ comment.getId());
                        CommentUtil.getCommentUtil(getContext()).deleteComment(comment.getId());
                        updateUI();
                        Log.i("test","delete");
                    }
                });

                //设置数据
                mUsernameText.setText(comment.getUsername());
                mCommentText.setText(comment.getContent());


            }
        }
    }
}
