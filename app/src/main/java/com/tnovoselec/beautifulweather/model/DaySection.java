package com.tnovoselec.beautifulweather.model;

public enum DaySection {
    MORNING("Morning"), DAY("Day"), EVENING("Evening"), NIGHT("Night");

    private String description;

    DaySection(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
