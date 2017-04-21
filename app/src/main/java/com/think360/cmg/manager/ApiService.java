package com.think360.cmg.manager;

import com.think360.cmg.model.user.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by think360 on 18/04/17.
 */

public interface ApiService {


    @FormUrlEncoded
    @POST("login/")
    Call<Data> loginUser(@Field("email") String name,
                         @Field("password") String password);

}
