package com.tnovoselec.beautifulweather.api;

import android.util.Log;

import com.tnovoselec.beautifulweather.api.model.HourlyForecast;

import retrofit.RestAdapter;
import rx.Observable;

public class WeatherService {

  private static final String BASE_URL = " http://api.openweathermap.org";

  private WeatherApi weatherApi;

  private static final WeatherService INSTANCE = new WeatherService();

  public static WeatherService getInstance() {
    return INSTANCE;
  }

  private WeatherService() {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(BASE_URL)
        .build();
    weatherApi = restAdapter.create(WeatherApi.class);
  }

  public Observable<HourlyForecast> getForecast(double latitude, double longitude) {
    Log.e("getForecast", "latitude: " + latitude + " longitude: " + longitude);
    return weatherApi.getForecast(latitude, longitude);
  }

}
