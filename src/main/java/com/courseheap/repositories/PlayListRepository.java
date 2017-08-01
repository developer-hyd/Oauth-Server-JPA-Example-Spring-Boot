package com.courseheap.repositories;

import com.courseheap.entities.PlayList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ashish.p on 1/8/17.
 */
public interface PlayListRepository extends CrudRepository<PlayList, Long> {

    PlayList findById(Long id);
    List<PlayList> findByLanguage(String language);
    PlayList findByPlayListDetails(String playListDetails);
}
