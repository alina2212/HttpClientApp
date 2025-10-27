package com.example.httpclient.util;

import java.util.Scanner;

public class ConsoleReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        System.out.print("Введите команду (Get 1, Get ALL, Post, Put, Delete, Get URL, Exit): ");
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    public String[] readUrls() {
        System.out.print("Введите один или несколько URL через запятую: ");
        String line = scanner.nextLine().trim();
        return line.split("\\s*,\\s*");
    }
}