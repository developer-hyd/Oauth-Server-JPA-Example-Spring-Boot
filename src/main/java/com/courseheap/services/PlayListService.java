package com.courseheap.services;

import com.courseheap.repositories.PlayListRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Created by ashish.p on 1/8/17.
 */
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;

    public CompletableFuture<ResponseEntity<String>> getAllPLayLists() {

        return CompletableFuture.supplyAsync(() -> {
            String arrayToJson = null;
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                arrayToJson = objectMapper.writeValueAsString(playListRepository.findAll());
            } catch (JsonProcessingException e) {
                Logger.getLogger("CourseService").info("Converting Iterable to Json failed. " + e);
                e.printStackTrace();
                return new ResponseEntity<>(arrayToJson, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(arrayToJson, HttpStatus.CREATED);
        });
    }

    public CompletableFuture<ResponseEntity<String>> getPLayListsByLanguage(String language) {

        return CompletableFuture.supplyAsync(() -> {
            String arrayToJson = null;
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                arrayToJson = objectMapper.writeValueAsString(playListRepository.findByLanguage(language));
            } catch (JsonProcessingException e) {
                Logger.getLogger("CourseService").info("Converting Iterable to Json failed. " + e);
                e.printStackTrace();
                return new ResponseEntity<>(arrayToJson, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(arrayToJson, HttpStatus.CREATED);
        });
    }
}
