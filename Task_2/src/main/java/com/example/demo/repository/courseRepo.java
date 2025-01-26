package com.example.demo.repository;

import com.example.demo.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface courseRepo extends JpaRepository<Course,Integer> {

    List<Course> findByFirstName(String firstname);
}
