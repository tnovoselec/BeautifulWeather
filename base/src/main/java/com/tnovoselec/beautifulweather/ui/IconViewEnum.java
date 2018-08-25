package com.tnovoselec.beautifulweather.ui;

public enum IconViewEnum {
  SUN(0), MOON(1), CLOUD(2), WIND(3), THUNDERSTORM(4), RAIN(5), SNOW(6), MIST(7);

  private int value;

  IconViewEnum(int value) {
    this.value = value;
  }

  public static IconViewEnum fromValue(int value){
    for (IconViewEnum iconViewEnum : values()){
      if (value == iconViewEnum.value){
        return iconViewEnum;
      }
    }
    throw new RuntimeException();
  }
}
