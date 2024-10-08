package main.ecom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.ecom.model.Product;
import main.ecom.model.User;
import main.ecom.service.EmailService;
import main.ecom.service.UserService;
import main.ecom.util.Auth;
import main.ecom.util.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {
    Map<String, Object> response = new HashMap<>();

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService mailService;
    @Autowired
    private Auth auth;
    @Autowired
    private JwtUtil jwt;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/getAllUser")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Optional<User> obj = userService.getUserByEmail(user.getEmail());
        if (obj.isPresent()) {
            response.put("status", 0);
            response.put("response", "Email already exist");
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }
        user.setOtp(auth.generateOtp());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userService.saveUser(user);
        response.put("status", 1);
        response.put("response", "Registration successfull");
        response.put("userId", createdUser.get_id().toString());
        Map<String, Object> emailPayload = new HashMap<>();
        emailPayload.put("userName", createdUser.getUserName());
        emailPayload.put("otp", createdUser.getOtp());
        mailService.sendHtmlMessage(emailPayload, "Register", createdUser.getEmail(), "Verify OTP");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/verifyOtp/{id}")
    public ResponseEntity<Map<String, Object>> verifyOTP(@PathVariable ObjectId id, @RequestBody User user) {
        Optional<User> obj = userService.getUserById(id);
        if (!obj.isPresent()) {
            response.put("status", 0);
            response.put("response", "Invalid Request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (user.getOtp().equals(obj.get().getOtp())) {
            obj.get().setStatus(1);
            userService.saveUser(obj.get());
            response.put("status", 1);
            response.put("response", "OTP verified successfull");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("status", 0);
        response.put("response", "Invalid Request");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        Optional<User> obj = userService.getUserByEmail(user.getEmail());
        if (!obj.isPresent()) {
            response.put("status", 0);
            response.put("response", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        boolean passwordMatch = passwordEncoder.matches(user.getPassword(), obj.get().getPassword());
        if (passwordMatch == false) {
            response.put("status", 0);
            response.put("response", "Invalid credentials");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        String token = jwt.generateToken(obj.get().get_id().toString());
        response.put("status", 1);
        response.put("response", "Login successfull");
        response.put("token", token);
        response.put("role", obj.get().getRole());
        response.put("userId", obj.get().get_id());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable ObjectId id) {
        return userService.getUserById(id);
    }

    @PostMapping("/addToCard")
    public ResponseEntity<Map<String, Object>> addToCard(@RequestBody User user) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("card", user.getCard());
        userService.updateMultipleFields(user.get_id().toString(), updates);
        response.put("status", 1);
        response.put("response", "Added to card");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
