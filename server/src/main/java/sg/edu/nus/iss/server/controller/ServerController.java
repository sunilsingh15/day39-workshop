package sg.edu.nus.iss.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.server.service.ServerService;

@RestController
@RequestMapping(path = "/api")
public class ServerController {

    @Autowired
    private ServerService service;

    @GetMapping(path = "/characters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCharacters(@RequestParam String query,
            @RequestParam(defaultValue = "20") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        return ResponseEntity.ok(service.getCharacters(query, limit, offset).toString());
    }

}
