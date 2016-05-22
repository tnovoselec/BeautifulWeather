package com.tnovoselec.beautifulweather.business;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.List;

import rx.Observable;

public class CityNameDealer {

  private final Geocoder geocoder;

  public CityNameDealer(Geocoder geocoder) {
    this.geocoder = geocoder;
  }

  public Observable<String> getCityName(Location location) {
    try {
      return Observable.just(getAddress(location));
    } catch (Exception e) {
      return Observable.error(e);
    }
  }

  private String getAddress(Location location) throws Exception {
    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
    return addresses.get(0).getLocality() + "," + addresses.get(0).getCountryCode();
  }
}
