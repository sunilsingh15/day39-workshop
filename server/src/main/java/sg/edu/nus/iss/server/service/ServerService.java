package sg.edu.nus.iss.server.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import lombok.val;

@Service
public class ServerService {

    @Value("${MARVEL_PUBLIC_KEY}")
    String publicKey;

    @Value("${MARVEL_HASH}")
    String hash;

    @Value("${MARVEL_API_URL}")
    String apiURL;

    private RestTemplate template = new RestTemplate();

    public JsonArray getCharacters(String query, Integer limit, Integer offset) {

        String urlToCall = UriComponentsBuilder
                .fromUriString(apiURL)
                .queryParam("ts", 1)
                .queryParam("nameStartsWith", query)
                .queryParam("apikey", publicKey)
                .queryParam("hash", hash)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .toUriString();

        ResponseEntity<String> response = template.getForEntity(urlToCall, String.class);

        JsonReader reader = Json.createReader(new StringReader(response.getBody()));
        JsonObject result = reader.readObject();
        JsonObject data = result.getJsonObject("data");
        JsonArray searchResults = data.getJsonArray("results");

        JsonArrayBuilder finalResults = Json.createArrayBuilder();

        for (JsonValue value : searchResults) {
            JsonObject valueObj = value.asJsonObject();
            JsonObject thumbnail = valueObj.getJsonObject("thumbnail");

            String imageUrl = thumbnail.getString("path") + '.' + thumbnail.getString("extension");

            JsonObject valuesToAdd = Json.createObjectBuilder()
                    .add("id", valueObj.getInt("id"))
                    .add("name", valueObj.getString("name"))
                    .add("image", imageUrl)
                    .build();

            finalResults.add(valuesToAdd);
        }

        // List<JsonObject> finalResults2 = searchResults.stream()
        //         .map(JsonValue::asJsonObject)
        //         .map(valueObj -> Json.createObjectBuilder()
        //                 .add("id", valueObj.getInt("id"))
        //                 .add("name", valueObj.getString("name"))
        //                 .build())
        //         .collect(Collectors.toList());

        return finalResults.build();

    }

}
