package com.tnovoselec.beautifulweather.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
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

  @Bind(R.id.day_section_icon)
  View icon;

  @Bind(R.id.animation_data_container)
  View animationDataContainer;

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
    this.temperature.setText(daySectionData.getTemperature() + "° C");
    this.wind.setText("Wind: E " + daySectionData.getWind());
    this.humidity.setText("Humidity: " + daySectionData.getHumidity());
    this.setBackgroundColor(getResources().getColor(daySectionData.getBackground()));
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

    Log.e("animateIcon", "from: " + from + " to: " + to + " up: " + up + " isCurrent: " + isCurrent);

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
