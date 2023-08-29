package sg.edu.nus.iss.server.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServerRepository {

    private MongoTemplate template;
    
}
