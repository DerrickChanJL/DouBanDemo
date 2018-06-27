package com.example.android.douban.network;




import com.example.android.douban.model.MovieReponse;
import com.example.android.douban.model.Response;

import com.example.android.douban.model.SearchReponse;
import com.example.android.douban.model.UserBean;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Derrick on 2018/4/7.
 */

public interface APIFunction {


   // private OkHttpClient mOkHttpClient;
//    @FormUrlEncoded
//    @POST(URLConfig.BASE_URI + "test/hello")
//    Observable<User> gettest(@Field("username") String username);//@Query("username") String username
//
//    @FormUrlEncoded
//    @POST(URLConfig.BASE_URI + "test/sms")
//    Observable<User> getsms(@Field("secret") String secret, @Field("phoneNumber") String phomenumber);//@Query("username") String username
//
//    @GET(URLConfig.DOUBAN_BASE_URI + "top250")
//    Observable<Response<List<Movie>>> listMovie(@Query("start") int start, @Query("end") int end);
//
//    //https://api.themoviedb.org/3/list/1?api_key=8dfd7400ee27bbf60cb82b64df74e880&language=zh-CN
//    @GET(URLConfig.BASE_URI+"list/{list_id}")
//    Observable<List<String>> listMovie(@Path("list_id") int list_id, @Query("api_key") String apiKey);
//
////    @FormUrlEncoded
////    @POST(URLConfig.ACCOUNT_URL + "login")
////    Observable<String> login(@Field("phoneNumbers") String phoneNumber);
//
//    @POST(URLConfig.ACCOUNT_URL + "login")
//    Observable<String> login(@Body RequestBody requestBody);
//
////    @FormUrlEncoded
////    @POST(URLConfig.ACCOUNT_URL + "loginConfirm")
////    Observable<LoginConfirm> loginComfirm(@Field("phoneNumbers") String phoneNumber, @Field("code") String code);
//
//    /**
//     * 确认登陆请求
//     * @param requestBody
//     * @return
//     */
//    @POST(URLConfig.ACCOUNT_URL + "loginConfirm")
//    Observable<LoginConfirm> loginComfirm(@Body RequestBody requestBody);
//
//
//    /**
//     * 获取用户信息请求
//     * @param id
//     * @param accessToken
//     * @return
//     */
//    @GET(URLConfig.ACCOUNT_URL + "{userId}")
//    Observable<User> getUser(@Path("userId") int id, @Header("Authorization") String accessToken);
//
//
//    /**
//     * 设置用户
//     * @param requestBody
//     * @param accessToken
//     * @return
//     */
//    @POST(URLConfig.ACCOUNT_URL)
//    Observable<User> setUser(@Body RequestBody requestBody,@Header("Authorization") String accessToken);
//
//

    /**
     * 获取豆瓣电影
     * @param start
     * @param count
     * @return
     */
    @FormUrlEncoded
    @POST(URLConfig.BASE_URI +"top250")
    Observable<MovieReponse> getMovies(@Field("start") int start, @Field("count") int count);

    /**
     * 登陆
     * @return
     */
    @FormUrlEncoded
    @POST(URLConfig.ACCOUNT_URL + "account")
    Observable<Response<UserBean>> login(@Field("action") String action,@Field("username") String username,@Field("password") String password);

    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST(URLConfig.ACCOUNT_URL + "account")
    Observable<Response<UserBean>> register(@Field("action") String action,@Field("username") String username,@Field("password") String password);

    /**
     * 搜索电影
     */
    @GET(URLConfig.SEARCH_URL + "douban")
    Observable<SearchReponse> searchMovie(@Query("apikey") String apikey, @Query("kw") String movieNames);


    //douban?apikey=zUq0gCscEqbP728WKBJM56cOEDw5wnKIqWKN96mQeMFDdHCXNWSwgrb0M0ibDGqv&kw=%E9%87%91%E5%88%9A

}
