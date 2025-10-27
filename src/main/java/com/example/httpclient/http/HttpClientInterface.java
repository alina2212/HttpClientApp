package com.example.httpclient.http;
import java.io.IOException;

public interface HttpClientInterface {
    String sendRequest(HttpMethod method, String url, String body) throws IOException, InterruptedException;
}