package com.think360.cmg;

import android.app.Application;
import android.content.Context;

import com.think360.cmg.di.components.AppComponent;
import com.think360.cmg.di.components.DaggerAppComponent;
import com.think360.cmg.di.modules.AndroidModule;
import com.think360.cmg.di.modules.ApplicationModule;
import com.think360.cmg.di.modules.HttpModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {


    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();


        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .androidModule(new AndroidModule())
                .httpModule(new HttpModule())
                .build();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/robotolight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Timber.plant(new Timber.DebugTree());

    }


    public AppComponent getComponent() {
        return component;
    }
}

