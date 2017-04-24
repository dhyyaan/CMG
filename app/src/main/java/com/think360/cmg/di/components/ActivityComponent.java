package com.think360.cmg.di.components;



import com.think360.cmg.di.PerActivity;
import com.think360.cmg.di.modules.ActivityModule;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

}
