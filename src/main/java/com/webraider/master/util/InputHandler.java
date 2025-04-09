package com.webraider.master.util;

import com.webraider.master.style.ConsoleColor;
import com.webraider.master.style.ASCIIArt;

import java.util.Scanner;

/**
*
*@author Sma1lo
*/

public class InputHandler {

    private static String targetUrl;
    private static int numberOfThreads;
    private static int requestsPerThread;
    private static int delay;
    private static String requestMethod;
    private static String postData = "key1=value1&key2=value2";

    public static void handleInput(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            displayMenu(scanner);
        } catch (Exception e) {
            System.err.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Input error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void displayMenu(Scanner scanner) {

        System.out.println(ASCIIArt.WEBRAIDER);
        System.out.println("\t\t" + ConsoleColor.RED + "v 1.0" + ConsoleColor.RESET + ", Author: Sma1lo\n");
        System.out.println("\n[" + ConsoleColor.RED + "1" + ConsoleColor.RESET +"] Start WebRaider");
        System.out.println("[" + ConsoleColor.RED + "0" + ConsoleColor.RESET +"] Exit");
        System.out.print("\nEnter number: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

            switch (choice) {
                case 1:

                    getInputFromUser(scanner);

                    if ("POST".equals(requestMethod)) {
                        System.out.println("POST Data: " + postData);
                    }

                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Invalid choice.");
            }
    }

    private static void getInputFromUser(Scanner scanner) {
        System.out.print("Enter target URL: ");
        targetUrl = scanner.nextLine();

        System.out.print("Enter number of threads: ");
        numberOfThreads = scanner.nextInt();

        System.out.print("Enter number of requests per thread: ");
        requestsPerThread = scanner.nextInt();

        System.out.print("Enter delay between requests (ms): ");
        delay = scanner.nextInt();

        System.out.print("Enter HTTP method (GET/POST): ");
        requestMethod = scanner.next().toUpperCase();
        validateMethod(requestMethod);

        if ("POST".equals(requestMethod)) {
            System.out.print("Enter POST data (key1=value1&key2=value2): ");
            postData = scanner.next();
        }
    }

    private static void validateMethod(String method) {
        if (!"GET".equals(method) && !"POST".equals(method)) {
            System.err.println(ConsoleColor.RED + "[ERROR]" + ConsoleColor.RESET + " Invalid HTTP method. Use GET or POST.");
            System.exit(1);
        }
    }

    public static String getTargetUrl() {
        return targetUrl;
    }

    public static int getNumberOfThreads() {
        return numberOfThreads;
    }

    public static int getRequestsPerThread() {
        return requestsPerThread;
    }

    public static int getDelay() {
        return delay;
    }

    public static String getRequestMethod() {
        return requestMethod;
    }

    public static String getPostData() {
        return postData;
    }
}
