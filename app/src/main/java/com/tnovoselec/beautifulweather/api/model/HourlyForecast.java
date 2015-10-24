package com.tnovoselec.beautifulweather.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecast {

  @SerializedName("list")
  private ArrayList<Forecast> forecasts;

  @SerializedName("city")
  private City city;

  public List<Forecast> getForecasts() {
    return forecasts;
  }

  public City getCity() {
    return city;
  }

  public class Forecast {

    private int cityId;

    @SerializedName("dt")
    private long timestamp;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    public int getCityId() {
      return cityId;
    }

    public void setCityId(int cityId) {
      this.cityId = cityId;
    }

    public long getTimestamp() {
      return timestamp * 1000;
    }

    public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
    }

    public Main getMain() {
      return main;
    }

    public void setMain(Main main) {
      this.main = main;
    }

    public List<Weather> getWeather() {
      return weather;
    }

    public Weather getFirstWeather() {
      return this.weather != null ? this.weather.get(0) : null;
    }

    public void setWeather(List<Weather> weather) {
      this.weather = weather;
    }

    public Wind getWind() {
      return wind;
    }

    public void setWind(Wind wind) {
      this.wind = wind;
    }

    public Clouds getClouds() {
      return clouds;
    }

    public void setClouds(Clouds clouds) {
      this.clouds = clouds;
    }


    public class Main {

      @SerializedName("temp")
      private float temp;

      @SerializedName("temp_min")
      private float tempMin;

      @SerializedName("temp_max")
      private float tempMax;

      @SerializedName("pressure")
      private float pressure;

      @SerializedName("sea_level")
      private float seaLevel;

      @SerializedName("grnd_level")
      private float groundLevel;

      @SerializedName("humidity")
      private float humidity;

      @SerializedName("temp_kf")
      private float tempKf;

      public float getTemp() {
        return temp;
      }

      public void setTemp(float temp) {
        this.temp = temp;
      }

      public float getTempMin() {
        return tempMin;
      }

      public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
      }

      public float getTempMax() {
        return tempMax;
      }

      public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
      }

      public float getPressure() {
        return pressure;
      }

      public void setPressure(float pressure) {
        this.pressure = pressure;
      }

      public float getSeaLevel() {
        return seaLevel;
      }

      public void setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
      }

      public float getGroundLevel() {
        return groundLevel;
      }

      public void setGroundLevel(float groundLevel) {
        this.groundLevel = groundLevel;
      }

      public float getHumidity() {
        return humidity;
      }

      public void setHumidity(float humidity) {
        this.humidity = humidity;
      }

      public float getTempKf() {
        return tempKf;
      }

      public void setTempKf(float tempKf) {
        this.tempKf = tempKf;
      }

    }

    public class Weather {

      @SerializedName("id")
      private int id;

      @SerializedName("icon")
      private String icon;

      @SerializedName("description")
      private String description;

      @SerializedName("main")
      private String main;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public String getIcon() {
        return icon;
      }

      public void setIcon(String icon) {
        this.icon = icon;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }

      public String getMain() {
        return main;
      }

      public void setMain(String main) {
        this.main = main;
      }
    }

    public class Clouds {
      @SerializedName("all")
      private float all;

      public float getAll() {
        return all;
      }

      public void setAll(float all) {
        this.all = all;
      }

    }

    public class Wind {

      @SerializedName("speed")
      private float speed;

      @SerializedName("deg")
      private float deg;

      public float getSpeed() {
        return speed;
      }

      public void setSpeed(float speed) {
        this.speed = speed;
      }

      public float getDeg() {
        return deg;
      }

      public void setDeg(float deg) {
        this.deg = deg;
      }

    }
  }

  public class City {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    public long getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getCountry() {
      return country;
    }
  }

}
