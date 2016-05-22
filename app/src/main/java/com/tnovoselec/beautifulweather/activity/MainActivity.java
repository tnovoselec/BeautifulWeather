package com.tnovoselec.beautifulweather.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.api.WeatherService;
import com.tnovoselec.beautifulweather.business.LocationDealer;
import com.tnovoselec.beautifulweather.business.ModelConverter;
import com.tnovoselec.beautifulweather.di.ActivityComponent;
import com.tnovoselec.beautifulweather.model.DayData;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.SectionChoreographer;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;
import com.tnovoselec.beautifulweather.ui.view.WeatherIconView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.tnovoselec.beautifulweather.ui.IconViewEnum.SUN;

public class MainActivity extends BaseActivity {

  @Bind(R.id.forecast_container)
  View forecastContainer;

  @Bind(R.id.list_container)
  ViewGroup listContainer;

  @Bind(R.id.first_view)
  DaySectionView firstView;

  @Bind(R.id.second_view)
  DaySectionView secondView;

  @Bind(R.id.third_view)
  DaySectionView thirdView;

  @Bind(R.id.fourth_view)
  DaySectionView fourthView;

  @Bind(R.id.progress_container)
  View progressContainer;

  @Bind(R.id.loader_icon)
  WeatherIconView loaderIcon;

  @Bind(R.id.city_name)
  TextView cityName;

  @Inject
  WeatherService weatherService;

  @Inject
  LocationDealer locationDealer;

  private CompositeSubscription subscriptions = new CompositeSubscription();

  private SectionChoreographer sectionChoreographer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    sectionChoreographer = new SectionChoreographer(listContainer, Arrays.asList(firstView, secondView, thirdView, fourthView));
    sectionChoreographer.initialize();
  }

  @Override
  protected void inject(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    subscriptions = new CompositeSubscription();
    loaderIcon.postDelayed(() -> loaderIcon.setIconViewEnum(SUN), 100);
    showProgress();
    getData();
  }

  private void getData() {
    Subscription subscription = locationDealer.getLocationUpdatesObservable()
        .flatMap(location -> weatherService.getForecast(location.getLatitude(), location.getLongitude()))
        .map(ModelConverter::fromHourlyForecast)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(MainActivity.this::fillData, Throwable::printStackTrace);
    subscriptions.add(subscription);
  }

  @Override
  protected void onPause() {
    super.onPause();
    subscriptions.unsubscribe();
  }

  private void fillData(DayData dayData) {
    List<DaySectionData> daySections = dayData.getDaySections();

    firstView.setDaySectionData(daySections.get(0));
    secondView.setDaySectionData(daySections.get(1));
    thirdView.setDaySectionData(daySections.get(2));
    fourthView.setDaySectionData(daySections.get(3));
    forecastContainer.setVisibility(View.VISIBLE);

    cityName.setText(dayData.getCity().getName());

    hideProgress();
  }

  private void showProgress() {
    progressContainer
        .animate()
        .alpha(1)
        .setInterpolator(new DecelerateInterpolator())
        .withStartAction(() -> progressContainer.setVisibility(View.VISIBLE));
  }

  private void hideProgress() {
    progressContainer
        .animate()
        .alpha(0)
        .setInterpolator(new DecelerateInterpolator())
        .withEndAction(() -> progressContainer.setVisibility(View.GONE));
  }

  @OnClick({R.id.first_view, R.id.second_view, R.id.third_view, R.id.fourth_view})
  public void onSectionClick(View sectionView) {
    sectionChoreographer.onSectionClicked(sectionView);
  }
}
