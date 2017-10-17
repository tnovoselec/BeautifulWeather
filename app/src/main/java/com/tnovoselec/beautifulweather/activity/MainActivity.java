package com.tnovoselec.beautifulweather.activity;

import static com.tnovoselec.beautifulweather.ui.IconViewEnum.SUN;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.api.WeatherService;
import com.tnovoselec.beautifulweather.business.CityNameDealer;
import com.tnovoselec.beautifulweather.business.LocationDealer;
import com.tnovoselec.beautifulweather.business.ModelConverter;
import com.tnovoselec.beautifulweather.di.ActivityComponent;
import com.tnovoselec.beautifulweather.model.DayData;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.SectionChoreographer;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;
import com.tnovoselec.beautifulweather.ui.view.WeatherIconView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity {

  private static final int LOCATION_PERMISSION_REQUEST = 123;

  @BindView(R.id.forecast_container)
  View forecastContainer;

  @BindView(R.id.list_container)
  ViewGroup listContainer;

  @BindView(R.id.first_view)
  DaySectionView firstView;

  @BindView(R.id.second_view)
  DaySectionView secondView;

  @BindView(R.id.third_view)
  DaySectionView thirdView;

  @BindView(R.id.fourth_view)
  DaySectionView fourthView;

  @BindView(R.id.progress_container)
  View progressContainer;

  @BindView(R.id.loader_icon)
  WeatherIconView loaderIcon;

  @BindView(R.id.city_name)
  TextView cityName;

  @Inject
  WeatherService weatherService;

  @Inject
  LocationDealer locationDealer;

  @Inject
  CityNameDealer cityNameDealer;

  private CompositeSubscription subscriptions = new CompositeSubscription();

  private SectionChoreographer sectionChoreographer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this, this);

    sectionChoreographer = new SectionChoreographer(listContainer,
        Arrays.asList(firstView, secondView, thirdView, fourthView));
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
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

      ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
          LOCATION_PERMISSION_REQUEST);

    } else {
      Subscription subscription = locationDealer.getLocationUpdatesObservable()
          .flatMap(location -> cityNameDealer.getCityName(location))
          .flatMap(location -> weatherService.getForecast(location))
          .map(ModelConverter::fromHourlyForecast)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(MainActivity.this::fillData, Throwable::printStackTrace);
      addSubscription(subscription);
    }

  }

  @Override
  protected void onPause() {
    super.onPause();
    subscriptions.unsubscribe();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      getData();
    } else {

      Toast.makeText(this, "No location, no app :( ", Toast.LENGTH_SHORT).show();
    }
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

  private void addSubscription(Subscription subscription) {
    if (subscriptions == null || subscriptions.isUnsubscribed()) {
      subscriptions = new CompositeSubscription();
    }
    subscriptions.add(subscription);
  }
}
