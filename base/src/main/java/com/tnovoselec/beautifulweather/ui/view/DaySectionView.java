package com.tnovoselec.beautifulweather.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tnovoselec.beautifulweather.base.R;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.util.VisualUtils;

import static com.tnovoselec.beautifulweather.ui.util.FormatUtils.formatDescription;

public class DaySectionView extends LinearLayout {

    private TextView name;
    private TextView description;
    private TextView temperature;
    private TextView wind;
    private TextView humidity;
    private WeatherIconView icon;
    private View animationDataContainer;

    private DaySectionData daySectionData;

    private Interpolator accelerateInterpolator = new AccelerateInterpolator();
    private Interpolator decelerateInterpolator = new DecelerateInterpolator();

    public DaySectionView(Context context) {
        this(context, null);
    }

    public DaySectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaySectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        name = findViewById(R.id.day_section_name);
        description = findViewById(R.id.day_section_description);
        temperature = findViewById(R.id.day_section_temperature);
        wind = findViewById(R.id.day_section_wind);
        humidity = findViewById(R.id.day_section_humidity);
        icon = findViewById(R.id.day_section_icon);
        animationDataContainer = findViewById(R.id.animation_data_container);

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
        this.description.setText(formatDescription(daySectionData.getDescription()));
        this.temperature.setText(Math.round(daySectionData.getTemperature()) + "° C");
        this.wind.setText("Wind: " + Math.round(daySectionData.getWind()));
        this.humidity.setText("Humidity: " + Math.round(daySectionData.getHumidity()));
        this.setBackgroundColor(getResources().getColor(daySectionData.getBackground()));
        this.icon.setIconViewEnum(daySectionData.getIconViewEnum());
    }

    public void hideIcon() {
        icon.setVisibility(INVISIBLE);
    }

    public void showIcon() {
        icon.setVisibility(VISIBLE);
    }

    public void animateDataContainer() {
        animationDataContainer.setTranslationY(this.getHeight());
        animationDataContainer.setVisibility(VISIBLE);
        animationDataContainer.animate().translationY(0).setDuration(300).setInterpolator(decelerateInterpolator);
    }

    public void hideDataContainer() {
        animationDataContainer.setVisibility(GONE);
    }

    public void animateIcon(boolean up, final boolean isCurrent) {

        final float from = isCurrent ? 0 : up ? icon.getHeight() : -icon.getHeight();
        float to = isCurrent ? up ? -icon.getHeight() : icon.getHeight() : 0;
        Interpolator interpolator = isCurrent ? accelerateInterpolator : decelerateInterpolator;

        icon.setTranslationY(from);
        icon.setVisibility(VISIBLE);
        icon.animate()
            .translationY(to)
            .setDuration(250)
            .setInterpolator(interpolator)
            .setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isCurrent) {
                        hideIcon();
                    }
                }
            });
    }
}
