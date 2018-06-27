package com.example.android.douban.network;





import com.example.android.douban.YYLApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Derrick on 2018/4/7.
 * Retrofit工具类
 */

public class RetrofitFactory {

    private static RetrofitFactory mRetrofitFactory;
    private static APIFunction mAPIFunction;
    private static OkHttpClient mOkHttpClient;      //为了可以重新生成OkHttpClient进行其他操作

    public static OkHttpClient getOkHttpClient(){
        if(mOkHttpClient == null)
            return new OkHttpClient();

        return mOkHttpClient;
    }

    private RetrofitFactory(){

        //缓存目录
       // Cache cache = new Cache(YYLApplication.getContext().getCacheDir(),10 * 10 * 1024);
        //配置Okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //time out
                .connectTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                //设置Cache目录
               // .cache(cache)
                //日志拦截器
                .addInterceptor(InterceptorUtil.LogInterceptor())
                //网络缓存的拦截器
                .addNetworkInterceptor(InterceptorUtil.NetworkInterceptor())
                //失败重连
                .retryOnConnectionFailure(true)
                .build();
        mOkHttpClient = okHttpClient;


        //配置Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConfig.BASE_URI)
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                //添加RxJava转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置OkHttpClient
                .client(okHttpClient)
                .build();

        //关联接口
        mAPIFunction = retrofit.create(APIFunction.class);

    }

    public static RetrofitFactory getInstance(){

        if(mRetrofitFactory == null){
            synchronized (RetrofitFactory.class){
                if(mRetrofitFactory == null){
                    mRetrofitFactory = new RetrofitFactory();
                }
            }
        }

        return mRetrofitFactory;
    }

    public APIFunction API(){
        return mAPIFunction;
    }
}
