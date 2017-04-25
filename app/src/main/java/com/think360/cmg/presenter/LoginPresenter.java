package com.think360.cmg.presenter;

import android.app.ProgressDialog;

import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.user.Data;
import com.think360.cmg.view.acitivity.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by think360 on 18/04/17.
 */

public class LoginPresenter {

    private LoginPresenter.View view;
    private ProgressDialog pDialog;


    public interface View {

        void loginSucessfull(String firstName, String workderId);

        void loginFailed(Throwable t);
    }

    public LoginPresenter(final LoginPresenter.View view, ApiService apiService, String email, String password) {
        this.view = view;
        pDialog = new ProgressDialog((LoginActivity) view);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();


        apiService.loginUser(email, password).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    view.loginSucessfull(response.body().getFirstName(), response.body().getWorkerId());


                } else {
                    pDialog.dismiss();
                    Timber.d("LOGIN_ELSE", response.body());

                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                pDialog.dismiss();
                view.loginFailed(t);
            }
        });

    }
}
