package com.webraider.master.http;

import com.webraider.master.style.ConsoleColor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncHttpHandler {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final String[] USER_AGENTS = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Safari/605.1.15",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0",
            "Mozilla/5.0 (Linux; Android 11; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.210 Mobile Safari/537.36",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1"
    };

    public static CompletableFuture<Void> sendRequest(String targetUrl, String method, String body, String fakeIp,
                                                      AtomicInteger sent, AtomicInteger success, AtomicInteger failed) {
        Random random = new Random();
        String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("User-Agent", userAgent)
                .header("X-Forwarded-For", fakeIp);

        switch (method) {
            case "GET":
                requestBuilder.GET();
                break;
            case "POST":
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
                break;
            case "HEAD":
                requestBuilder.method("HEAD", HttpRequest.BodyPublishers.noBody());
                break;
            case "PUT":
                requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body));
                break;
            case "DELETE":
                requestBuilder.method("DELETE", HttpRequest.BodyPublishers.noBody());
                break;
            default:
                System.err.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Unsupported method: " + method);
                return CompletableFuture.completedFuture(null);
        }

        HttpRequest request = requestBuilder.build();

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    sent.incrementAndGet();
                    int status = response.statusCode();
                    if (status >= 200 && status < 300) {
                        success.incrementAndGet();
                        System.out.println(ConsoleColor.GREEN + "[INFO] " + ConsoleColor.RESET + targetUrl + " - Status: " + status + " (IP: " + fakeIp + ")");
                    } else {
                        failed.incrementAndGet();
                        System.out.println(ConsoleColor.YELLOW + "[WARN] " + ConsoleColor.RESET + targetUrl + " - Status: " + status + " (IP: " + fakeIp + ")");
                    }
                })
                .exceptionally(throwable -> {
                    failed.incrementAndGet();
                    System.err.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Request failed: " + throwable.getMessage());
                    return null;
                });
    }
}
