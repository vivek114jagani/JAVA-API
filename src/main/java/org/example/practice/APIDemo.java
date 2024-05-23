package org.example.practice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//import java.io.BufferedReader;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
import java.net.HttpURLConnection;
import java.net.URI;
//import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Objects;

public class APIDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        APIDemo apiDemo = new APIDemo();
        String id = apiDemo.Post();
        System.out.println("--------------------------------------------------------------------------------------------");
        if (id != null) {
            apiDemo.Get(id);
            System.out.println("--------------------------------------------------------------------------------------------");
            apiDemo.Put(id);
            apiDemo.Get(id);
            System.out.println("--------------------------------------------------------------------------------------------");
            apiDemo.Patch(id);
            apiDemo.Get(id);
            System.out.println("--------------------------------------------------------------------------------------------");
            apiDemo.Delete(id);
        } else {
            System.out.println("Failed to retrieve ID from POST request.");
        }
    }

    public void Get(String id) throws IOException, InterruptedException {
        String url = "https://api.restful-api.dev/objects/" + id;

        var request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("GET API Status Code:- " + response.statusCode());
        System.out.println("GET API Response Body:- \n" + response.body());
    }

    public String Post() throws IOException, InterruptedException {
        String body = """
                {
                    "name": "vivek",
                    "data": {
                        "age": 19,
                        "dob": "11/02/2005",
                        "language": {
                            "hindi": true,
                            "english": true,
                            "gujarati": true,
                            "marathi": false
                        },
                        "address": {
                            "country": "India",
                            "state": "Gujarat",
                            "city": "surat",
                            "area": "varachhaa"
                        }
                    }
                }
                """;

        String url = "https://api.restful-api.dev/objects";

        var request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {

            System.out.println("\n\nPOST API Status code:- " + response.statusCode());
//            System.out.println("POST API Response Body:- \n" + response.body());
            System.out.println("DATA received successfully!");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(response.body());
            String id = responseJson.get("id").asText();

            System.out.println("CREATED DATA ID IS:- " + id);

            return id;
        } else {

            System.out.println("Failed to POST data. Status Code: " + response.statusCode());
            return null;
        }
/*
        This is a second way to post data in API.👇

        URL url = new URL("https://api.restful-api.dev/objects");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try(DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        System.out.println("Status Code:- " + conn.getResponseCode());

        try(BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println("Response Body:- " + line);
            }
        }
*/
    }

    public void Put(String id) throws IOException, InterruptedException {
        String body = """
                {
                    "name": "dhruv",
                    "data": {
                        "age": 29,
                        "dob": "11/02/2005",
                        "language": {
                            "hindi": true,
                            "english": true,
                            "gujarati": true,
                            "marathi": false
                        },
                        "address": {
                            "country": "India",
                            "state": "Gujarat",
                            "city": "surat",
                            "area": "varachhaa"
                        }
                    }
                }
                """;

        String url = "https://api.restful-api.dev/objects/" + id;

        var request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body)).build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("PUT API Status Code:- " + response.statusCode());
            System.out.println("DATA UPDATED SUCCESSFULLY!\nHere is updated data:- "); // go to the main function.
        } else {
            System.out.println("Failed to PUT data. Status Code: " + response.statusCode());
        }
    }

    public void Patch(String id) throws IOException, InterruptedException {
        String body = """
                {
                   "name": "Harsh"
                }
                """;

        String url = "https://api.restful-api.dev/objects/" + id;

        var request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body)).build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("PATCH API Status Code:- " + response.statusCode());
            System.out.println("DATA UPDATED SUCCESSFULLY!\nHere is updated data:- "); // go to the main function.
        } else {
            System.out.println("Failed to PATCH data. Status Code: " + response.statusCode());
        }
    }

    public void Delete(String id) throws IOException, InterruptedException {
        String url = "https://api.restful-api.dev/objects/" + id;

        var request = HttpRequest.newBuilder().uri(URI.create(url)).DELETE().build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("DELETE API Status Code:- " + response.statusCode());
            System.out.println("DATA DELETED SUCCESSFULLY!");
        } else {
            System.out.println("Failed to DELETE data. Status Code: " + response.statusCode());
        }
    }
}
