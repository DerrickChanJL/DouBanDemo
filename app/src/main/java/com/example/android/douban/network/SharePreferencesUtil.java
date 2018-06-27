package com.example.android.douban.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.android.douban.model.UserBean;

import java.util.ArrayList;
import java.util.HashSet;


/**
 *
 */

public class SharePreferencesUtil {

    private static SharePreferencesUtil instance;

    private static Context mContext;
    private SharedPreferences mSharedPreferences;


    private SharePreferencesUtil(Context context){}
    public static SharePreferencesUtil getInstance(Context context){
        if(instance == null)
        {
            instance = new SharePreferencesUtil(context);
            mContext = context;
        }

        return instance;
    }


    /**
     * 保存用户ID
     * @param fileName
     * @param id
     */
    public void saveUserId(String fileName, String id){
        mSharedPreferences = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("userId",id);
        editor.commit();
    }

    /**
     * 获取用户id
     * @param filename
     * @return
     */
    public String getUserId(String filename){
        try {
            SharedPreferences data = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
            return data.getString("userId",null);
        }catch (NullPointerException e){
            return null;
        }


    }

    /**
     * 保存用户
     * @param fileName
     * @param user
     */
    public void saveUser(String fileName,UserBean user){
        mSharedPreferences = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("username",user.getUsername());
        //editor.putStringSet("collection",new HashSet<String>(user.getCollection()));
        Log.i("test","保存用户信息");
        editor.apply();
    }

    /**
     * 修改用户名
     * @param fileName
     */
    public void updateUsername(String fileName,String name){
        mSharedPreferences = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("username",name);
        editor.apply();
    }


    /**
     * 获取用户
     * @param filename
     * @return
     */
    public UserBean getUser(String filename){

        try {
            SharedPreferences data = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
            if(data.getInt("id",-1) == -1)
                return null;

            UserBean user = new UserBean();
            user.setId(data.getInt("id",-1));
            user.setUsername(data.getString("username",null));
            Log.i("test","获取用户信息");
            return user;
        }catch (NullPointerException e){
            Log.i("test","???");
            return null;
        }
    }

    /**
     * 清除用户id数据
     * @param filename
     */
    public void clear(String filename){
        SharedPreferences data = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        if(data != null)
            data.edit().clear();
    }

    /**
     * 删除用户信息
     * @param filename
     */
    public void clearUser(String filename){
        SharedPreferences data = mContext.getSharedPreferences(filename,Context.MODE_PRIVATE);
        if(data != null)
        {
            data.edit().clear().commit();
            Log.i("test","删除用户信息");
        }
    }


    /**
     * 设置当前城市
     * @param fileName
     * @param city
     */
    public void setCurrentCity(String fileName,String city){
        mSharedPreferences = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("current_city",city);
        editor.commit();

    }

    /**
     * 获取当前城市
     * @param fileName
     * @return
     */
    public String getCurrentCity(String fileName){
        try {
            SharedPreferences data = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            Log.i("test",data.getString("current_city",null));

            String current_city = data.getString("current_city",null);
            return current_city;
        }catch (NullPointerException e){
            Log.i("test","???");
            return null;
        }

    }

}
