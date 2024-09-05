package main.ecom.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.ecom.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {
    
    Optional<User> findByEmail(String email);
}
