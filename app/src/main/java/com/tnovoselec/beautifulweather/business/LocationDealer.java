package com.tnovoselec.beautifulweather.business;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;

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

  public Observable<Location> getLocationUpdatesObservable(){
    locationUpdatesObservable = locationProvider.getUpdatedLocation(new LocationRequest());
    return locationUpdatesObservable;
  }

}
