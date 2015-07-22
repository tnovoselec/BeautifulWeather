package com.tnovoselec.beautifulweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tnovoselec.beautifulweather.mock.MockDataProvider;
import com.tnovoselec.beautifulweather.ui.SectionChoreographer;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_container)
    ViewGroup mainContainer;

    @Bind(R.id.morning_view)
    DaySectionView morningView;

    @Bind(R.id.day_view)
    DaySectionView dayView;

    @Bind(R.id.evening_view)
    DaySectionView eveningView;

    @Bind(R.id.night_view)
    DaySectionView nightView;

    private SectionChoreographer sectionChoreographer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fillData();
        sectionChoreographer = new SectionChoreographer(mainContainer, Arrays.asList(morningView, dayView, eveningView, nightView));
        sectionChoreographer.initialize();
    }

    private void fillData() {
        morningView.setDaySectionData(MockDataProvider.getMorningData());
        dayView.setDaySectionData(MockDataProvider.getDayData());
        eveningView.setDaySectionData(MockDataProvider.getEveningData());
        nightView.setDaySectionData(MockDataProvider.getNightData());
    }

    @OnClick({R.id.morning_view, R.id.day_view, R.id.evening_view, R.id.night_view})
    public void onSectionClick(View sectionView) {
        sectionChoreographer.onSectionClicked(sectionView);
    }
}
