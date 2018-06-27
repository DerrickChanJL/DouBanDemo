package com.example.android.douban.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.android.douban.R;
import com.example.android.douban.activity.WebViewActivity;
import com.example.android.douban.fragment.MovieFragment;
import com.example.android.douban.model.AdBean;
import com.example.android.douban.model.SectionModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Derrick on 2018/6/20.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionModel,BaseViewHolder> {


    //ViewPager组件
    private ViewPager mViewPager;
    private Context mContext;

    //广告显示的TextView
    private TextView mDataTextView;
    private TextView mTitleTextView;
    private TextView mTopicFromTextView;
    private TextView mTopicTextView;

    // 定义的五个指示点
    private View dot0;
    private View dot1;
    private View dot2;
    private View dot3;
    private View dot4;

    //定时任务线程
    private ScheduledExecutorService mScheduledExecutorService;
    //当前广告下标(索引)
    private int mCurrentItemIndex;
    private List<View> mDots; // 图片标题正文的那些点
    private List<View> mDotList;
    //滑动广告图片集合
    private List<ImageView> mImageViews;
    // 轮播banner的数据
    private List<AdBean> mAdBeanList = new ArrayList<>();
    //通过handle来通知ViewPager进行视图切换
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mCurrentItemIndex);
        }
    };


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionModel> data,View view,Context context) {
        super(layoutResId, sectionHeadResId, data);
        this.setEmptyView(view);
        mContext = context;
    }


    @Override
    protected void convertHead(final BaseViewHolder helper, SectionModel item) {

        Log.i("test","isHead");
        //region 广告模块View
        mImageViews = new ArrayList<ImageView>();

        // 点
        mDots = new ArrayList<View>();
        mDotList = new ArrayList<View>();
        dot0 = helper.getView(R.id.v_dot0);
        dot1 = helper.getView(R.id.v_dot1);
        dot2 = helper.getView(R.id.v_dot2);
        dot3 = helper.getView(R.id.v_dot3);
        dot4 = helper.getView(R.id.v_dot4);
        mDots.add(dot0);
        mDots.add(dot1);
        mDots.add(dot2);
        mDots.add(dot3);
        mDots.add(dot4);

        //TextView
        mTitleTextView = (TextView) helper.getView(R.id.tv_title);
        mViewPager = (ViewPager) helper.getView(R.id.vp);
        mViewPager.setAdapter(new MyViewPagerAdapter(item));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        mViewPager.addOnPageChangeListener(new MyPageChangerListener(item));
        initDynamicView(helper,item);

        //endregion

        startAd();

    }

    @Override
    protected void convert(final BaseViewHolder helper, final SectionModel item) {

        //加载图片
        Glide.with(helper.itemView.getContext()).load(item.t.getImages().getSmall())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                helper.setImageBitmap(R.id.cover,resource);
            }
        });

        //设置标题
        helper.setText(R.id.title_text_view,item.t.getTitle());
        //设置电影类型
        helper.setText(R.id.movie_type_text,item.t.genresToString());
        //设置评论人数
        helper.setText(R.id.year_text,item.t.getCollect_count() + "人评论");
        //设置评分
        helper.setText(R.id.rating_text,item.t.getRating().getAverage()+"");
        //设置星星
        helper.setRating(R.id.ratingBar,(float) item.t.getRating().getAverage()/2.0f);



    }





    //region 广告模块
    /**
     * 动态初始化指示的原点
     */
    private void initDynamicView(BaseViewHolder holder ,SectionModel item){

        mAdBeanList = item.getAdBeanList();
        // 动态添加图片和下面指示的圆点
        // 初始化图片资源
        for (int i = 0; i < item.getAdBeanList().size(); i++) {
            ImageView imageView = new ImageView(holder.itemView.getContext());
            // 异步加载图片
            Glide.with(holder.itemView.getContext()).load(item.getAdBeanList().get(i).getImgUrl()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews.add(imageView);
            mDots.get(i).setVisibility(View.VISIBLE);
            mDotList.add(mDots.get(i));
        }
    }

    /**
     * 开启一个线程执行定时任务
     * 轮番播放广告
     */
    private void startAd(){
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
                TimeUnit.SECONDS);
    }


    /**
     * 轮播子线程
     */
    private class ScrollTask implements Runnable{

        @Override
        public void run() {
            mCurrentItemIndex = (mCurrentItemIndex + 1) % mImageViews.size();
            mHandler.obtainMessage().sendToTarget();
        }
    }


    //region ViewPager模块

    /**
     * 监听ViewPager页面更换
     */
    private class MyPageChangerListener implements ViewPager.OnPageChangeListener{

        private SectionModel item;
        public MyPageChangerListener(SectionModel item){
            this.item = item;
        }

        private int oldPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentItemIndex = position;
            AdBean adBean = item.getAdBeanList().get(position);
            mTitleTextView.setText(adBean.getTitle()); // 设置标题
            mDots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            mDots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /**
     * 自定义的ViewPager适配器
     */
    private class MyViewPagerAdapter extends PagerAdapter {

        private SectionModel item;
        public MyViewPagerAdapter (SectionModel item){
            this.item = item;
        }

        @Override
        public int getCount() {
            return item.getAdBeanList().size();
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView iv = mImageViews.get(position);
            ((ViewPager) container).addView(iv);
            final AdBean adDomain = item.getAdBeanList().get(position);
            // 在这个方法里面设置图片的点击事件
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdBean adBean = mAdBeanList.get(position);
                    Intent intent = WebViewActivity.newInstance(mContext,adBean.getTargetUrl());
                    mContext.startActivity(intent);
                }
            });
            return iv;
        }
    }

    //endregion
    //endregion


    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        if(holder.getItemViewType() != 0){
            if(mScheduledExecutorService != null)
            {
                mScheduledExecutorService.shutdown();
                Log.i("test","shutdown");
            }

        }
    }
}
