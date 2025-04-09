package com.webraider.master.http;

import com.webraider.master.style.ConsoleColor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
@author Sma1lo
*/

public class HttpRequestHandler {

    public static void sendRequest(String targetUrl, String requestMethod, String postData) throws IOException {
        HttpURLConnection connection = null;
        try {
            URI uri = new URI(targetUrl);
            URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "LoadTestClient");

            if ("POST".equals(requestMethod)) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            connection.connect();
            int responseCode = connection.getResponseCode();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.printf(ConsoleColor.GREEN + "[INFO]" + ConsoleColor.RESET + " [%s] Resp: %d%n", timestamp, responseCode);

            if (responseCode != 200) {
                retryRequest(connection);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static void retryRequest(HttpURLConnection connection) throws IOException {
        int retries = 3;
        int responseCode = connection.getResponseCode();
        while (responseCode != 200 && retries > 0) {
            System.out.printf(ConsoleColor.YELLOW + "[WARN]" + ConsoleColor.RESET + " Retry %d for response %d%n", 4 - retries, responseCode);
            retries--;
            connection.connect();
            responseCode = connection.getResponseCode();
        }
    }
}