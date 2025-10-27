package org.example;

import com.example.httpclient.cache.CacheService;
import com.example.httpclient.http.CachedHttpClient;
import com.example.httpclient.http.HttpClientService;
import com.example.httpclient.http.HttpClientInterface;
import com.example.httpclient.util.ConsoleReader;

public class Main {
    public static void main(String[] args) {
        HttpClientInterface clientService1 = new HttpClientService();
        HttpClientInterface clientService2 = new HttpClientService();
        CacheService cacheService = new CacheService();
        CachedHttpClient client = new CachedHttpClient(clientService1, clientService2, cacheService);
        ConsoleReader reader = new ConsoleReader();

        System.out.println("=== HTTP Client App ===");
        System.out.println("Доступные команды:");
        System.out.println("Get 1, Get ALL, Post, Put, Delete, Get URL, Exit\n");

        while (true) {
            String command = reader.readCommand();

            if (command.equalsIgnoreCase("Exit")) {
                System.out.println("Выход из программы...");
                break;
            }

            switch (command.toLowerCase()) {
                case "get 1" -> client.get("https://jsonplaceholder.typicode.com/todos/1");
                case "get all" -> client.get("https://jsonplaceholder.typicode.com/todos");
                case "post" -> {
                    System.out.print("Выберите API (1 - jsonplaceholder, 2 - beeceptor): ");
                    String apiChoice = reader.readCommand();
                    if (apiChoice.equals("1")) {
                        client.post("https://jsonplaceholder.typicode.com/todos",
                                "{\"userId\":1,\"title\":\"test\",\"completed\":false}");
                    } else if (apiChoice.equals("2")) {
                        client.post("https://fake-json-api.mock.beeceptor.com/users",
                                "{\"name\": \"John\"}");
                    } else {
                        System.out.println("Неверный выбор API");
                    }
                }
                case "put" -> {
                    client.put("https://jsonplaceholder.typicode.com/todos/1",
                            "{\"userId\":1,\"title\":\"updated\",\"completed\":true}");
                    client.put("https://fake-json-api.mock.beeceptor.com/users/1",
                            "{\"name\": \"John Updated\"}");
                }
                case "delete" -> {
                    client.delete("https://jsonplaceholder.typicode.com/todos/1");
                    client.delete("https://fake-json-api.mock.beeceptor.com/users/1");
                }
                case "get url" -> {
                    String[] urls = reader.readUrls();
                    for (String url : urls) {
                        client.get(url);
                    }
                }
                default -> System.out.println("Неизвестная команда! Попробуйте снова.");
            }
        }

        reader.close();
    }
}