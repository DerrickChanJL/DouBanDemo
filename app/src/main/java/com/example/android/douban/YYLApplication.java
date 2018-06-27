package com.example.android.douban;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;


import com.example.android.douban.network.SharePreferencesUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 *
 */

public class YYLApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //sHA1(mContext);
        Log.i("test"," "+dip2px(mContext,25));

       // Log.i("test",FileUtils.getFilePath(this,FileUtils.TOKEN_DIR));
       // FileUtils.writeAccessToken(this,"token.txt","456456");
        //Log.i("test",FileUtils.readAccessToken(this,"token.txt"));
        //User user = new User();
        //user.setName("Derrick");
        //LoginViewModel.setUserTest(user);
        SharePreferencesUtil.getInstance(mContext).getUser("user");


    }

    public static Context getContext(){
        return mContext;
    }

    /**
     * 检查后台网络的可用性
     * @return
     */
    public static boolean isNetworkAvailableAndConnected(){
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(CONNECTIVITY_SERVICE);
        //不要忘了，要使用getActiveNetworkInfo()方法，还要在manifest配置文件中获取
        //ACCESS_NETWORK_STATE权限
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

    /**
     * 判断用户是否登录
     * @return
     */
    public static boolean isLogin(){

        return SharePreferencesUtil.getInstance(mContext).getUser("user") == null? false:true;
    }

//    /**
//     * 用户登录
//     * @return
//     */
//    public static void login(String accessToken){
//        FileUtils.writeAccessToken(mContext,"token.txt",accessToken);
//    }
//
//    /**
//     * 注销
//     */
//    public static void logout(){
//        FileUtils.clearToken(mContext,"token.txt");
//    }
//
//    /**
//     * 返回token
//     * @return
//     */
//    public static String getToken(){
//        return FileUtils.readAccessToken(mContext,"token.txt");
//    }

        public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            Log.i("sha1",": " + result.substring(0, result.length()-1));
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * EditText获取焦点并显示软键盘
     * @param activity
     * @param editText
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }



}
