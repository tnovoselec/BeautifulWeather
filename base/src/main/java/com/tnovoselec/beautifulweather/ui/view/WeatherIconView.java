package com.tnovoselec.beautifulweather.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.tnovoselec.beautifulweather.base.R;
import com.tnovoselec.beautifulweather.ui.painter.IconPainter;
import com.tnovoselec.beautifulweather.ui.IconPainterFactory;
import com.tnovoselec.beautifulweather.ui.IconViewEnum;

public class WeatherIconView extends View {

  private IconPainter iconPainter;

  private int strokeColor;
  private int bgColor;
  private int width;
  private int height;

  public WeatherIconView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public WeatherIconView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherIconView);
    strokeColor = typedArray.getColor(R.styleable.WeatherIconView_strokeColor, 0);
    if (strokeColor == 0) {
      strokeColor = Color.BLACK;
    }
    bgColor = typedArray.getColor(R.styleable.WeatherIconView_bgColor, 0);
    if (bgColor == 0) {
      bgColor = Color.TRANSPARENT;
    }
    typedArray.recycle();
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    this.width = w;
    this.height = h;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (iconPainter != null) {
      iconPainter.paint(canvas);
    }
    invalidate();
  }

  public void setIconViewEnum(IconViewEnum iconViewEnum) {
    iconPainter = IconPainterFactory.createPainter(iconViewEnum);
    iconPainter.init(strokeColor, bgColor);
    iconPainter.onSizeChanged(width, height, 0, 0);
    invalidate();
  }
}
