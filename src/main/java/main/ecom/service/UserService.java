package main.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.ecom.model.User;
import main.ecom.repository.UserRepo;
import org.bson.types.ObjectId;

@Service
public class UserService {

    @Autowired
    private UserRepo userDb;

    public List<User> getAllUser() {
        return userDb.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {    
        return userDb.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userDb.findByEmail(email);
    }

    public User saveUser(User obj){
        return userDb.save(obj);
    }
}
