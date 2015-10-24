package com.tnovoselec.beautifulweather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.api.WeatherService;
import com.tnovoselec.beautifulweather.business.LocationDealer;
import com.tnovoselec.beautifulweather.business.ModelConverter;
import com.tnovoselec.beautifulweather.model.DayData;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.SectionChoreographer;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;
import com.tnovoselec.beautifulweather.ui.view.WeatherIconView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.tnovoselec.beautifulweather.ui.IconViewEnum.SUN;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onResume() {
        super.onResume();
        loaderIcon.postDelayed(() -> loaderIcon.setIconViewEnum(SUN), 100);
    }

    private void getData() {
        Subscription subscription = locationDealer.getLastKnownLocationObservable()
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
        subscriptions.unsubscribe();
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

    @OnClick({R.id.first_view, R.id.second_view, R.id.third_view, R.id.fourth_view})
    public void onSectionClick(View sectionView) {
        sectionChoreographer.onSectionClicked(sectionView);
    }
}
