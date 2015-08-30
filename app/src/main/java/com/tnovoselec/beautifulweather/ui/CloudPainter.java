package com.tnovoselec.beautifulweather.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CloudPainter implements IconPainter {

  private Paint paint;
  private int screenW, screenH;
  private float X, Y;
  private double count;
  Cloud cloud;
  int strokeColor;
  int bgColor;

  @Override
  public void init(int strokeColor, int bgColor) {
    this.strokeColor = strokeColor;
    this.bgColor = bgColor;
    count = 0;

    paint = new Paint();
    paint.setColor(strokeColor);
    paint.setStrokeWidth((screenW / 25));
    paint.setAntiAlias(true);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStyle(Paint.Style.STROKE);
    paint.setShadowLayer(0, 0, 0, Color.BLACK);
  }

  @Override
  public void paint(Canvas canvas) {
    // set canvas background color
    canvas.drawColor(bgColor);

    // set stroke width
    paint.setStrokeWidth((float) (0.02083 * screenW));

    //incrementing counter for rotation

    count = (count + 3.5) % 360;
    canvas.drawPath(cloud.getCloud(X, Y, screenW, count), paint);
  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    screenW = w; //getting Screen Width
    screenH = h; // getting Screen Height

    // center point  of Screen
    X = screenW / 2;
    Y = (screenH / 2);

    cloud = new Cloud();
  }
}
