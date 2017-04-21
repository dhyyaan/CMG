package com.think360.cmg;

import android.app.Application;
import android.content.Context;

import com.think360.cmg.di.components.AppComponent;
import com.think360.cmg.di.components.DaggerAppComponent;
import com.think360.cmg.di.modules.ApplicationModule;
import com.think360.cmg.di.modules.HttpModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AppController extends Application {


    private static AppController instance;

    private AppComponent applicationComponent;

    public static AppController getInstance() {
        return instance;
    }

    public static void setInstance(AppController instance) {
        AppController.instance = instance;
    }


    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);
        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/robotolight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }


    public AppComponent getComponent() {
        return component;
    }
}

