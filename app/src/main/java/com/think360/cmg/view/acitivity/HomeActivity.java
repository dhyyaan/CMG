package com.think360.cmg.view.acitivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.think360.cmg.adapter.PagerAdapter;
import com.think360.cmg.R;
import com.think360.cmg.StopWatchService;
import com.think360.cmg.databinding.ActivityHomeBinding;
import com.think360.cmg.view.fragment.TimeFragment;
import com.think360.cmg.view.fragment.WorkFragment;
import com.think360.cmg.view.fragment.WorkHistoryFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements WorkFragment.OnFragmentInteractionListener,WorkHistoryFragment.OnFragmentInteractionListener,TimeFragment.OnFragmentInteractionListener {


    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        activityHomeBinding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getFragmentArrrayList()));


    }

    private ArrayList<Fragment> getFragmentArrrayList() {
        ArrayList<Fragment> fragmentSparseArray = new ArrayList<>();
        fragmentSparseArray.add(WorkFragment.newInstance("", ""));
        fragmentSparseArray.add(WorkHistoryFragment.newInstance("", ""));
        fragmentSparseArray.add(TimeFragment.newInstance("", ""));

        return fragmentSparseArray;

    }

    public void startService(View view) {
        Intent intent = new Intent(getApplicationContext(), StopWatchService.class);
        startService(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
