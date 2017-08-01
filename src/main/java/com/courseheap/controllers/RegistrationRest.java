package com.courseheap.controllers;

/**
 * Created by ashish.p on 1/8/17.
 */

import com.courseheap.services.RegistrationService;
import com.oauth.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@RestController
public class RegistrationRest {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/signup")
    @ResponseBody
    public CompletionStage<ResponseEntity<String>> addUser(@RequestBody User user) {
        return registrationService.UserSignUp(user);
    }

    @GetMapping("/signup/verify")
    @ResponseBody
    public CompletionStage<ResponseEntity<String>> verifyUser(@RequestParam String token) {
        return registrationService.verifyUser(token);
    }
}
