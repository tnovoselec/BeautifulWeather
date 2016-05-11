package com.tnovoselec.beautifulweather.api;

import android.util.Log;

import com.tnovoselec.beautifulweather.api.model.HourlyForecast;

import rx.Observable;

public class WeatherService {

  private WeatherApi weatherApi;

  public WeatherService(WeatherApi weatherApi) {
    this.weatherApi = weatherApi;
  }

  public Observable<HourlyForecast> getForecast(double latitude, double longitude) {
    Log.e("getForecast", "latitude: " + latitude + " longitude: " + longitude);
    return weatherApi.getForecast(latitude, longitude);
  }

}
