package com.example.android.douban.fragment.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.douban.R;
import com.example.android.douban.model.MovieReponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Derrick on 2018/6/20.
 */

public class IntroductionFragment extends Fragment {

    private String mIntroducation;

    private static String mAlt;
    private TextView mTextView;

    private ScheduledExecutorService mScheduledExecutorService;

    //通过handle来通知TextView设置text
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mIntroducation = (String)msg.obj;
            mTextView.setText((String)msg.obj);
        }
    };

    public static IntroductionFragment newInstance(String alt){
        mAlt = alt;
        return new IntroductionFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_introduce,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData(){

        //如果没有爬取到简介则爬取
        if(mIntroducation == null || mIntroducation.equals(""))
        {
            new Thread(new IntroductionTask()).start();
        }else {
            //否则设置简介
            mTextView.setText(mIntroducation);
        }
    }
    /**
     *
     */
    private void initView(View view){

        mTextView = view.findViewById(R.id.introduction_text_view);
        loadData();
    }

    /**
     * 根据alt抓取简介
     */
    private class IntroductionTask implements Runnable{

        @Override
        public void run() {
            //获得数据
            try {
                Log.i("test","run + " + mAlt);
                Document doc = Jsoup.connect(mAlt).get();
                Elements div = doc.select("#link-report");

                Elements in1 = doc.select("span.all.hidden");
                Elements in3 = doc.select("span");
                if(in1.size() > 0){
                    mHandler.obtainMessage(1,in1.get(0).text()).sendToTarget();
                }else {
                    Elements in2 = doc.body().getElementsByAttribute("property");
                    Log.i("test","size " + in2.size());
                    if(in2.size() > 0){

                        for (Element e : in2){
                            Log.i("test","value " + e.attr("property"));
                            if(e.attr("property").equals("v:summary")){
                                mHandler.obtainMessage(1,e.text()).sendToTarget();
                                break;
                            }
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.w("test",e);
            }

        }
    }
}
