package com.example.android.douban.network;

import android.util.Log;

import com.example.android.douban.YYLApplication;

import java.io.IOException;
import java.util.concurrent.Executor;



import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Derrick on 2018/4/7.
 * 拦截器工具类
 */

public class InterceptorUtil {

    private static String TAG = "InterceptorUtil";

    //日志打印拦截器
    public static HttpLoggingInterceptor LogInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: "+message );
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }


    //网络缓存拦截器
    public static Interceptor NetworkInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Response response = chain.proceed(request);

//                if (YYLApplication.isNetworkAvailableAndConnected()) {
//                    int maxAge = 60*60*24*2;//缓存失效时间，单位为秒
//                    return response.newBuilder()
//                            .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                            .header("Cache-Control", "public ,max-age=" + maxAge)
//                            .build();
//                }
                return response;
            }
        };
    }




}
