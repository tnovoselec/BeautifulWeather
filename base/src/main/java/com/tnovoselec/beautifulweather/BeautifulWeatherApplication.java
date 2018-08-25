package com.tnovoselec.beautifulweather;

import android.app.Application;
import android.content.Context;

public class BeautifulWeatherApplication extends Application{

  public static Context applicationContext;

  @Override
  public void onCreate() {
    super.onCreate();
    applicationContext = this;
  }
}
