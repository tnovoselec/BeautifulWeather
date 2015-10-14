package com.tnovoselec.beautifulweather.api;

import com.tnovoselec.beautifulweather.api.model.HourlyForecast;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherApi {

  String API_KEY = "b8a9c2bc8b76cd26c47294f2169c26d0";

  @GET("/data/2.5/forecast/?units=metric&APPID=" + API_KEY)
  Observable<HourlyForecast> getForecast(@Query("lat") double latitude, @Query("lon") double longitude);
}
