package com.tnovoselec.beautifulweather.di.module;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;
import com.tnovoselec.beautifulweather.BuildConfig;
import com.tnovoselec.beautifulweather.api.WeatherApi;
import com.tnovoselec.beautifulweather.api.WeatherService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module
public class WeatherApiModule {

  private static final String BASE_URL = " http://api.openweathermap.org";

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient() {
    OkHttpClient okHttpClient = new OkHttpClient();
    okHttpClient.setConnectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
    okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);
    return okHttpClient;
  }

  @Provides
  @Singleton
  RestAdapter provideRestAdapter(Application application, OkHttpClient okHttpClient) {
    RestAdapter.Builder builder = new RestAdapter.Builder();
    builder.setClient(new OkClient(okHttpClient))
        .setEndpoint(BASE_URL);

    if (BuildConfig.DEBUG) {
      builder.setLogLevel(RestAdapter.LogLevel.FULL);
    }

    return builder.build();
  }

  @Provides
  @Singleton
  WeatherApi provideWeatherApi(RestAdapter restAdapter) {
    return restAdapter.create(WeatherApi.class);
  }

  @Provides
  WeatherService provideWeatherService(WeatherApi weatherApi){
    return new WeatherService(weatherApi);
  }
}
