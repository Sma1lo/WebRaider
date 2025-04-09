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
    private int numRequests;
    private String method;
    private String body;

    public void handleInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter target URL: ");
        targetUrl = scanner.nextLine();
        if (!targetUrl.matches("https?://.+")) {
            throw new IllegalArgumentException("Invalid URL format. Use http:// or https://");
        }

        System.out.print("Enter number of requests: ");
        numRequests = Integer.parseInt(scanner.nextLine());
        if (numRequests <= 0) {
            throw new IllegalArgumentException("Number of requests must be positive");
        }

        System.out.print("Enter HTTP method (GET/POST/HEAD/PUT/DELETE): ");
        method = scanner.nextLine().toUpperCase();
        if (!Arrays.asList("GET", "POST", "HEAD", "PUT", "DELETE").contains(method)) {
            throw new IllegalArgumentException("Unsupported HTTP method");
        }

        if (method.equals("POST") || method.equals("PUT")) {
            System.out.print("Enter request body (e.g., key1=value1&key2=value2): ");
            body = scanner.nextLine();
        } else {
            body = "";
        }
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public int getNumRequests() {
        return numRequests;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }
}