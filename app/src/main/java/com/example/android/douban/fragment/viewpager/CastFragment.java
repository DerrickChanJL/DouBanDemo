package com.example.android.douban.fragment.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.douban.HSpaceItemDecoration;
import com.example.android.douban.R;
import com.example.android.douban.activity.WebViewActivity;
import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.network.BaseObserver;
import com.example.android.douban.network.RetrofitFactory;
import com.example.android.douban.network.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2018/6/20.
 */

public class CastFragment extends Fragment {

    public class Character{

        private String alt;
        private String avator;
        private String name;
        private String id;
        private String type;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    private RecyclerView mRecyclerView;
    //主演集合
    private static List<MovieReponse.Subjects.Casts> mCastList;
    //导演集合
    private static List<MovieReponse.Subjects.Directors> mDirectorList;
    //人物集合
    private static List<Character> mCharacterList;

    public static CastFragment newInstance(List<MovieReponse.Subjects.Casts> castses,List<MovieReponse.Subjects.Directors> directorses){
        mCastList = castses;
        mDirectorList = directorses;
        return new CastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast,container,false);
        initData();
        initView(view);
        return view;
    }

    private void initData(){
        mCharacterList = new ArrayList<>();
        for (MovieReponse.Subjects.Casts casts : mCastList){
            Character character = new Character();
            character.setId(casts.getId());
            character.setAlt(casts.getAlt());
            character.setAvator(casts.getAvatars().getSmall());
            character.setName(casts.getName());
            character.setType("主演");

            mCharacterList.add(character);
        }

        for (MovieReponse.Subjects.Directors directors : mDirectorList){
            Character character = new Character();
            character.setId(directors.getId());
            character.setAlt(directors.getAlt());
            character.setAvator(directors.getAvatars().getSmall());
            character.setName(directors.getName());
            character.setType("导演");


            mCharacterList.add(character);
        }


    }
    /**
     * 初始化视图
     */
    private void initView(View view){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRecyclerView.addItemDecoration(new HSpaceItemDecoration(20));
        mRecyclerView.setAdapter(new CastAdapter());
    }

    /**
     * 主演item适配器
     */
    public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{


        @Override
        public CastAdapter.CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.common_item,null);
            return new CastAdapter.CastViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CastViewHolder holder, int position) {
            //拿到当前位置的数据
            Character character = mCharacterList.get(position);
            holder.bind(character);
        }

        @Override
        public int getItemCount() {
            return mCharacterList.size();
        }


        public class CastViewHolder extends RecyclerView.ViewHolder{

            private TextView mTitleTextView;
            private ImageView mImageView;
            private View mView;

            public CastViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                mTitleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
                mImageView = (ImageView) itemView.findViewById(R.id.cover);
            }

            private void bind(final Character character){

                //设置名字
                mTitleTextView.setText(character.getType()+"/"+character.getName());
                //加载图片
                Glide.with(getContext())
                        .load(character.getAvator())
                        .error(R.drawable.avator)
                        .into(mImageView);
                //
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转webViewActivity
                        Intent intent = WebViewActivity.newInstance(getContext(),character.getAlt());
                        startActivity(intent);
                    }
                });
            }//bind_end
        }//viewHolder_end
    }
}
