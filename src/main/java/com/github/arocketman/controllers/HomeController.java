package com.github.arocketman.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping(value = "/")
    public String index(){
        return "Hello world";
    }
    
    @GetMapping(value = "/private/home")
    public String index1(){
        return "Hello world";
    }
}
