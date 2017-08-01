package com.courseheap.services;

import com.courseheap.repositories.CoursesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Created by ashish.p on 31/7/17.
 */
@Service
public class CourseService {

    @Autowired
    private CoursesRepository coursesRepository;

    public CompletableFuture<ResponseEntity<String>> getAllCourses() {

        return CompletableFuture.supplyAsync(() -> {
            String arrayToJson = null;
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                arrayToJson = objectMapper.writeValueAsString(coursesRepository.findAll());
            } catch (JsonProcessingException e) {
                Logger.getLogger("CourseService").info("Converting Iterable to Json failed. " + e);
                e.printStackTrace();
                return new ResponseEntity<>(arrayToJson, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(arrayToJson, HttpStatus.CREATED);
        });
    }

}
