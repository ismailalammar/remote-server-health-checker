package com.server.healthchecker.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HostService {

    public boolean pingURL(String url, int timeout) {
        /*
        ping using http instead of https Otherwise an exception may be thrown on invalid SSL certificates.
        * */
        url = url.replaceFirst("^https", "http");

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }
}
