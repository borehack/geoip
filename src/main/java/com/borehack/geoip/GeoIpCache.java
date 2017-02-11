package com.borehack.geoip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.borehack.geoip.model.GeoIpInfo;
import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;

@Service
public class GeoIpCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoIpCache.class);

    private static final String MMDB_URL = "http://geolite.maxmind.com/download/geoip/database/";
    private static final String MMDB_FILE = "GeoLite2-City.mmdb.gz";
    private DatabaseReader reader;

    public GeoIpCache() {
        this.reader = initializeReader();
    }

    public GeoIpInfo get(String remoteAddr) {
        if (remoteAddr == null || remoteAddr.isEmpty()) {
            return new GeoIpInfo("", "");
        }

        try {
            InetAddress ipAddress = InetAddress.getByName(remoteAddr);
            CityResponse response = reader.city(ipAddress);
            Country country = response.getCountry();
            return new GeoIpInfo(country.getIsoCode(), response.getCity().getName());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new GeoIpInfo("", "");
        }
    }

    /**
     * From the website: http://dev.maxmind.com/geoip/geoip2/geolite2/ <br />
     * GeoLite2 databases are free IP geolocation databases comparable to, but less accurate than, MaxMind’s GeoIP2
     * databases. GeoLite2 databases are updated on the first Tuesday of each month. <br />
     * The pattern is a list of six single space-separated fields: representing second, minute, hour, day, month,
     * weekday. Month and weekday names can be given as the first three letters of the English names.
     */
    @Scheduled(cron = "0 0 2 1-7 * WED")
    public void refresh() {
        LOGGER.info("start refresh");
        downloadFile();
        reader = initializeReader();
        LOGGER.info("end refresh");
    }

    private DatabaseReader initializeReader() {
        if (!mmdbFileExists()) {
            downloadFile();
        }

        try {
            InputStream in = new GZIPInputStream(new FileInputStream(MMDB_FILE));
            return new DatabaseReader.Builder(in).withCache(new CHMCache()).build();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean mmdbFileExists() {
        return new File(MMDB_FILE).exists();
    }

    private void downloadFile() {
        try {
            LOGGER.info("start download");
            URL website = new URL(MMDB_URL + MMDB_FILE);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(MMDB_FILE);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            LOGGER.info("end download");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
