package com.blured.coronavirustracker.models;

import java.util.List;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCases;
    private List<Integer> newCasesPerDay;
    private List<Integer> deltaCasesDayBefore;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public List<Integer> getNewCasesPerDay() {
        return newCasesPerDay;
    }

    public void setNewCasesPerDay(List<Integer> newCasesPerDay) {
        this.newCasesPerDay = newCasesPerDay;
    }

    public List<Integer> getDeltaCasesDayBefore() {
        return deltaCasesDayBefore;
    }

    public void setDeltaCasesDayBefore(List<Integer> deltaCasesDayBefore) {
        this.deltaCasesDayBefore = deltaCasesDayBefore;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", newCasesPerDay=" + newCasesPerDay +
                ", deltaCasesDayBefore=" + deltaCasesDayBefore +
                '}';
    }
}
