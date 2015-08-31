package com.tnovoselec.beautifulweather.ui.painter;

import android.graphics.Canvas;

public interface IconPainter {

  void init(int strokeColor, int bgColor);

  void paint(Canvas canvas);

  void onSizeChanged(int w, int h, int oldw, int oldh);
}
