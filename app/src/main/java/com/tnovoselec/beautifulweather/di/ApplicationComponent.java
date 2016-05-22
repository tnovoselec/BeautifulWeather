package com.tnovoselec.beautifulweather.di;


import android.app.Application;

import com.tnovoselec.beautifulweather.BeautifulWeatherApplication;
import com.tnovoselec.beautifulweather.api.WeatherService;
import com.tnovoselec.beautifulweather.business.CityNameDealer;
import com.tnovoselec.beautifulweather.business.LocationDealer;
import com.tnovoselec.beautifulweather.di.module.ApplicationModule;
import com.tnovoselec.beautifulweather.di.module.LocationModule;
import com.tnovoselec.beautifulweather.di.module.WeatherApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
    modules = {
        ApplicationModule.class,
        WeatherApiModule.class,
        LocationModule.class
    }
)
public interface ApplicationComponent {

  /**
   * An initializer that creates the graph from an application.
   */
  final class Initializer {
    static public ApplicationComponent init(BeautifulWeatherApplication application) {
      return DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(application))
          .locationModule(new LocationModule(application))
          .build();
    }

    // No instances
    private Initializer() {
    }
  }

  Application getApplication();

  WeatherService weatherService();

  LocationDealer locationDealer();

  CityNameDealer cityNameDealer();

  void inject(BeautifulWeatherApplication commerceApplication);

}
