package com.oauth.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping(value = "/")
    public String index() {
        return "Hello world";
    }

    @GetMapping(value = "/private/home")
    public String index1() {
        return "Hello world";
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/private/all")
    public String securedHello() {
        return "Secured Hello";
    }

    @GetMapping("/private/alternate")
    public String alternate() {
        return "alternate";
    }
}
