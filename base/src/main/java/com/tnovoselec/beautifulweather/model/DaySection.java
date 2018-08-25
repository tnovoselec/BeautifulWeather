package com.tnovoselec.beautifulweather.model;

public enum DaySection {
    MORNING("MORNING"), DAY("DAY"), EVENING("EVENING"), NIGHT("NIGHT");

    private String description;

    DaySection(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
