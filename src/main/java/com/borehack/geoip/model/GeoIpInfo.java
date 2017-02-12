package com.borehack.geoip.model;

public class GeoIpInfo {
    private String country = "";
    private String city = "";

    public GeoIpInfo() {
    }

    public GeoIpInfo(String country, String city) {
        this.country = empty(country);
        this.city = empty(city);
    }

    private String empty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        } else {
            return value.trim();
        }
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
