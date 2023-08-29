package sg.edu.nus.iss.server.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
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
import sg.edu.nus.iss.server.repository.ServerRepository;

@Service
public class ServerService {

    @Value("${MARVEL_PUBLIC_KEY}")
    String publicKey;

    @Value("${MARVEL_HASH}")
    String hash;

    @Value("${MARVEL_API_URL}")
    String apiURL;

    @Autowired
    private ServerRepository repository;

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

        return finalResults.build();

    }

    public JsonObject getCharacterByID(String id) {

        String urlToCall = UriComponentsBuilder
                .fromUriString(apiURL + "/" + id)
                .queryParam("ts", 1)
                .queryParam("apikey", publicKey)
                .queryParam("hash", hash)
                .toUriString();

        ResponseEntity<String> response = template.getForEntity(urlToCall, String.class);

        JsonReader reader = Json.createReader(new StringReader(response.getBody()));
        JsonObject result = reader.readObject();
        JsonObject character = result.getJsonObject("data").getJsonArray("results").get(0).asJsonObject();
        JsonObject thumbnail = character.getJsonObject("thumbnail");
        String imageUrl = thumbnail.getString("path") + "." + thumbnail.getString("extension");

        JsonObject returnObj = Json.createObjectBuilder()
                .add("id", character.getInt("id"))
                .add("name", character.getString("name"))
                .add("description", character.getString("description"))
                .add("image", imageUrl)
                .build();

        return returnObj;
    }

    public void postCommentToDB(String id, String comment) {

        // convert comment to JSON object
        JsonReader reader = Json.createReader(new StringReader(comment));
        JsonObject commentObj = reader.readObject();

        // first, check if collection already contains this ID
        // if contains ID, retrieve document
        if (repository.checkIfIdExists(id)) {
            Document retrievedObj = repository.getDocumentById(id);
            List<Document> comments = retrievedObj.getList("comments", Document.class);

            Document newComment = new Document();
            newComment.append("text", commentObj.getString("comment"));
            newComment.append("timestamp", new Date().toString());
            comments.add(newComment);

            retrievedObj.remove("comments");
            retrievedObj.append("comments", comments);
            repository.updateComments(id, retrievedObj);
            return;
        }

        // otherwise code can proceed as like below
        Document mainDoc = new Document();
        Document commentDoc = new Document();
        List<Document> comments = new ArrayList<>();

        commentDoc.append("text", commentObj.getString("comment"));
        commentDoc.append("timestamp", new Date().toString());
        comments.add(commentDoc);

        mainDoc.append("_id", id);
        mainDoc.append("comments", comments);

        repository.postComment(mainDoc);

    }

    public Document getCommentsById(String id) {
        return repository.getDocumentById(id);
    }

}
