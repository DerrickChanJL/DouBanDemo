package com.example.android.douban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.douban.HSpaceItemDecoration;
import com.example.android.douban.R;
import com.example.android.douban.SpaceItemDecoration;
import com.example.android.douban.activity.DetailActivity;
import com.example.android.douban.adapter.MovieAdapter;
import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.model.SectionModel;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Derrick on 2018/6/19.
 */

public class LeaderFragment extends Fragment {

    private RecyclerView mHRecyclerView;
    private RecyclerView mVRecyclerView;
    private List<MovieReponse.Subjects> mSubjectsList;
    private List<ButtonModel> mButtonModelList;
    private MovieAdapter mMovieAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leader,container,false);
        mSubjectsList = new ArrayList<>();
        initData();
        initView(view);
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData(){
        mButtonModelList = new ArrayList<>();
        ButtonModel b1 = new ButtonModel();
        b1.setStart(0);
        b1.setText("top1-50");

        ButtonModel b2 = new ButtonModel();
        b2.setStart(50);
        b2.setText("top51-100");

        ButtonModel b3 = new ButtonModel();
        b3.setStart(100);
        b3.setText("top101-150");

        ButtonModel b4 = new ButtonModel();
        b4.setStart(150);
        b4.setText("top151-200");

        ButtonModel b5 = new ButtonModel();
        b5.setStart(200);
        b5.setText("top201-250");
        mButtonModelList.add(b1);
        mButtonModelList.add(b2);
        mButtonModelList.add(b3);
        mButtonModelList.add(b4);
        mButtonModelList.add(b5);



    }

    /**
     * 初始化View
     */
    private void initView(View view){

        mHRecyclerView = (RecyclerView) view.findViewById(R.id.horizontal_recyclerview);
        mVRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        //设置水平RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置横向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHRecyclerView.setLayoutManager(layoutManager);
        //设置间距
        mHRecyclerView.addItemDecoration(new HSpaceItemDecoration(30));
        mHRecyclerView.setAdapter(new HorizontalAdapter(mButtonModelList));

        //垂直RecyclerView
        mVRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mVRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        //空view
        View empty = LayoutInflater.from(getContext()).inflate(R.layout.empty_view,null);
        mMovieAdapter = new MovieAdapter(R.layout.movie_item,mSubjectsList,empty);
        //设置适配器
        mVRecyclerView.setAdapter(mMovieAdapter);

        //监听加载更多(绑定recycler)
        mMovieAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {


            }
        },mVRecyclerView);

        //监听item的点击事件
        mMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获得点击item的数据
                MovieReponse.Subjects subjects =  (MovieReponse.Subjects) adapter.getData().get(position);
                //跳转的detail页面
                Intent intent = DetailActivity.newIntent(getContext(),subjects);
                //设置动画
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), adapter.getViewByPosition(position,R.id.cover), "trans");
                ActivityCompat.startActivity(getContext(),intent,compat.toBundle());
            }
        });

        //初始化第一页数据
        initFirstData();



    }

    /**
     * 初始化第一页数据
     */
    private void initFirstData(){

        RetrofitFactory.getInstance().API().getMovies(0,50)
                .compose(new Transformer<MovieReponse>())
                .subscribe(new BaseObserver<MovieReponse>() {
                    @Override
                    protected void onSuccess(MovieReponse movieReponse) throws Exception {
                        mSubjectsList = movieReponse.getSubjects();
                        mMovieAdapter.setNewData(mSubjectsList);
                        //移动到第一位
                        mVRecyclerView.scrollToPosition(0);

                    }

                    @Override
                    public void onComplete() {
                        d.dispose();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * top按钮模型
     */
    public class ButtonModel{

        private int start;
        private String text;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    /**
     * 水平的RecyclerView的适配器
     */
    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HViewHolder>{

        private List<ButtonModel> mButtonModels;

        public HorizontalAdapter(List<ButtonModel> buttonModels){
            mButtonModels = buttonModels;
        }

        @Override
        public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.top_button_item,null);
            return new HViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HViewHolder holder, int position) {
            ButtonModel buttonModel = mButtonModels.get(position);
            holder.bind(buttonModel);
        }

        @Override
        public int getItemCount() {
            return mButtonModels.size();
        }


        public class HViewHolder extends RecyclerView.ViewHolder{

            private TextView mTopTextView;
            private View mView;
            public HViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                mTopTextView = (TextView) itemView.findViewById(R.id.top_text_view);
            }

            private void bind(final ButtonModel buttonModel){

                //设置标题
                mTopTextView.setText(buttonModel.getText());
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //清除数据
                        mSubjectsList.clear();
                        mMovieAdapter.notifyDataSetChanged();
                        RetrofitFactory.getInstance().API().getMovies(buttonModel.getStart(),50)
                                .compose(new Transformer<MovieReponse>())
                                .subscribe(new BaseObserver<MovieReponse>() {
                                    @Override
                                    protected void onSuccess(MovieReponse movieReponse) throws Exception {
                                        mSubjectsList = movieReponse.getSubjects();
                                        mMovieAdapter.setNewData(mSubjectsList);
                                        //移动到第一位
                                        mVRecyclerView.scrollToPosition(0);

                                    }

                                    @Override
                                    public void onComplete() {
                                        d.dispose();
                                    }

                                    @Override
                                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                                    }
                                });
                    }
                });
            }//bind_end
        }//viewHolder_end
    }
}
