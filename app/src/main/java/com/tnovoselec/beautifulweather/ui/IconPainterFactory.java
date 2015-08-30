package com.tnovoselec.beautifulweather.ui;

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
