package com.tnovoselec.beautifulweather.ui;

import com.tnovoselec.beautifulweather.ui.painter.CloudPainter;
import com.tnovoselec.beautifulweather.ui.painter.IconPainter;
import com.tnovoselec.beautifulweather.ui.painter.MoonPainter;
import com.tnovoselec.beautifulweather.ui.painter.RainPainter;
import com.tnovoselec.beautifulweather.ui.painter.SunPainter;
import com.tnovoselec.beautifulweather.ui.painter.ThunderPainter;

public class IconPainterFactory {

  public static IconPainter createPainter(IconViewEnum iconViewEnum) {
    switch (iconViewEnum) {
      case SUN:
        return new SunPainter();
      case MOON:
        return new MoonPainter();
      case CLOUD:
        return new CloudPainter();
      case RAIN:
        return new RainPainter();
      case THUNDERSTORM:
        return new ThunderPainter();
      default:
        throw new RuntimeException();
    }
  }
}
