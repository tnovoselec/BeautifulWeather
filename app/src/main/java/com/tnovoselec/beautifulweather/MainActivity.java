package com.tnovoselec.beautifulweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tnovoselec.beautifulweather.api.WeatherService;
import com.tnovoselec.beautifulweather.business.LocationDealer;
import com.tnovoselec.beautifulweather.business.ModelConverter;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.SectionChoreographer;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.main_container)
  ViewGroup mainContainer;

  @Bind(R.id.first_view)
  DaySectionView firstView;

  @Bind(R.id.second_view)
  DaySectionView secondView;

  @Bind(R.id.third_view)
  DaySectionView thirdView;

  @Bind(R.id.fourth_view)
  DaySectionView fourthView;

  private WeatherService weatherService = WeatherService.getInstance();

  private LocationDealer locationDealer = LocationDealer.getInstance();

  private CompositeSubscription subscriptions = new CompositeSubscription();

  private SectionChoreographer sectionChoreographer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
    getData();
  }


  private void getData() {
    Subscription subscription = locationDealer.getLastKnownLocationObservable()
        .flatMap(location -> weatherService.getForecast(location.getLatitude(), location.getLongitude())).map(ModelConverter::fromHourlyForecast)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(MainActivity.this::fillData, Throwable::printStackTrace);
    subscriptions.add(subscription);
  }

  @Override
  protected void onStop() {
    super.onStop();
    subscriptions.unsubscribe();
  }

  private void fillData(List<DaySectionData> daySectionDatas) {
    Log.e("fillData", "size: " + daySectionDatas.size());
    sectionChoreographer = new SectionChoreographer(mainContainer, Arrays.asList(firstView, secondView, thirdView, fourthView));
    sectionChoreographer.initialize();

    firstView.setDaySectionData(daySectionDatas.get(0));
    secondView.setDaySectionData(daySectionDatas.get(1));
    thirdView.setDaySectionData(daySectionDatas.get(2));
    fourthView.setDaySectionData(daySectionDatas.get(3));
  }

  @OnClick({R.id.first_view, R.id.second_view, R.id.third_view, R.id.fourth_view})
  public void onSectionClick(View sectionView) {
    sectionChoreographer.onSectionClicked(sectionView);
  }
}
