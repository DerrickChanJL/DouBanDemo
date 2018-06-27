package com.example.android.douban.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.douban.R;
import com.example.android.douban.adapter.MovieAdapter;
import com.example.android.douban.adapter.SectionAdapter;
import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.model.SectionModel;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.Transformer;

import java.util.List;

/**
 * Created by Derrick on 2018/6/21.
 */

public class TopFragment extends Fragment {


    private static int mCount;
    private static int mStart;

    private RecyclerView mRecyclerView;
    private SectionAdapter mSectionAdapter;
    private List<MovieReponse.Subjects> mSubjectsList;
    private MovieAdapter mMovieAdapter;

    public static TopFragment newInstance(int start,int count){
        mStart = start;
        mCount = count;
        return new TopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leader,container,false);
        initData();
        initView(view);
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData(){

        if(mSubjectsList == null){

            RetrofitFactory.getInstance().API().getMovies(mStart,mCount)
                    .compose(new Transformer<MovieReponse>())
                    .subscribe(new BaseObserver<MovieReponse>() {
                        @Override
                        protected void onSuccess(MovieReponse movieReponse) throws Exception {
                            mSubjectsList = movieReponse.getSubjects();
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

    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        View empty = LayoutInflater.from(getContext()).inflate(R.layout.empty_view,null);
        mMovieAdapter = new MovieAdapter(R.layout.movie_item,mSubjectsList,empty);


    }

}
