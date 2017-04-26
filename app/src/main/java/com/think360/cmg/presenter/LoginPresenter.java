package com.think360.cmg.presenter;

import com.think360.cmg.AppController;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.user.User;
import com.think360.cmg.utils.AppConstants;
import com.think360.cmg.view.acitivity.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by think360 on 18/04/17.
 */

public class LoginPresenter extends BasePresenter {

    private LoginPresenter.View view;

    public interface View {


        void loginSucessfull(String firstName, int workderId);

        void loginFailed(Throwable t);
    }

    public LoginPresenter(final LoginPresenter.View view, ApiService apiService, String email, String password) {
        super((LoginActivity) view);
        this.view = view;
        pDialog.show();
        apiService.loginUser(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body().getStatus()) {
                    pDialog.dismiss();

                    AppController.getSharedPrefEditor().putInt(AppConstants.WORKER_ID, response.body().getData().getWorkerId()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.WORKER_NAME, response.body().getData().getWorkerName()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.WORKER_EMAIL, response.body().getData().getEmail()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.WORKER_PROFILE_IMAGE_URL, response.body().getData().getWorkerPic()).apply();

                    view.loginSucessfull(response.body().getData().getWorkerName(), response.body().getData().getWorkerId());

                } else {
                    pDialog.dismiss();
                    alertDialog.setMessage(response.body().getMessage());
                    alertDialog.show();
                    Timber.d("LOGIN_ELSE", response.body());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pDialog.dismiss();
                view.loginFailed(t);
            }
        });

    }
}
