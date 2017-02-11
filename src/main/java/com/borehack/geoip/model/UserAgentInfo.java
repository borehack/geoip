package com.borehack.geoip.model;

public class UserAgentInfo {
    private String browser;
    private String operatingSystem;

    public UserAgentInfo(String browser, String operatingSystem) {
        this.browser = browser;
        this.operatingSystem = operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
}
