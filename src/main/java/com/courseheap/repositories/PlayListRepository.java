package com.courseheap.repositories;

/**
 * Created by ashish.p on 31/7/17.
 */

import com.courseheap.entities.PlayList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayListRepository extends CrudRepository<PlayList, Long> {

    PlayList findById(Long id);
    List<PlayList> findByLanguage(String language);
    PlayList findByPlayListDetails(String playListDetails);
}
