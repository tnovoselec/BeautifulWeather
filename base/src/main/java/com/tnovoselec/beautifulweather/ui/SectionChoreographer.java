package com.tnovoselec.beautifulweather.ui;


import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.tnovoselec.beautifulweather.ui.view.DaySectionView;

import java.util.List;

public class SectionChoreographer {

  private static final String TAG = SectionChoreographer.class.getSimpleName();

  private static final int DEFAULT_SELECTION = 0;
  private static final int DELAY_INTERVAL = 250;

  private int defaultTranslationY;

  private final List<DaySectionView> daySectionViews;

  private final ViewGroup mainContainer;

  private int currentSelection = DEFAULT_SELECTION;

  private Handler handler = new Handler();

  public SectionChoreographer(ViewGroup mainContainer, List<DaySectionView> daySectionViews) {
    this.mainContainer = mainContainer;
    this.daySectionViews = daySectionViews;
  }

  public void initialize() {
    if (daySectionViews.isEmpty()) {
      return;
    }

    mainContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        mainContainer.getViewTreeObserver().removeOnPreDrawListener(this);
        defaultTranslationY = mainContainer.getMeasuredHeight() / 2;
        Log.e(TAG, "defaultTranslation: " + defaultTranslationY);
        setUpChildren();
        return true;
      }
    });
  }

  private void setUpChildren() {
    for (int i = 1; i < daySectionViews.size(); i++) {
      DaySectionView daySectionView = daySectionViews.get(i);
      float translation = (i - 1) * defaultTranslationY / 3 + defaultTranslationY;
      daySectionView.setTranslationY(translation);
      daySectionView.hideIcon();
      daySectionView.hideDataContainer();
    }
  }

  public void onSectionClicked(View view) {
    DaySectionView sectionView = (DaySectionView) view;
    final int index = daySectionViews.indexOf(sectionView);

    if (index == currentSelection) {
      return;
    }
    if (index < currentSelection) {
      shift(index + 1, currentSelection, false);
    } else if (index > currentSelection) {
      shift(currentSelection + 1, index, true);
    }

    daySectionViews.get(currentSelection).animateIcon(index < currentSelection, true);
    final int currentCopy = currentSelection;
    handler.postDelayed(() -> daySectionViews.get(index).animateIcon(index < currentCopy, false), DELAY_INTERVAL);

    handler.postDelayed(() -> {
      daySectionViews.get(index).animateDataContainer();
      daySectionViews.get(currentCopy).hideDataContainer();
    }, 2 * DELAY_INTERVAL);
    currentSelection = index;

  }

  private void shift(int from, int to, boolean up) {
    for (int i = from; i <= to; i++) {

      DaySectionView daySectionView = daySectionViews.get(i);
      float translationStep = i * defaultTranslationY / 3;
      daySectionView
          .animate()
          .translationY(up ? translationStep : translationStep + defaultTranslationY * 2 / 3)
          .setInterpolator(new AccelerateDecelerateInterpolator())
          .setDuration(2 * DELAY_INTERVAL);
    }
  }


}
