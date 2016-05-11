package com.tnovoselec.beautifulweather.di;

import com.tnovoselec.beautifulweather.activity.BaseActivity;
import com.tnovoselec.beautifulweather.activity.MainActivity;
import com.tnovoselec.beautifulweather.di.module.ActivityModule;
import com.tnovoselec.beautifulweather.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
    dependencies = {
        ApplicationComponent.class
    },
    modules = {
        ActivityModule.class
    }
)
public interface ActivityComponent extends ApplicationComponent{

  final class Initializer {

    static public ActivityComponent init(BaseActivity injectorActivity,
                                         ComponentProvider<ApplicationComponent> applicationComponentProvider) {
      return DaggerActivityComponent.builder()
          .applicationComponent(applicationComponentProvider.getComponent())
          .activityModule(new ActivityModule(injectorActivity))
          .build();
    }

    // No instances
    private Initializer() {
    }
  }

  void inject(MainActivity mainActivity);
}
