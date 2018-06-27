package com.example.android.douban.network;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Derrick on 2018/4/8.
 * Observer的基类
 */

public abstract class BaseObserver<T> implements Observer<T> {

    protected Context mContext;
    protected Disposable d;

    public BaseObserver(Context context){
        mContext = context;
    }

    public BaseObserver(){

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onRequestStart(d);
        Log.i("test","onSubscribe");
    }

    @Override
    public void onNext(@NonNull T t) {
        onRequestEnd();
        try {
            onSuccess(t);
            Log.i("test","call onSuccess");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.w("TAG",e.toString());
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 返回成功
     * @throws Exception
     */
    protected abstract void onSuccess(T t) throws Exception;

    /**
     * 返回成功 但code错误
     * @throws Exception
     */
    protected void onCodeError(T t) throws Exception{}

    /**
     * 返回失败
     * @param e
     * @param isNetWorkError
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart(Disposable d){
        this.d = d;
    }

    protected void onRequestEnd(){
        Log.i("test","onRequestEnd");
    }

    public void showProgressDiglog(){


    }

    public void closeProgressDialog(){

    }


}
