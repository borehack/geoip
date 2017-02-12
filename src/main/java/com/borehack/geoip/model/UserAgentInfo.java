package com.borehack.geoip.model;

public class UserAgentInfo {
    private String browser = "";
    private String operatingSystem = "";

    public UserAgentInfo() {
    }

    public UserAgentInfo(String browser, String operatingSystem) {
        this.browser = empty(browser);
        this.operatingSystem = empty(operatingSystem);
    }

    private String empty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        } else {
            return value.trim();
        }
    }

    public String getBrowser() {
        return browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
}
