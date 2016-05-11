package com.tnovoselec.beautifulweather.di.module;

import com.tnovoselec.beautifulweather.activity.BaseActivity;

import dagger.Module;

@Module
public class ActivityModule {

  private final BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }
}
