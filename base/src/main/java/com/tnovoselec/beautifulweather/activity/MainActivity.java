package com.tnovoselec.beautifulweather.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.tnovoselec.beautifulweather.api.WeatherService;
import com.tnovoselec.beautifulweather.base.R;
import com.tnovoselec.beautifulweather.business.LocationDealer;
import com.tnovoselec.beautifulweather.business.ModelConverter;
import com.tnovoselec.beautifulweather.model.DayData;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.SectionChoreographer;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;
import com.tnovoselec.beautifulweather.ui.view.WeatherIconView;

import java.util.Arrays;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.tnovoselec.beautifulweather.ui.IconViewEnum.SUN;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private View forecastContainer;
    private ViewGroup listContainer;
    private DaySectionView firstView;
    private DaySectionView secondView;
    private DaySectionView thirdView;
    private DaySectionView fourthView;
    private View progressContainer;
    private WeatherIconView loaderIcon;
    private TextView cityName;

    private WeatherService weatherService = WeatherService.getInstance();

    private LocationDealer locationDealer = LocationDealer.getInstance();

    private CompositeDisposable subscriptions = new CompositeDisposable();

    private SectionChoreographer sectionChoreographer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastContainer = findViewById(R.id.forecast_container);
        listContainer = findViewById(R.id.list_container);
        firstView = findViewById(R.id.first_view);
        secondView = findViewById(R.id.second_view);
        thirdView = findViewById(R.id.third_view);
        fourthView = findViewById(R.id.fourth_view);
        progressContainer = findViewById(R.id.progress_container);
        loaderIcon = findViewById(R.id.loader_icon);
        cityName = findViewById(R.id.city_name);

        setOnClickListeners();
        checkLocationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaderIcon.postDelayed(() -> loaderIcon.setIconViewEnum(SUN), 100);
    }

    private void getData() {
        Disposable subscription = locationDealer.getLastKnownLocationObservable()
                                                  .flatMap(location -> weatherService.getForecast(location.getLatitude(), location.getLongitude()))
                                                  .map(ModelConverter::fromHourlyForecast)
                                                  .subscribeOn(Schedulers.io())
                                                  .observeOn(AndroidSchedulers.mainThread())
                                                  .subscribe(MainActivity.this::fillData, Throwable::printStackTrace);
        subscriptions.add(subscription);
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.dispose();
    }

    private void fillData(DayData dayData) {
        List<DaySectionData> daySectionDatas = dayData.getDaySections();
        Log.e("fillData", "size: " + daySectionDatas.size());
        sectionChoreographer = new SectionChoreographer(listContainer, Arrays.asList(firstView, secondView, thirdView, fourthView));
        sectionChoreographer.initialize();

        firstView.setDaySectionData(daySectionDatas.get(0));
        secondView.setDaySectionData(daySectionDatas.get(1));
        thirdView.setDaySectionData(daySectionDatas.get(2));
        fourthView.setDaySectionData(daySectionDatas.get(3));
        forecastContainer.setVisibility(View.VISIBLE);

        cityName.setText(dayData.getCity().getName());

        progressContainer
                .animate()
                .setStartDelay(2000)
                .alpha(0)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> progressContainer.setVisibility(View.GONE));
    }

    private void setOnClickListeners() {
        firstView.setOnClickListener(this::onSectionClick);
        secondView.setOnClickListener(this::onSectionClick);
        thirdView.setOnClickListener(this::onSectionClick);
        fourthView.setOnClickListener(this::onSectionClick);
    }

    private void onSectionClick(View sectionView) {
        sectionChoreographer.onSectionClicked(sectionView);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                                              new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                              MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            getData();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        getData();
                    }
                }
            }
        }
    }
}
