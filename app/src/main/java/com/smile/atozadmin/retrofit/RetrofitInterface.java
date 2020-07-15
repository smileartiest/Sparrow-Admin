package com.smile.atozadmin.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("sparrow/updatetoken.php")
    Call<String> updateid(@Field("phno") String phno,
                          @Field("token") String token);

    @FormUrlEncoded
    @POST("sparrow/usernotify.php")
    Call<String> sendpush(@Field("uid") String uid,
                          @Field("tit") String title,
                          @Field("msg") String msg);

}


