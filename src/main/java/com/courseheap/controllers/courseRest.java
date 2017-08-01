package com.courseheap.controllers;

/**
 * Created by ashish.p on 31/7/17.
 */

import com.courseheap.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletionStage;

@RestController
public class courseRest {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/private/getC")
    public String securedHello() {
        return "Secured Hello0000000000";
    }

    @GetMapping(value = "/private/getCourses", produces = {"application/json"})
    @ResponseBody
    public CompletionStage<ResponseEntity<String>> addMerchant() {
        return courseService.getAllCourses();
    }


}
