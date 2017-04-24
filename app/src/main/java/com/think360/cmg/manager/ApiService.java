package com.think360.cmg.manager;

import com.think360.cmg.model.user.Data;
import com.think360.cmg.model.work.WorkHistory;

import io.reactivex.Observable;
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


    @FormUrlEncoded
    @POST("workerprofile")
    Call<WorkHistory> getWorkHistoryWithCall(@Field("id") int password);


    @FormUrlEncoded
    @POST("workerprofile/")
    Observable<WorkHistory> getWorkHistory(@Field("id") int password);


}
