package com.think360.cmg;

import android.app.Application;

import com.think360.cmg.di.components.AppComponent;
import com.think360.cmg.di.components.DaggerAppComponent;
import com.think360.cmg.di.modules.ApplicationModule;
import com.think360.cmg.di.modules.HttpModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MainApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    public AppComponent getComponent() {
        return component;
    }
}

