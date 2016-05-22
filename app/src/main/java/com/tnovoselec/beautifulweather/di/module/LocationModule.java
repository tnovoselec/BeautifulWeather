package com.tnovoselec.beautifulweather.di.module;

import android.app.Application;
import android.location.Geocoder;

import com.tnovoselec.beautifulweather.business.CityNameDealer;
import com.tnovoselec.beautifulweather.business.LocationDealer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class LocationModule {

  private Application application;

  public LocationModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  public ReactiveLocationProvider provideReactiveLocationProvider() {
    return new ReactiveLocationProvider(application);
  }

  @Provides
  @Singleton
  public LocationDealer provideLocationDealer(ReactiveLocationProvider reactiveLocationProvider) {
    return new LocationDealer(reactiveLocationProvider);
  }

  @Provides
  @Singleton
  public Geocoder provideGeocoder() {
    return new Geocoder(application);
  }

  @Provides
  @Singleton
  public CityNameDealer provideCityNameDealer(Geocoder geocoder){
    return new CityNameDealer(geocoder);
  }

}
