package com.tnovoselec.beautifulweather.api;

import com.tnovoselec.beautifulweather.api.model.HourlyForecast;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherApi {


  @GET("/data/2.5/forecast/?units=metric")
  Observable<HourlyForecast> getForecast(@Query("lat") double latitude, @Query("lon") double longitude);
}
