package com.borehack.geoip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.borehack.geoip.model.UserAgentInfo;

import eu.bitwalker.useragentutils.UserAgent;

public class UserAgentParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAgentParser.class);

    public UserAgentInfo parseUserAgent(String userAgent) {
        if (userAgent == null || userAgent.isEmpty()) {
            return new UserAgentInfo("", "");
        }

        try {
            UserAgent userAgentResult = UserAgent.parseUserAgentString(userAgent);
            String browser = userAgentResult.getBrowser() + " " + userAgentResult.getBrowserVersion();
            String operationSystem = userAgentResult.getOperatingSystem().getName();
            return new UserAgentInfo(browser, operationSystem);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new UserAgentInfo("", "");
        }
    }
}
