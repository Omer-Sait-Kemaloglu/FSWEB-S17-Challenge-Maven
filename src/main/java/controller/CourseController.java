package com.workintech.controller;

import com.workintech.model.*;
import entity.HighCourseGpa;
import entity.LowCourseGpa;
import entity.MediumCourseGpa;
import lombok.extern.slf4j.Slf4j;
import model.Course;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workintech/courses")
@Slf4j
public class CourseController {
    private final List<Course> courses = new ArrayList<>();
    private final LowCourseGpa lowCourseGpa;
    private final MediumCourseGpa mediumCourseGpa;
    private final HighCourseGpa highCourseGpa;

    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course getCourseByName(@PathVariable String name) {
        return courses.stream()
                .filter(course -> course.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public String addCourse(@RequestBody Course course) {
        if (courses.stream().anyMatch(c -> c.getName().equalsIgnoreCase(course.getName()))) {
            throw new RuntimeException("A course with the same name already exists.");
        }

        if (course.getCredit() < 1 || course.getCredit() > 4) {
            throw new RuntimeException("Credit must be between 1 and 4.");
        }

        courses.add(course);
        int totalGpa = calculateTotalGpa(course);
        return "Course added! Total GPA: " + totalGpa;
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable int id, @RequestBody Course course) {
        if (id < 0 || id >= courses.size()) {
            throw new RuntimeException("Invalid course ID");
        }

        courses.set(id, course);
        int totalGpa = calculateTotalGpa(course);
        return "Course updated! Total GPA: " + totalGpa;
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        if (id < 0 || id >= courses.size()) {
            throw new RuntimeException("Invalid course ID");
        }

        courses.remove(id);
        return "Course deleted successfully!";
    }

    private int calculateTotalGpa(Course course) {
        int gpa;
        if (course.getCredit() <= 2) {
            gpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        } else if (course.getCredit() == 3) {
            gpa = course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        } else {
            gpa = course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }
        return gpa;
    }
}
