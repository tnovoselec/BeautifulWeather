package com.tnovoselec.beautifulweather.model;

import java.util.List;

public class DayData {

    private final CityData city;
    private final List<DaySectionData> daySections;

    public DayData(CityData city, List<DaySectionData> daySections) {
        this.city = city;
        this.daySections = daySections;
    }

    public CityData getCity() {
        return city;
    }

    public List<DaySectionData> getDaySections() {
        return daySections;
    }
}
