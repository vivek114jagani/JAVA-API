package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        String url = "https://pokeapi.co/api/v2/pokemon/ditto/";
        var request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\n\nStates Code:- " + response.statusCode());
        String responseBody = response.body();
        System.out.println(responseBody);
        System.out.println("\n\n");

        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray abilityArray = jsonResponse.getJSONArray("abilities");

        // Iterate over the abilities array
        for (int i = 0; i < abilityArray.length(); i++) {
            JSONObject abilityObject = abilityArray.getJSONObject(i);
            JSONObject ability = abilityObject.getJSONObject("ability");
            String abilityName = ability.getString("name");
            String abilityURL = ability.getString("url");

            // Print the ability name
            System.out.println("Ability:- " + abilityName);

            // Create and send the request to get ability details
            HttpRequest abilityRequest = HttpRequest.newBuilder().uri(URI.create(abilityURL)).GET().build();
            HttpResponse<String> abilityResponse = HttpClient.newHttpClient().send(abilityRequest, HttpResponse.BodyHandlers.ofString());

            // Print the ability details
            System.out.println("Ability Status:- " + abilityResponse.statusCode());
            System.out.println("Ability Detail:- " + abilityResponse.body());
            System.out.println("--------------------------------------------------------------------------------------------------");
        }

//        App app = new App();
//        app.Get();
    }

//    public void Get() throws IOException, InterruptedException {
//        String url = "https://www.anapioficeandfire.com/api/books/5";
//        var request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println("Status:- " + response.statusCode());
//        String responseBody = response.body();
//        System.out.println("response Body:- " + responseBody);
//        System.out.println("\n\n\n");
//
//        JSONObject jsonObject = new JSONObject(responseBody);
//        JSONArray povCharacters = jsonObject.getJSONArray("povCharacters");
//
//        for (int i = 0; i < povCharacters.length(); i++) {
//            String povCharactersURL = povCharacters.getString(i);
//
//            HttpRequest povCharactersRequest = HttpRequest.newBuilder().uri(URI.create(povCharactersURL)).build();
//            HttpResponse<String> povCharactersResponse = HttpClient.newHttpClient().send(povCharactersRequest, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println("PovCharacters Status:- " + povCharactersResponse.statusCode());
//            System.out.println("PovCharacter Body:- " + povCharactersResponse.body());
//            System.out.println("--------------------------------------------------------------------------------------------------");
//        }
//    }
}
