package com.example.httpclient.http;

import com.example.httpclient.cache.CacheService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CachedHttpClient {
    private static final Logger logger = LogManager.getLogger(CachedHttpClient.class);

    private final HttpClientInterface clientService1;
    private final HttpClientInterface clientService2;
    private final CacheService cache;

    public CachedHttpClient(HttpClientInterface clientService1, HttpClientInterface clientService2, CacheService cache) {
        this.clientService1 = clientService1;
        this.clientService2 = clientService2;
        this.cache = cache;
    }


    private HttpClientInterface selectClientService(String url) {
        if (url.contains("jsonplaceholder")) {
            return clientService1;
        } else {
            return clientService2;
        }
    }


    public String get(String url) {
        try {
            if (cache.contains(url)) {
                String cachedResponse = cache.get(url);
                System.out.println(" Ответ получен из кэша:");
                System.out.println(cachedResponse);
                return cachedResponse;
            }

            String response = selectClientService(url).sendRequest(HttpMethod.GET, url, null);
            System.out.println(" Ответ от сервера:");
            System.out.println(response);

            cache.put(url, response);
            return response;

        } catch (IOException | InterruptedException e) {
            logger.error("Ошибка при GET-запросе: {}", e.getMessage());
            System.err.println("Ошибка при GET-запросе: " + e.getMessage());
            return null;
        }
    }


    public String post(String url, String body) {
        try {
            String response = selectClientService(url).sendRequest(HttpMethod.POST, url, body);
            System.out.println(" Ответ от сервера:");
            System.out.println(response);
            return response;
        } catch (IOException | InterruptedException e) {
            logger.error("Ошибка при POST-запросе: {}", e.getMessage());
            System.err.println("Ошибка при POST-запросе: " + e.getMessage());
            return null;
        }
    }

    public String put(String url, String body) {
        try {
            String response = selectClientService(url).sendRequest(HttpMethod.PUT, url, body);
            System.out.println(" Ответ от сервера:");
            System.out.println(response);
            return response;
        } catch (IOException | InterruptedException e) {
            logger.error("Ошибка при PUT-запросе: {}", e.getMessage());
            System.err.println("Ошибка при PUT-запросе: " + e.getMessage());
            return null;
        }
    }


    public String delete(String url) {
        try {
            String response = selectClientService(url).sendRequest(HttpMethod.DELETE, url, null);
            System.out.println(" Ответ от сервера:");
            System.out.println(response);

            cache.remove(url); // очистка кэша после удаления
            return response;
        } catch (IOException | InterruptedException e) {
            logger.error("Ошибка при DELETE-запросе: {}", e.getMessage());
            System.err.println("Ошибка при DELETE-запросе: " + e.getMessage());
            return null;
        }
    }
}
