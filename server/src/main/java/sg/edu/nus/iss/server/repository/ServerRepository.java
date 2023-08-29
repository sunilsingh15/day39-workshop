package sg.edu.nus.iss.server.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ServerRepository {

    @Autowired
    private MongoTemplate template;

    public void postComment(Document comment) {
        template.insert(comment, "comments");
    }

    public Boolean checkIfIdExists(String id) {
        return template.exists(Query.query(Criteria.where("_id").is(id)), "comments");
    }

    public Document getDocumentById(String id) {
        return template.findOne(Query.query(Criteria.where("_id").is(id)), Document.class, "comments");
    }

    public void updateComments(String id, Document newDoc) {
        template.findAndReplace(Query.query(Criteria.where("_id").is(id)), newDoc, "comments");
    }

}
