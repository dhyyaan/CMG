package com.think360.cmg.view.acitivity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.think360.cmg.AppController;
import com.think360.cmg.R;
import com.think360.cmg.databinding.ActivityMainBinding;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.model.user.Data;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;


    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppController.getInstance().getComponent().inject(this);


    }


    public void login(View view) {


        Call<Data> dataCall = apiService.loginUser(activityMainBinding.etEmail.getText().toString().trim(), activityMainBinding.etPassword.getText().toString().trim());

        dataCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                finish();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
