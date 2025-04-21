package com.webraider.master.util;

import com.webraider.master.http.HttpRequestHandler;
import com.webraider.master.style.ConsoleColor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *@author Sma1lo
 */

public class ThreadManager {

    public static void executeRequests(int numberOfThreads, int requestsPerThread, int delay, String requestMethod, String postData, AtomicInteger successCount) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> performRequests(requestsPerThread, delay, requestMethod, postData, successCount));
        }

        shutdownExecutor(executor);
    }

    private static void performRequests(int requestsPerThread, int delay, String requestMethod, String postData, AtomicInteger successCount) {
        for (int j = 0; j < requestsPerThread; j++) {
            try {
                HttpRequestHandler.sendRequest(InputHandler.getTargetUrl(), requestMethod, postData);
                successCount.incrementAndGet();
            } catch (IOException e) {
                System.err.printf(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Request failed: %s%n", e.getMessage());
            }
            sleep(delay);
        }
    }

    private static void sleep(int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Thread was interrupted during sleep: " + e.getMessage());
        }
    }

    private static void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
