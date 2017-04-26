package com.think360.cmg.di.components;

import com.think360.cmg.di.modules.AndroidModule;
import com.think360.cmg.view.acitivity.HomeActivity;
import com.think360.cmg.view.acitivity.LoginActivity;
import com.think360.cmg.di.modules.ApplicationModule;
import com.think360.cmg.di.modules.HttpModule;
import com.think360.cmg.manager.ApiService;
import com.think360.cmg.view.fragment.ProfileFragment;
import com.think360.cmg.view.fragment.WorkHistoryFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class, AndroidModule.class})
public interface AppComponent {
    void inject(HomeActivity homeActivity);
    void inject(LoginActivity fragment);
    void inject(WorkHistoryFragment fragment);
    void inject(ProfileFragment fragment);
    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
