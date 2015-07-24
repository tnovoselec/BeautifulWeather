package com.tnovoselec.beautifulweather.model;


import com.tnovoselec.beautifulweather.ui.IconViewEnum;

public class DaySectionData {

  private final DaySection daySection;
  private final int temperature;
  private final String description;
  private final double wind;
  private final double humidity;
  private final int background;
  private final IconViewEnum iconViewEnum;

  public DaySectionData(DaySection daySection, int temperature, String description, double wind, double humidity, int background, IconViewEnum iconViewEnum) {
    this.daySection = daySection;
    this.temperature = temperature;
    this.description = description;
    this.wind = wind;
    this.humidity = humidity;
    this.background = background;
    this.iconViewEnum = iconViewEnum;
  }

  public DaySection getDaySection() {
    return daySection;
  }

  public int getTemperature() {
    return temperature;
  }

  public String getDescription() {
    return description;
  }

  public double getWind() {
    return wind;
  }

  public double getHumidity() {
    return humidity;
  }

  public int getBackground() {
    return background;
  }

  public IconViewEnum getIconViewEnum() {
    return iconViewEnum;
  }
}
