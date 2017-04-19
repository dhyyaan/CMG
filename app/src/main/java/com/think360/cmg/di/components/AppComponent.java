package com.think360.cmg.di.components;

import com.think360.cmg.view.acitivity.LoginActivity;
import com.think360.cmg.di.modules.ApplicationModule;
import com.think360.cmg.di.modules.HttpModule;
import com.think360.cmg.manager.ApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(LoginActivity fragment);

    ApiService api();
}
