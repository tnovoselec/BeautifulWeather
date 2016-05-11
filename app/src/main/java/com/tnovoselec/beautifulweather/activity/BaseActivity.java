package com.tnovoselec.beautifulweather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tnovoselec.beautifulweather.BeautifulWeatherApplication;
import com.tnovoselec.beautifulweather.di.ActivityComponent;
import com.tnovoselec.beautifulweather.di.ComponentFactory;
import com.tnovoselec.beautifulweather.di.ComponentProvider;

public abstract class BaseActivity extends AppCompatActivity implements ComponentProvider<ActivityComponent> {

  private ActivityComponent activityComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectMe();
  }

  @Override
  public ActivityComponent getComponent() {
        /*
            This can happen when system kills activity. In that case all components depending on
            this one would crash. You can simulate this by checking
            Settings->Developer options->Don't keep activities
         */
    if (activityComponent == null) {
      activityComponent = ComponentFactory.createActivityComponent(this, (BeautifulWeatherApplication) getApplicationContext());
    }
    return activityComponent;
  }


  protected abstract void inject(ActivityComponent activityComponent);

  private void injectMe() {
    activityComponent = getComponent();
    inject(activityComponent);
  }
}
