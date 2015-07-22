package com.tnovoselec.beautifulweather.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.utils.VisualUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DaySectionView extends LinearLayout {

    @Bind(R.id.day_section_name)
    TextView name;

    @Bind(R.id.day_section_description)
    TextView description;

    @Bind(R.id.day_section_temperature)
    TextView temperature;

    @Bind(R.id.day_section_wind)
    TextView wind;

    @Bind(R.id.day_section_humidity)
    TextView humidity;

    private DaySectionData daySectionData;

    public DaySectionView(Context context) {
        this(context, null);
    }

    public DaySectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaySectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        ButterKnife.bind(this);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_section, this);
        final int screenHeight = VisualUtils.getScreenHeight();
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.height = screenHeight / 2;
                setLayoutParams(params);
                return true;
            }
        });
    }

    public void setDaySectionData(DaySectionData daySectionData) {
        this.daySectionData = daySectionData;
        fillViews();
    }

    private void fillViews() {
        this.name.setText(daySectionData.getDaySection().getDescription());
        this.description.setText(daySectionData.getDescription());
        this.temperature.setText("" + daySectionData.getTemperature());
        this.wind.setText("" + daySectionData.getWind());
        this.humidity.setText("" + daySectionData.getHumidity());
        this.setBackgroundColor(getResources().getColor(daySectionData.getBackground()));
    }
}
