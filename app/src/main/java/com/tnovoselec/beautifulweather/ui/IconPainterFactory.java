package com.tnovoselec.beautifulweather.ui;

public class IconPainterFactory {

  public static IconPainter createPainer(IconViewEnum iconViewEnum) {
    switch (iconViewEnum) {
      case SUN:
        return new SunPainter();
      case MOON:
        return new MoonPainter();
      case CLOUD:
        return new CloudPainter();
      default:
        throw new RuntimeException();
    }
  }
}
