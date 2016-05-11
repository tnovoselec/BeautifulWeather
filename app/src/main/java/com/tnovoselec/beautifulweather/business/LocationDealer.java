package com.tnovoselec.beautifulweather.business;

import android.location.Location;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

public class LocationDealer {

  private ReactiveLocationProvider locationProvider;
  private Observable<Location> lastKnownLocationObservable;
  private Observable<Location> locationUpdatesObservable;

  public LocationDealer(ReactiveLocationProvider locationProvider) {
    this.locationProvider = locationProvider;
  }

  public Observable<Location> getLastKnownLocationObservable(){
    lastKnownLocationObservable = locationProvider.getLastKnownLocation();
    return lastKnownLocationObservable;
  }

}
