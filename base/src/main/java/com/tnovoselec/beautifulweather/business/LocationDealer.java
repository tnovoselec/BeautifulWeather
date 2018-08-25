package com.tnovoselec.beautifulweather.business;

import android.location.Location;

import com.tnovoselec.beautifulweather.BeautifulWeatherApplication;

import io.reactivex.Observable;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

public class LocationDealer {

  private static final LocationDealer INSTANCE = new LocationDealer();

  private ReactiveLocationProvider locationProvider;
  private Observable<Location> lastKnownLocationObservable;

  public static LocationDealer getInstance(){
    return INSTANCE;
  }

  private LocationDealer() {
    locationProvider = new ReactiveLocationProvider(BeautifulWeatherApplication.applicationContext);
  }

  public Observable<Location> getLastKnownLocationObservable(){
    lastKnownLocationObservable = locationProvider.getLastKnownLocation();
    return lastKnownLocationObservable;
  }


}
