package com.tnovoselec.beautifulweather.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class SunPainter implements IconPainter {

  private Paint paint;
  private int screenW;
  private int screenH;
  private float X;
  private float Y;
  private Path path;
  private Path path1;
  private double count;
  int strokeColor;
  int bgColor;


  @Override
  public void init(int strokeColor, int bgColor) {
    this.strokeColor = strokeColor;
    this.bgColor = bgColor;

    X = screenW;
    Y = (screenH);

    count = 0;
    paint = new Paint();
    paint.setColor(strokeColor);

    paint.setAntiAlias(true);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStyle(Paint.Style.STROKE);
    paint.setShadowLayer(0, 0, 0, Color.BLACK);

    path = new Path();
    path1 = new Path();
  }

  @Override
  public void paint(Canvas canvas) {
    canvas.drawColor(bgColor);

    // initializing paths
    path.reset();
    path1.reset();
    // set stroke width
    paint.setStrokeWidth((float) (0.02083 * screenW));

    //incrementing counter for rotation
    count = count + 1.5;


    // drawing center circle
    path.addCircle(X, Y, (int) (0.20 * screenW), Path.Direction.CW);

    // drawing arms of sun
    for (int i = 0; i < 360; i += 45) {
      path1.moveTo(X, Y);
      float x1 = (float) ((int) (0.2858 * screenW) * Math.cos(Math.toRadians(i + count)) + X); //arm pointX at radius 50 with incrementing angle from center of sun
      float y1 = (float) ((int) (0.2858 * screenW) * Math.sin(Math.toRadians(i + count)) + Y);//arm pointY at radius 50 with incrementing angle from center of sun
      float X2 = (float) ((int) (0.3875 * screenW) * Math.cos(Math.toRadians(i + count)) + X);//arm pointX at radius 65 with incrementing angle from center of sun
      float Y2 = (float) ((int) (0.3875 * screenW) * Math.sin(Math.toRadians(i + count)) + Y);//arm pointY at radius 65 with incrementing angle from center of sun
      path1.moveTo(x1, y1); // draw arms of sun
      path1.lineTo(X2, Y2);

    }

    // drawing paths on canvas
    canvas.drawPath(path, paint);
    canvas.drawPath(path1, paint);

  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    screenW = w; //getting Screen Width
    screenH = h;// getting Screen Height

    // center point  of Screen
    X = screenW / 2;
    Y = (screenH / 2);

    path.moveTo(X, Y);

  }
}
