package com.example.demo.controller;
import com.example.demo.exceptions.CourseNotFoundException;
import com.example.demo.models.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.courseRepo;

import java.util.List;
import java.util.Optional;

@RestController
public class Courses {
    @Autowired
    private courseRepo Course;

    @GetMapping("/course")
    public ResponseEntity<List<Course>> getAll(){
       try{
           List<Course> Courses = Course.findAll();
           return new ResponseEntity<>(Courses, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/course/{firstname}")
    public ResponseEntity<List<Course>> getCourseByFirstname(@PathVariable String firstname) {
        List<Course> courseData = Course.findByFirstName(firstname);
        if (!courseData.isEmpty()) {
            return new ResponseEntity<>(courseData, HttpStatus.OK);  // Return all matching courses
        }
        throw new CourseNotFoundException("Course with first name " + firstname + " not found.");
    }


    @PostMapping("/course")
    public ResponseEntity<Course> create_Course(@RequestBody Course Course){
       Course newCourse = this.Course.save(Course);
       return new ResponseEntity<>(newCourse, HttpStatus.OK);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer id, @RequestBody Course courseDetails) {
        Optional<Course> optionalCourse = Course.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            BeanUtils.copyProperties(courseDetails, existingCourse, "id");
            Course updatedCourse = Course.save(existingCourse);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            throw new CourseNotFoundException("Course with id " + id + " not found.");
        }
    }

    @PatchMapping("/course/{id}")
    public ResponseEntity<Course> partialUpdateCourse(@PathVariable Integer id, @RequestBody Course courseDetails) {
        Optional<Course> optionalCourse = Course.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            if (courseDetails.getfirstName() != null) {
                existingCourse.setfirstName(courseDetails.getfirstName());
            }
            if (courseDetails.getduration() != null) {
                existingCourse.setduration(courseDetails.getduration());
            }
            Course updatedCourse = Course.save(existingCourse);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            throw new CourseNotFoundException("Course with id " + id + " not found.");
        }
    }
}

