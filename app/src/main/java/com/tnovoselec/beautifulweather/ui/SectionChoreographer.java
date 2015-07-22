package com.tnovoselec.beautifulweather.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.tnovoselec.beautifulweather.ui.utils.VisualUtils;
import com.tnovoselec.beautifulweather.ui.view.DaySectionView;

import java.util.ArrayList;
import java.util.List;

public class SectionChoreographer {

    private static final int DEFAULT_SELECTION = 0;

    private int defaultTranslationY;

    private final List<DaySectionView> daySectionViews;

    private final ViewGroup mainContainer;

    private int currentSelection = DEFAULT_SELECTION;

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
        }
    }

    public void onSectionClicked(View view) {
        DaySectionView sectionView = (DaySectionView) view;
        int index = daySectionViews.indexOf(sectionView);

        if (index < currentSelection) {
            shift(index + 1, currentSelection, false);
        } else if (index > currentSelection) {
            shift(currentSelection + 1, index, true);
        }
        currentSelection = index;
    }

    private void shift(int from, int to, boolean up) {
        for (int i = from; i <= to; i++) {
            float translationBy = up ? -defaultTranslationY : defaultTranslationY;
            DaySectionView daySectionView = daySectionViews.get(i);
            daySectionView
                    .animate()
                    .translationYBy(translationBy * 2 / 3)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(500);
        }
    }


}
