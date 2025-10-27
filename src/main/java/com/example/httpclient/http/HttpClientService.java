package com.example.httpclient.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientService implements HttpClientInterface {
    private static final Logger logger = LogManager.getLogger(HttpClientService.class);
    private final HttpClient client;

    public HttpClientService() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public String sendRequest(HttpMethod method, String url, String body) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10));

        switch (method) {
            case GET -> builder.GET();
            case DELETE -> builder.DELETE();
            case POST -> builder.header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
            case PUT -> builder.header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
        }

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        logger.info("Запрос выполнен: {} {} - статус {}", method, url, response.statusCode());
        return response.body();
    }
}