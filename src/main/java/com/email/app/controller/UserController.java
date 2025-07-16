package com.email.app.controller;

import com.email.app.model.User;
import com.email.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Verification mail send Successfully. Please check your mail!! :)");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token){
        boolean isveified = userService.verifyToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Verified successfully. :)");
    }
}
