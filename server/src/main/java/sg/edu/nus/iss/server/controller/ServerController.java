package sg.edu.nus.iss.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.server.service.ServerService;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin()
public class ServerController {

    @Autowired
    private ServerService service;

    @GetMapping(path = "/characters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharacters(@RequestParam String query,
            @RequestParam(defaultValue = "20") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        return ResponseEntity.ok(service.getCharacters(query, limit, offset).toString());
    }

    @GetMapping(path = "/character/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharacterByID(@PathVariable String id) {
        return ResponseEntity.ok(service.getCharacterByID(id).toString());
    }

    @PostMapping(path = "/character/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postComment(@PathVariable String id, @RequestBody String comment) {
        return ResponseEntity.ok("");
    }

}
