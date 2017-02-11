package com.borehack.geoip.model;

public class GeoUserInfo {
    private String country;
    private String city;
    private String browser;
    private String operatingSystem;

    public GeoUserInfo(GeoIpInfo geoip, UserAgentInfo userAgent) {
        this.country = geoip.getCountry();
        this.city = geoip.getCity();
        this.browser = userAgent.getBrowser();
        this.operatingSystem = userAgent.getOperatingSystem();
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
}
