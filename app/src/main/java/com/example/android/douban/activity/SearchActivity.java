package com.example.android.douban.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.douban.R;
import com.example.android.douban.adapter.MovieAdapter;
import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.model.SearchReponse;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.Transformer;
import com.example.android.douban.network.URLConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索页面Activity
 */
public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private MovieAdapter mMovieAdapter;

    //搜索的电影名称
    private String mMovieName;
    //变换后的数据
    private List<MovieReponse.Subjects> mSubjectsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //获得电影名,使用intent传递数据
        Intent intent = getIntent();
        mMovieName = intent.getStringExtra("movieName");
        initData();
        initView();
    }

    /**
     * 初始化搜索数据
     */
    private void initData(){
        //网络请求数据
        RetrofitFactory.getInstance().API().searchMovie(URLConfig.APIKEY,mMovieName)
                .compose(new Transformer<SearchReponse>())
                .subscribe(new BaseObserver<SearchReponse>() {
                    @Override
                    protected void onSuccess(SearchReponse searchReponse) throws Exception {
                        if(searchReponse.getRetcode().equals("100002")){
                            Log.i("test","no result");
                            TextView textView = mMovieAdapter.getEmptyView().findViewById(R.id.empty_text);
                            textView.setText("搜索失败，无相关电影");
                            mMovieAdapter.notifyDataSetChanged();
                        }
                        //变换数据
                        mSubjectsList = changeData(searchReponse.getData());
                        Log.i("net","search size " + mSubjectsList.size());
                        if(mSubjectsList.size() == 0){
                            TextView textView = mMovieAdapter.getEmptyView().findViewById(R.id.empty_text);
                            textView.setText("搜索失败，无相关电影");
                            mMovieAdapter.notifyDataSetChanged();
                        }
                        mMovieAdapter.setNewData(mSubjectsList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * 初始化view
     */
    private void initView(){

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置标题
        mToolbar.setTitle(mMovieName);
        //设置toolbar
        setSupportActionBar(mToolbar);
        //设置返回可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //监听返回按钮
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSubjectsList = new ArrayList<>();
        View empty = LayoutInflater.from(SearchActivity.this).inflate(R.layout.empty_view,null);
        mMovieAdapter = new MovieAdapter(R.layout.movie_item,mSubjectsList,empty);
        mRecyclerView.setAdapter(mMovieAdapter);
        //监听上拉
        mMovieAdapter.setOnLoadMoreListener(null,mRecyclerView);
        //监听点击事件
        mMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获得点击item的数据
                MovieReponse.Subjects subjects =  (MovieReponse.Subjects) adapter.getData().get(position);
                //跳转的detail页面
                Intent intent = DetailActivity.newIntent(SearchActivity.this,subjects);
                //设置动画
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this, adapter.getViewByPosition(position,R.id.cover), "trans");
                ActivityCompat.startActivity(SearchActivity.this,intent,compat.toBundle());
            }
        });


    }

    /**
     * 变换数据
     */
    private List<MovieReponse.Subjects> changeData(List<SearchReponse.Data> seachDatas){
        List<MovieReponse.Subjects> subjectses = new ArrayList<>();
        try {

            for (SearchReponse.Data data :seachDatas){

                MovieReponse.Subjects sub = new MovieReponse.Subjects();
                sub.setId(data.getId());
                sub.setTitle(data.getTitle());
                sub.setAlt(data.getUrl());
                sub.setCollect_count(data.getRatingCount());
                MovieReponse.Subjects.Images images = new MovieReponse.Subjects.Images();

                images.setSmall(data.getCoverUrl());
                images.setLarge(data.getCoverUrl());
                images.setMedium(data.getCoverUrl());
                sub.setImages(images);

                MovieReponse.Subjects.Rating rating = new MovieReponse.Subjects.Rating();
                rating.setAverage(data.getRating());
                sub.setRating(rating);

                sub.setYear(data.getPressDate());
                sub.setGenres(data.getGenres());

                List<MovieReponse.Subjects.Casts> castsList = new ArrayList<>();
                for (SearchReponse.Data.Actors actor : data.getActors()){
                    MovieReponse.Subjects.Casts casts = new MovieReponse.Subjects.Casts();
                    MovieReponse.Subjects.Casts.Avatars avatars = new MovieReponse.Subjects.Casts.Avatars();
                    avatars.setSmall("https://img3.doubanio.com/f/movie/30c6263b6db26d055cbbe73fe653e29014142ea3/pics/movie/movie_default_large.png");
                    casts.setAvatars(avatars);
                    casts.setName(actor.getName());
                    casts.setId(actor.getId());
                    castsList.add(casts);

                }

                List<MovieReponse.Subjects.Directors> directorses = new ArrayList<>();
                for (SearchReponse.Data.Directors director : data.getDirectors())
                {
                    MovieReponse.Subjects.Directors directors = new MovieReponse.Subjects.Directors();
                    MovieReponse.Subjects.Directors.AvatarsX avatars = new MovieReponse.Subjects.Directors.AvatarsX();
                    avatars.setSmall("https://img3.doubanio.com/f/movie/30c6263b6db26d055cbbe73fe653e29014142ea3/pics/movie/movie_default_large.png");
                    directors.setName(director.getName());
                    directors.setId(director.getId());
                    directors.setAvatars(avatars);
                    directorses.add(directors);
                }

                sub.setCasts(castsList);
                sub.setDirectors(directorses);


                subjectses.add(sub);

            }

        }catch (Exception e){
            Log.w("net" ,e.toString());
        }

        Log.i("test","change");
        return subjectses;

    }


}


