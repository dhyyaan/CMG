package com.think360.cmg.view.acitivity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.think360.cmg.AppController;
import com.think360.cmg.R;
import com.think360.cmg.databinding.ActivityMainBinding;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.presenter.LoginPresenter;
import com.think360.cmg.utils.AppConstants;
import com.think360.cmg.utils.KeyboardUtil;

import javax.inject.Inject;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, View.OnClickListener {


    @Inject
    ApiService apiService;


    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((AppController) getApplication()).getComponent().inject(this);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.btnSignIn.setOnClickListener(this);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void loginSucessfull(String firstName, int workderId) {
        AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_REMEMBER_TAPPED, activityMainBinding.switchGprs.isChecked()).apply();
        AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_LOGIN, true).apply();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }


    @Override
    public void loginFailed(Throwable t) {
        Timber.d("FAILED", t.getStackTrace());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                if (TextUtils.isEmpty(activityMainBinding.etEmail.getText().toString().trim()) || TextUtils.isEmpty(activityMainBinding.etPassword.getText().toString().trim()) | !KeyboardUtil.isValidEmail(activityMainBinding.etEmail.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(activityMainBinding.etEmail.getText().toString().trim())) {
                        Toast.makeText(LoginActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();

                    } else if (!KeyboardUtil.isValidEmail(activityMainBinding.etEmail.getText().toString().trim())) {
                        Toast.makeText(LoginActivity.this, "Email should be valid", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new LoginPresenter(this, apiService, activityMainBinding.etEmail.getText().toString().trim(), activityMainBinding.etPassword.getText().toString().trim());
                }
                break;
        }
    }
}
