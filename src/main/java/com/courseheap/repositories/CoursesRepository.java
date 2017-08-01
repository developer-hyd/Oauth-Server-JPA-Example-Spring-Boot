package com.courseheap.repositories;

/**
 * Created by ashish.p on 31/7/17.
 */

import com.courseheap.entities.Course;
import org.springframework.data.repository.CrudRepository;


public interface CoursesRepository extends CrudRepository<Course, Long> {

    Course findByCourseName(String courseName);
    Course findById(Long courseId);
}
