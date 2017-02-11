package com.borehack.geoip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.borehack.geoip.model.GeoIpInfo;
import com.borehack.geoip.model.GeoUserInfo;
import com.borehack.geoip.model.UserAgentInfo;

@RestController
public class RestEndpoint {

    @Autowired
    private GeoIpCache geoIpCache;
    private UserAgentParser userAgentParser = new UserAgentParser();

    @RequestMapping("/geoip")
    public GeoIpInfo geoip(@RequestParam(value = "ip") String ip) {
        return geoIpCache.get(ip);
    }

    @RequestMapping("/useragent")
    public UserAgentInfo userAgent(@RequestParam(value = "useragent") String userAgent) {
        return userAgentParser.parseUserAgent(userAgent);
    }

    @RequestMapping("/geouser")
    public GeoUserInfo geouser(@RequestParam(value = "ip") String ip,
        @RequestParam(value = "userAgent") String userAgent) {
        UserAgentInfo userAgentInfo = userAgentParser.parseUserAgent(userAgent);
        GeoIpInfo geoIpInfo = geoIpCache.get(ip);
        return new GeoUserInfo(geoIpInfo, userAgentInfo);
    }
}
