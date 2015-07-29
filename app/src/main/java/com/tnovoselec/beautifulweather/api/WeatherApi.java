package com.tnovoselec.beautifulweather.api;

import android.location.Location;

import com.tnovoselec.beautifulweather.api.model.HourlyForecast;

import retrofit.http.GET;
import rx.Observable;

public interface WeatherApi {


  @GET("/data/2.5/forecast/?id=%s&units=metric")
  Observable<HourlyForecast> getForecast(float latitude, float longitude);
}
