package com.example.health_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Fixes the "Network Error" you saw in React
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Find the user in the MySQL 'users' table you just verified
        return userRepository.findByUsername(loginRequest.getUsername())
            .filter(user -> user.getPassword().equals(loginRequest.getPassword()))
            .map(user -> ResponseEntity.ok(user)) // Returns the user role (DOCTOR/PATIENT)
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}