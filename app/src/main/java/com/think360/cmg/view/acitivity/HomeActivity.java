package com.think360.cmg.view.acitivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.think360.cmg.AppController;
import com.think360.cmg.R;
import com.think360.cmg.StopWatchService;
import com.think360.cmg.adapter.PagerAdapter;
import com.think360.cmg.databinding.ActivityHomeBinding;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.presenter.HomePresenter;
import com.think360.cmg.view.fragment.ProfileFragment;
import com.think360.cmg.view.fragment.WorkTimeFragment;
import com.think360.cmg.view.fragment.WorkHistoryFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class HomeActivity extends AppCompatActivity implements WorkTimeFragment.OnFragmentInteractionListener, WorkHistoryFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, HomePresenter.View {

    @Inject
    ApiService apiService;
    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppController) getApplication()).getComponent().inject(this);
        activityHomeBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        activityHomeBinding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getFragmentArrrayList()));
        activityHomeBinding.viewPager.setOffscreenPageLimit(3);
        activityHomeBinding.toolbar.toolBarTitle.setText("Historico");
        activityHomeBinding.BottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {

                switch (i) {
                    case R.id.bbn_item1:
                        activityHomeBinding.toolbar.toolBarTitle.setText("Historico");
                        activityHomeBinding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.bbn_item2:
                        activityHomeBinding.toolbar.toolBarTitle.setText("Trabajo");
                        activityHomeBinding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.bbn_item3:
                        activityHomeBinding.toolbar.toolBarTitle.setText("Ajustes");
                        activityHomeBinding.viewPager.setCurrentItem(2);
                        break;

                }
            }

            @Override
            public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

            }
        });

        activityHomeBinding.toolbar.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new HomePresenter(HomeActivity.this, HomeActivity.this, apiService);

            }
        });


    }

    private ArrayList<Fragment> getFragmentArrrayList() {
        ArrayList<Fragment> fragmentSparseArray = new ArrayList<>();
        fragmentSparseArray.add(WorkHistoryFragment.newInstance("", ""));
        fragmentSparseArray.add(WorkTimeFragment.newInstance("", ""));
        fragmentSparseArray.add(ProfileFragment.newInstance("", ""));

        return fragmentSparseArray;

    }

    public void startService(View view) {
        Intent intent = new Intent(getApplicationContext(), StopWatchService.class);
        startService(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void logOutSucessfull() {

        AppController.getSharedPrefEditor().clear().apply();

        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void logOutFailed(Throwable t) {

    }
}
