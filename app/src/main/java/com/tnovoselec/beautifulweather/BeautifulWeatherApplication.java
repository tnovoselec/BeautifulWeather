package com.tnovoselec.beautifulweather;

import com.squareup.leakcanary.LeakCanary;
import com.tnovoselec.beautifulweather.di.ApplicationComponent;
import com.tnovoselec.beautifulweather.di.ComponentFactory;
import com.tnovoselec.beautifulweather.di.ComponentProvider;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class BeautifulWeatherApplication extends Application implements ComponentProvider<ApplicationComponent> {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    inject();

    LeakCanary.install(this);
  }

  protected void inject() {
    applicationComponent = ComponentFactory.createApplicationComponent(this);
    applicationComponent.inject(this);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  @Override
  public ApplicationComponent getComponent() {
    return applicationComponent;
  }
}
