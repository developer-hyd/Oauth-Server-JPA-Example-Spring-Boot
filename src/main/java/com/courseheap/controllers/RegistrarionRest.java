package com.courseheap.controllers;

import com.oauth.config.CustomUserDetails;
import com.oauth.entities.User;
import com.oauth.repositories.UserRepository;
import com.oauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by ashish.p on 1/8/17.
 */

@RestController
public class RegistrarionRest {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManagerBuilder builder;
    @Autowired
    UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public CompletionStage<ResponseEntity<String>> addMerchant(@RequestBody User user) {
        return CompletableFuture.supplyAsync(() -> {
            userService.save(user);
            try {
                builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Sign Up Failed Please try again..!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("A verification link has been sent to your email ID. Please verify.", HttpStatus.CREATED);
        });
    }

    private UserDetailsService userDetailsService(final UserRepository repository) {
        return username -> new CustomUserDetails(repository.findByName(username).get());
    }


}
