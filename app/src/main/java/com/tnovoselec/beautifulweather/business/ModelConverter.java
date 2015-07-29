package com.tnovoselec.beautifulweather.business;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.api.model.HourlyForecast;
import com.tnovoselec.beautifulweather.model.DaySection;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.IconViewEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 28.7.2015..
 */
public class ModelConverter {

    public static List<DaySectionData> fromHourlyForecast(HourlyForecast hourlyForecast){
        List<DaySectionData> daySections = new ArrayList<>();
        for (HourlyForecast.Forecast forecast : hourlyForecast.getForecasts()){

        }

        return daySections;
    }

    private static DaySectionData fromForecast(HourlyForecast.Forecast forecast){
        return new DaySectionData(DaySection.NIGHT, 8, "Twilight", 15.5, 76, R.color.night_bg, IconViewEnum.MOON);
    }
}
