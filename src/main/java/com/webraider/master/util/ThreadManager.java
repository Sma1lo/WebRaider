package com.webraider.master.util;

import com.webraider.master.http.AsyncHttpHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
*
*@author Sma1lo
*/

public class ThreadManager {
    public static void executeMultiThreadedRequests(
            String targetUrl, int numThreads, int requestsPerThread, String method, String body,
            List<String> ipList, AtomicInteger sent, AtomicInteger success, AtomicInteger failed, int delayMs) {

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        Random random = new Random();

        for (int t = 0; t < numThreads; t++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < requestsPerThread; i++) {
                    String fakeIp = ipList.isEmpty()
                            ? "192.168.1." + random.nextInt(255)
                            : ipList.get(random.nextInt(ipList.size()));
                    AsyncHttpHandler.sendRequest(targetUrl, method, body, fakeIp, sent, success, failed).join();
                    try {
                        Thread.sleep(delayMs);
                    } catch (InterruptedException ignored) {}
                }
            });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
