package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIImagesDownload {
    private static final String OUTPUT_DIR = "E:/IDE project/APIClassLearning/src/main/java/org/example/Images/sprites";

    public static void main(String[] args) {
        APIImagesDownload apiImagesDownload = new APIImagesDownload();
        try {
            apiImagesDownload.get();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void get() throws IOException, InterruptedException {
        String url = "https://pokeapi.co/api/v2/pokemon/ditto/";

        var request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n\nGET API Status Code:- " + response.statusCode());
        String responseBody = response.body();
        System.out.println("Response body:- " + responseBody);

        // Create output directory if it doesn't exist
        Files.createDirectories(Paths.get(OUTPUT_DIR));

        // Parse JSON and download images
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode spritesNode = rootNode.path("sprites");

        downloadAndSaveImages(spritesNode, "");
    }

    private void downloadAndSaveImages(JsonNode node, String parentKey) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();

            if (value.isObject() || value.isArray()) {
                downloadAndSaveImages(value, parentKey.isEmpty() ? key : parentKey + "_" + key);
            } else if (value.isTextual()) {
                String imageUrl = value.asText();
                if (!imageUrl.isEmpty()) {
                    String fileName = parentKey.isEmpty() ? key : parentKey + "_" + key;
                    downloadImage(httpClient, imageUrl, fileName);
                }
            }
        }
    }

    private void downloadImage(HttpClient httpClient, String imageUrl, String key) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(URI.create(imageUrl)).GET().build();
        HttpResponse<InputStream> imageResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

        String fileExtension = imageUrl.substring(imageUrl.lastIndexOf("."));
        String fileName = key + fileExtension;
        try (InputStream inputStream = imageResponse.body();
             FileOutputStream outputStream = new FileOutputStream(OUTPUT_DIR + "/" + fileName)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Downloaded and saved image: " + fileName);
        }
    }
}
