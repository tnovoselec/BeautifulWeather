package com.tnovoselec.beautifulweather.business;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.api.model.HourlyForecast;
import com.tnovoselec.beautifulweather.model.CityData;
import com.tnovoselec.beautifulweather.model.DayData;
import com.tnovoselec.beautifulweather.model.DaySection;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.IconViewEnum;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.tnovoselec.beautifulweather.ui.IconViewEnum.CLOUD;
import static com.tnovoselec.beautifulweather.ui.IconViewEnum.MIST;
import static com.tnovoselec.beautifulweather.ui.IconViewEnum.MOON;
import static com.tnovoselec.beautifulweather.ui.IconViewEnum.RAIN;
import static com.tnovoselec.beautifulweather.ui.IconViewEnum.SNOW;
import static com.tnovoselec.beautifulweather.ui.IconViewEnum.SUN;
import static com.tnovoselec.beautifulweather.ui.IconViewEnum.THUNDERSTORM;
import static org.joda.time.DateTimeZone.UTC;

public class ModelConverter {

  private static final DateTime CURRENT = new DateTime();
  private static final DateTime MORNING = new DateTime(UTC).withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0);
  private static final DateTime DAY = new DateTime(UTC).withHourOfDay(15).withMinuteOfHour(0).withSecondOfMinute(0);
  private static final DateTime EVENING = new DateTime(UTC).withHourOfDay(21).withMinuteOfHour(0).withSecondOfMinute(0);
  private static final DateTime NIGHT = new DateTime(UTC).withHourOfDay(3).withMinuteOfHour(0).withSecondOfMinute(0);
  private static final long HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);


  public static DayData fromHourlyForecast(HourlyForecast hourlyForecast) {
    List<DaySectionData> daySections = new ArrayList<>();
    for (HourlyForecast.Forecast forecast : hourlyForecast.getForecasts()) {
      if (isMorning(forecast) || isDay(forecast) || isEvening(forecast) || isNight(forecast)) {
        daySections.add(fromForecast(forecast));
      }
    }
    CityData cityData = fromCity(hourlyForecast.getCity());
    return new DayData(cityData, daySections);
  }

  private static DaySectionData fromForecast(HourlyForecast.Forecast forecast) {
    DaySection daySection;
    if (isMorning(forecast)) {
      daySection = DaySection.MORNING;
    } else if (isDay(forecast)) {
      daySection = DaySection.DAY;
    } else if (isEvening(forecast)) {
      daySection = DaySection.EVENING;
    } else {
      daySection = DaySection.NIGHT;
    }
    return new DaySectionData(
        daySection,
        forecast.getMain().getTemp(),
        forecast.getFirstWeather().getDescription(),
        forecast.getWind().getSpeed(),
        forecast.getMain().getHumidity(),
        resolveBackground(daySection),
        resolveIconView(forecast, daySection));
  }

  private static CityData fromCity(HourlyForecast.City city){
    return new CityData(city.getName(), city.getCountry());
  }

  private static boolean isMorning(HourlyForecast.Forecast forecast) {
    return isInRange(forecast, MORNING);
  }

  private static boolean isDay(HourlyForecast.Forecast forecast) {
    return isInRange(forecast, DAY);
  }

  private static boolean isEvening(HourlyForecast.Forecast forecast) {
    return isInRange(forecast, EVENING);
  }

  private static boolean isNight(HourlyForecast.Forecast forecast) {
    return isInRange(forecast, NIGHT);
  }

  private static boolean isInRange(HourlyForecast.Forecast forecast, DateTime dateTime) {
    if (dateTime.compareTo(CURRENT) < 0) {
      dateTime = dateTime.plusDays(1);
//      Log.e("plusDays","plusDays");
    }
    DateTime converted = new DateTime(forecast.getTimestamp());
//    Log.e("timestamps", "dateTime: " + dateTime.getMillis() + " forecast: " + converted.getMillis() + " upper " + (dateTime.getMillis() + HOUR_IN_MILLIS) + " lower: " + (dateTime.getMillis() - HOUR_IN_MILLIS));
    return converted.getMillis() < (dateTime.getMillis() + HOUR_IN_MILLIS) &&
        converted.getMillis() > dateTime.getMillis() - HOUR_IN_MILLIS;
  }

  private static int resolveBackground(DaySection daySection) {
    switch (daySection) {
      case MORNING:
        return R.color.morning_bg;
      case DAY:
        return R.color.day_bg;
      case EVENING:
        return R.color.evening_bg;
      case NIGHT:
        return R.color.night_bg;
      default:
        throw new IllegalStateException("Unknown day section!");
    }
  }

  private static IconViewEnum resolveIconView(HourlyForecast.Forecast forecast, DaySection daySection) {
    int weatherId = forecast.getFirstWeather().getId();

    if (weatherId >= 200 && weatherId < 300) {
      return THUNDERSTORM;
    } else if (weatherId >= 300 && weatherId < 400) {
      return RAIN;
    } else if (weatherId >= 500 && weatherId < 600) {
      return RAIN;
    } else if (weatherId >= 600 && weatherId < 700) {
      return SNOW;
    } else if (weatherId >= 700 && weatherId < 800) {
      return MIST;
    } else if (weatherId == 800 && daySection == DaySection.NIGHT) {
      return MOON;
    } else if (weatherId == 800) {
      return SUN;
    } else if (weatherId > 800 && weatherId <= 900) {
      return CLOUD;
    } else {
      return SUN;
    }
  }
}
