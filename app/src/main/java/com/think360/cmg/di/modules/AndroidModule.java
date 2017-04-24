package com.think360.cmg.di.modules;

import android.support.customtabs.CustomTabsIntent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @Provides
    @Singleton
    public CustomTabsIntent.Builder provideCustomTabsBuilder() {
        return new CustomTabsIntent.Builder();
    }

    @Provides
    @Singleton
    public CustomTabsIntent provideCustomTabsIntent(CustomTabsIntent.Builder builder) {
        return builder.build();
    }
}
