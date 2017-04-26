package com.think360.cmg.presenter;

import android.content.Context;

import com.think360.cmg.AppController;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.WorkerEditProfileModel;
import com.think360.cmg.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by think360 on 26/04/17.
 */

public class HomePresenter extends BasePresenter {


    private ApiService apiService;
    private View view;

    public interface View {

        void logOutSucessfull();

        void logOutFailed(Throwable t);
    }


    public HomePresenter(Context context, View view, ApiService apiService) {
        super(context);
        this.view = view;
        this.apiService = apiService;
        logout();
    }

    private void logout() {


        apiService.logoutWorker(AppController.sharedPreferencesCompat.getInt(AppConstants.WORKER_ID, 0)).enqueue(new Callback<WorkerEditProfileModel>() {
            @Override
            public void onResponse(Call<WorkerEditProfileModel> call, Response<WorkerEditProfileModel> response) {
                pDialog.dismiss();
                view.logOutSucessfull();

            }

            @Override
            public void onFailure(Call<WorkerEditProfileModel> call, Throwable t) {
                pDialog.dismiss();
                view.logOutFailed(t);
            }
        });
    }

}
