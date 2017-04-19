package com.think360.cmg.view.acitivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.think360.cmg.R;
import com.think360.cmg.StopWatchService;
import com.think360.cmg.view.fragment.WorkFragment;

public class HomeActivity extends AppCompatActivity implements WorkFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(android.R.id.content, WorkFragment.newInstance("","")).commitAllowingStateLoss();

    }

    public void startService(View view) {
        Intent intent = new Intent(getApplicationContext(), StopWatchService.class);
        startService(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
