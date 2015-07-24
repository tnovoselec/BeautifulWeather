package com.tnovoselec.beautifulweather.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tnovoselec.beautifulweather.R;

public class SunView extends View {

  private static Paint paint;
  private int screenW, screenH;
  private float X, Y;
  private Path path, path1;
  private double count;
  boolean isStatic;
  boolean isAnimated;
  int strokeColor;
  int bgColor;


  public SunView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SunView);

    // get attributes from layout
    isStatic = a.getBoolean(R.styleable.SunView_isStatic, this.isStatic);
    strokeColor = a.getColor(R.styleable.SunView_strokeColor, this.strokeColor);
    if (strokeColor == 0) {
      strokeColor = Color.BLACK;
    }
    bgColor = a.getColor(R.styleable.SunView_bgColor, this.bgColor);
    if (bgColor == 0) {
      bgColor = Color.TRANSPARENT;
    }

    X = screenW;
    Y = (screenH);

    init();
  }

  private void init() {

    // initialize default values
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
    isAnimated = true;


  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    screenW = w; //getting Screen Width
    screenH = h;// getting Screen Height

    // center point  of Screen
    X = screenW / 2;
    Y = (screenH / 2);

    path.moveTo(X, Y);

  }


  @Override
  public void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // set canvas background color
    canvas.drawColor(bgColor);

    // initializing paths
    path.reset();
    path1.reset();
    // set stroke width
    paint.setStrokeWidth((float) (0.02083 * screenW));

    //incrementing counter for rotation
    count = count + 1.5;

    //comparison to check 360 degrees rotation
    int retval = Double.compare(count, 360.00);

//    if (retval > 0) {
//
//      if (!isAnimated) {
//        // mark completion of animation
//        isAnimated = true;
//        //resetting counter on completion of a rotation
//        count = 0;
//      } else {
//        //resetting counter on completion of a rotation
//        count = 0;
//      }
//    }

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

    if (!isStatic || !isAnimated) {
      // invalidate if not static or not animating
      invalidate();


    }


  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        // nothing to do
        return true;
      case MotionEvent.ACTION_MOVE:
        // nothing to do
        break;
      case MotionEvent.ACTION_UP:
        // start animation if it is not animating
        if (isStatic && isAnimated) {
          isAnimated = false;
        }

        break;
      default:
        return false;
    }

    // Schedules a repaint.
    if (!isAnimated) {
      invalidate();
    }
    return true;
  }
}
