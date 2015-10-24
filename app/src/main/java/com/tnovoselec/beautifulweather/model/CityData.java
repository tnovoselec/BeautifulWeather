package com.tnovoselec.beautifulweather.model;

public class CityData {

    private final String name;
    private final String country;

    public CityData(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
