package com.webraider.master.util;

import com.webraider.master.style.ConsoleColor;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class InputHandler {
    private String targetUrl;
    private int numThreads;
    private int requestsPerThread;
    private int delayMs;
    private String method;
    private String body;

    public void handleInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter target URL: ");
        targetUrl = scanner.nextLine();
        if (!targetUrl.matches("https?://.+")) {
            throw new IllegalArgumentException("Invalid URL format. Use http:// or https://");
        }

        System.out.print("Enter number of threads: ");
        numThreads = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter number of requests per thread: ");
        requestsPerThread = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter delay between requests (ms): ");
        delayMs = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter HTTP method (GET/POST/HEAD/PUT/DELETE): ");
        method = scanner.nextLine().toUpperCase();
        if (!Arrays.asList("GET", "POST", "HEAD", "PUT", "DELETE").contains(method)) {
            throw new IllegalArgumentException("Unsupported HTTP method");
        }

        if (method.equals("POST") || method.equals("PUT")) {
            System.out.print("Enter request body: ");
            body = scanner.nextLine();
        } else {
            body = "";
        }
    }

    public String getTargetUrl() { return targetUrl; }
    public int getNumThreads() { return numThreads; }
    public int getRequestsPerThread() { return requestsPerThread; }
    public int getDelayMs() { return delayMs; }
    public String getMethod() { return method; }
    public String getBody() { return body; }
}
