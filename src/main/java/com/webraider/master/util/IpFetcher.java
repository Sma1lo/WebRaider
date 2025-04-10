package com.webraider.master.util;

import com.webraider.master.style.ConsoleColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 *@author Sma1lo
 */

public class IpFetcher {
    private static final String[] IP_LIST_URLS = {
            "https://raw.githubusercontent.com/clarketm/proxy-list/master/proxy-list-raw.txt",
            "https://raw.githubusercontent.com/proxifly/free-proxy-list/main/proxies/protocols/http/data.txt",
            "https://raw.githubusercontent.com/proxifly/free-proxy-list/main/proxies/all/data.txt",
            "https://www.shodan.io/search?query=USA",
            "https://geonode.com/free-proxy-list",
            "https://hasdata.com/free-proxy-list",
            "https://www.proxyrack.com/free-proxy-list/"
    };

    public static List<String> getIpList() {
        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        for (String url : IP_LIST_URLS) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(URI.create(url).toURL().openStream()));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    reader.close();
                    String[] lines = content.toString().split("\n");
                    List<String> ips = new ArrayList<>();
                    for (String l : lines) {
                        String ip = l.split(":")[0].trim();
                        if (ip.matches("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b")) {
                            ips.add(ip);
                        }
                    }
                    return ips;
                } catch (Exception e) {
                    System.err.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Failed to fetch IPs from " + url + ": " + e.getMessage());
                    return new ArrayList<>();
                }
            }));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        List<String> allIps = new ArrayList<>();
        for (CompletableFuture<List<String>> future : futures) {
            allIps.addAll(future.join());
        }
        return allIps;
    }
}