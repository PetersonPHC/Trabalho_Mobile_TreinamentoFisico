package br.edu.fateczl.trabalho_mobile_treinamentofisico.http;



import okhttp3.Response;

import java.io.IOException;
import okhttp3.*;

public class HttpHelper {

    private static OkHttpClient client;
    private static String baseUrl;

    public HttpHelper(String baseUrl) {
        this.client = new OkHttpClient();
        this.baseUrl = baseUrl;
    }

    public static String get(String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return "Response body is null";
            }
        }
    }

    public String getAll(String endpoint) throws IOException {
        return get(endpoint);
    }

    public String post(String endpoint, String json) throws IOException {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return "Response body is null";
            }
        }
    }

    public String put(String endpoint, String json) throws IOException {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return "Response body is null";
            }
        }
    }

    public static String delete(String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return "Response body is null";
            }
        }
    }
}
