package com.tnovoselec.beautifulweather;

import android.app.Application;

import com.tnovoselec.beautifulweather.di.ApplicationComponent;
import com.tnovoselec.beautifulweather.di.ComponentFactory;
import com.tnovoselec.beautifulweather.di.ComponentProvider;

public class BeautifulWeatherApplication extends Application implements ComponentProvider<ApplicationComponent> {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    inject();
  }

  protected void inject() {
    applicationComponent = ComponentFactory.createApplicationComponent(this);
    applicationComponent.inject(this);
  }


  @Override
  public ApplicationComponent getComponent() {
    return applicationComponent;
  }
}
