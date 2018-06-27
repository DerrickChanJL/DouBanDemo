package com.example.android.douban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android.douban.R;
import com.example.android.douban.SpaceItemDecoration;
import com.example.android.douban.activity.CommonActivity;
import com.example.android.douban.activity.DetailActivity;
import com.example.android.douban.activity.SearchActivity;
import com.example.android.douban.activity.WebViewActivity;
import com.example.android.douban.adapter.MovieAdapter;
import com.example.android.douban.adapter.SectionAdapter;
import com.example.android.douban.model.AdBean;
import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.model.SectionModel;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.Transformer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/**
 * Created by Derrick on 2018/6/19.
 */

public class MovieFragment extends Fragment implements OnRefreshListener,OnLoadmoreListener{


    //控件
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private FloatingActionButton mSearchButton;
    private SectionAdapter mSectionAdapter;
    //数据集合
    private List<SectionModel> mSubjectsList = new ArrayList<>();
    //广告集合
    private List<AdBean> mAdBeanList;
    //随机数
    private int mCurrentCount =  new Random().nextInt(241);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie,container,false);
        initData();
        initView(view);
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData(){

    }


    /**
     * 初始化视图
     */
    private void initView(View view){

        //下拉刷新layout
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadmoreListener(this);
        //搜索按钮
        mSearchButton = (FloatingActionButton) view.findViewById(R.id.search_button);

        //监听搜索按钮
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("搜索电影")
                        .input("电影名", "肖申克的救赎", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    doSearch(input.toString());
                                }
                            }
                        }).show();
            }
        });

        //region RecyclerView模块
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        //空view
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view,null);

        mSectionAdapter = new SectionAdapter(R.layout.movie_item,R.layout.banner,mSubjectsList,emptyView,getContext());


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置间隔
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mSectionAdapter);

        //监听上拉加载
        mSectionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                RetrofitFactory.getInstance().API().getMovies(mCurrentCount,10)
                        .compose(new Transformer<MovieReponse>())
                        .subscribe(new BaseObserver<MovieReponse>() {
                            @Override
                            protected void onSuccess(MovieReponse movieReponse) throws Exception {
                                mSubjectsList = getSubjectsList(movieReponse.getSubjects(),false);
                                mSectionAdapter.addData(mSubjectsList);
                                mCurrentCount = new Random().nextInt(241);
                                mSectionAdapter.loadMoreComplete();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
            }
        },mRecyclerView);

        //item点击事件
        mSectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {

                //获得数据模型
                SectionModel sectionModel = (SectionModel) adapter.getData().get(position);
                Intent intent = DetailActivity.newIntent(getContext(),sectionModel.t);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), adapter.getViewByPosition(position,R.id.cover), "trans");
                ActivityCompat.startActivity(getContext(),intent,compat.toBundle());

            }
        });




        //endregion

        //初始化一开始数据
        downDataNetWork();

    }

    /**
     * 重api加载数据
     */
    private void downDataNetWork(){

        RetrofitFactory.getInstance().API().getMovies(mCurrentCount,10)
                .compose(new Transformer<MovieReponse>())
                .subscribe(new BaseObserver<MovieReponse>() {
                    @Override
                    protected void onSuccess(final MovieReponse movieReponse) throws Exception {

                        mSubjectsList = getSubjectsList(movieReponse.getSubjects(),true);
                        mSectionAdapter.setNewData(mSubjectsList);
                        Log.i("test","setNewData");
//                        //移动到最顶部
//                        mRecyclerView.scrollToPosition(0);
                        mCurrentCount = new Random().nextInt(241);
                        if(mSmartRefreshLayout.isRefreshing()){
                            mSmartRefreshLayout.finishRefresh();
                        }

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
     * 把数据转换为SectionModel
     * @param subjectses
     * @return
     */
    private List<SectionModel> getSubjectsList(List<MovieReponse.Subjects> subjectses,boolean hasHeader){

        List<SectionModel> modelList = new ArrayList<>();

        if(hasHeader){
            //添加头部广告
            SectionModel s1 = new SectionModel(true,"header");
            s1.setAdBeanList(getAdBeanList());
            modelList.add(s1);
        }


        //重新创建整理模型
        for (MovieReponse.Subjects subjects : subjectses){
            SectionModel sectionModel = new SectionModel(subjects);
            modelList.add(sectionModel);
        }

        return modelList;
    }


    /**
     * 获取广告数据
     * @return
     */
    private List<AdBean> getAdBeanList(){

        mAdBeanList = new ArrayList<>();

        AdBean adBean1 = new AdBean();
        adBean1.setId("1");
        adBean1.setTitle("侏罗纪公园2");
        adBean1.setImgUrl("https://img3.doubanio.com/view/photo/l/public/p2524984613.jpg");
        adBean1.setTargetUrl("https://movie.douban.com/subject/26416062/?from=showing");
        adBean1.setAd(true);//代表是广告
        mAdBeanList.add(adBean1);

        AdBean adBean2 = new AdBean();
        adBean2.setId("2");
        adBean2.setTitle("超人总动员2");
        adBean2.setImgUrl("https://img1.doubanio.com/view/photo/m/public/p2524230608.jpg");
        adBean2.setTargetUrl("https://movie.douban.com/subject/25849049/?from=showing");
        adBean2.setAd(true);//代表是广告
        mAdBeanList.add(adBean2);

        AdBean adBean3 = new AdBean();
        adBean3.setId("3");
        adBean3.setTitle("龙虾刑警 ");
        adBean3.setImgUrl("https://img3.doubanio.com/view/photo/m/public/p2523437474.jpg");
        adBean3.setTargetUrl("https://movie.douban.com/subject/26992383/?from=showing");
        adBean3.setAd(true);//代表是广告
        mAdBeanList.add(adBean3);

        AdBean adBean4 = new AdBean();
        adBean4.setId("4");
        adBean4.setTitle("动物世界");
        adBean4.setImgUrl("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2525143094.jpg");
        adBean4.setTargetUrl("https://movie.douban.com/subject/26925317/?from=showing");
        adBean4.setAd(true);//代表是广告
        mAdBeanList.add(adBean4);

        AdBean adBean5 = new AdBean();
        adBean5.setId("5");
        adBean5.setTitle("泄密者");
        adBean5.setImgUrl("https://img3.doubanio.com/view/photo/m/public/p2524686285.jpg");
        adBean5.setTargetUrl("https://movie.douban.com/subject/27195080/?from=playing_poster");
        adBean5.setAd(true);//代表是广告
        mAdBeanList.add(adBean5);

        return mAdBeanList;
    }

    /**
     * 查询电影
     * @param movieName
     */
    private void doSearch(String movieName){
        //跳转到搜索Activity
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra("movieName",movieName);
        startActivity(intent);
    }


    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        downDataNetWork();
    }


    //endregion
}
