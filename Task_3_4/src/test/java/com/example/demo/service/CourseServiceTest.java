package com.example.demo.service;
import com.example.demo.models.Course;
import com.example.demo.repository.courseRepo;
import com.example.demo.controller.Courses;
import com.example.demo.exceptions.CourseNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourseServiceTest {

    @Mock
    private courseRepo courseRepository;

    @InjectMocks
    private Courses coursesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Course course1 = new Course(1L, "Math", 30);
        Course course2 = new Course(2L, "Physics", 40);
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> result = coursesController.getAll().getBody();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getfirstName());
        assertEquals("Physics", result.get(1).getfirstName());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseByFirstname() {
        Course course = new Course(1L, "Math", 30);
        when(courseRepository.findByFirstName("Math")).thenReturn(List.of(course));

        List<Course> result = coursesController.getCourseByFirstname("Math").getBody();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Math", result.get(0).getfirstName());
        verify(courseRepository, times(1)).findByFirstName("Math");
    }

    @Test
    void testGetCourseByFirstnameNotFound() {
        when(courseRepository.findByFirstName("NonExistent")).thenReturn(List.of());
        assertThrows(CourseNotFoundException.class, () -> {
            coursesController.getCourseByFirstname("NonExistent");
        });
        verify(courseRepository, times(1)).findByFirstName("NonExistent");
    }

    @Test
    void testCreateCourse() {
        Course newCourse = new Course(1L, "Math", 30);
        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);

        Course result = coursesController.create_Course(newCourse).getBody();

        assertNotNull(result);
        assertEquals("Math", result.getfirstName());
        assertEquals(30, result.getduration());

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse() {
        Course existingCourse = new Course(1L, "Math", 30);
        Course updatedCourse = new Course(1L, "Math 101", 35);

        when(courseRepository.findById(1)).thenReturn(Optional.of((existingCourse)));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);
        Course result = coursesController.updateCourse(1, updatedCourse).getBody();

        assertNotNull(result);
        assertEquals("Math 101", result.getfirstName());
        assertEquals(35, result.getduration());
        verify(courseRepository, times(1)).findById(1);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourseNotFound() {
        Course updatedCourse = new Course(1L, "Math Updated", 35);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {
            coursesController.updateCourse(1, updatedCourse);
        });
        verify(courseRepository, times(1)).findById(1);
    }
}