package com.tnovoselec.beautifulweather.mock;

import com.tnovoselec.beautifulweather.R;
import com.tnovoselec.beautifulweather.model.DaySection;
import com.tnovoselec.beautifulweather.model.DaySectionData;
import com.tnovoselec.beautifulweather.ui.IconViewEnum;


public class MockDataProvider {

    public static DaySectionData getMorningData() {
        return new DaySectionData(DaySection.MORNING, 10, "Fog", 15.5, 76, R.color.morning_bg, IconViewEnum.SUN);
    }

    public static DaySectionData getDayData() {
        return new DaySectionData(DaySection.DAY, 30, "Sunny", 5.5, 76, R.color.day_bg, IconViewEnum.SUN);
    }

    public static DaySectionData getEveningData() {
        return new DaySectionData(DaySection.EVENING, 12, "Mist", 5.5, 76, R.color.evening_bg, IconViewEnum.CLOUD);
    }

    public static DaySectionData getNightData() {
        return new DaySectionData(DaySection.NIGHT, 8, "Twilight", 15.5, 76, R.color.night_bg, IconViewEnum.MOON);
    }
}
