package com.tnovoselec.beautifulweather.di;


import com.tnovoselec.beautifulweather.BeautifulWeatherApplication;
import com.tnovoselec.beautifulweather.activity.BaseActivity;

public final class ComponentFactory {

  public static ApplicationComponent createApplicationComponent(BeautifulWeatherApplication application) {
    return ApplicationComponent.Initializer.init(application);
  }

  public static ActivityComponent createActivityComponent(BaseActivity injectorActivity,
                                                          ComponentProvider<ApplicationComponent> applicationComponentProvider) {
    return ActivityComponent.Initializer.init(injectorActivity, applicationComponentProvider);
  }
}