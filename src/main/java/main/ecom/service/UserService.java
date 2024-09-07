package main.ecom.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import main.ecom.model.User;
import main.ecom.repository.UserRepo;
import org.bson.types.ObjectId;

@Service
public class UserService {

    @Autowired
    private UserRepo userDb;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getAllUser() {
        return userDb.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userDb.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userDb.findByEmail(email);
    }

    public User saveUser(User obj) {
        return userDb.save(obj);
    }

    public void updateMultipleFields(String userId, Map<String, Object> fieldsToUpdate) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(userId)));

        Update update = new Update();
        fieldsToUpdate.forEach(update::set);

        mongoTemplate.updateMulti(query, update, "user");
    }
}
