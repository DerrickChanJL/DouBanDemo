package com.example.android.douban.adapter;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android.douban.R;
import com.example.android.douban.model.MovieReponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by Derrick on 2018/6/19.
 */

public class MovieAdapter extends BaseQuickAdapter<MovieReponse.Subjects,BaseViewHolder>{


    public MovieAdapter(@LayoutRes int layoutResId, @Nullable List<MovieReponse.Subjects> data, View view) {
        super(layoutResId, data);
        this.setEmptyView(view);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MovieReponse.Subjects item) {


        //加载图片
         Glide.with(helper.itemView.getContext()).load(item.getImages().getSmall())
                .asBitmap().into(new SimpleTarget<Bitmap>() {
             @Override
             public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                 helper.setImageBitmap(R.id.cover,resource);
             }
         });

        //设置标题
        helper.setText(R.id.title_text_view,item.getTitle());
        //设置电影类型
        helper.setText(R.id.movie_type_text,item.genresToString());
        //设置评论人数
        helper.setText(R.id.year_text,item.getCollect_count() + "人评论");
        //设置评分
        helper.setText(R.id.rating_text,item.getRating().getAverage()+"");
        //设置星星
        helper.setRating(R.id.ratingBar,(float) item.getRating().getAverage());


    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {


    }
}
