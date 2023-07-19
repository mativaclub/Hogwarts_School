package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable long id) {
        return studentService.read(id);
    }

    @PostMapping()
    public Student create(@RequestBody() Student student) {
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public Student update(@RequestBody() Student student, @PathVariable long id) {
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }


    @GetMapping("/filter")
    public Map<Long, Student> filterByAge(@RequestParam("age") int age) {
        return studentService.filterByAge(age);
    }



//    @GetMapping
//    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) int age) {
//        if (age > 0) {
//            return ResponseEntity.ok(studentService.findByAge(age));
//        }
//        return ResponseEntity.ok(Collections.emptyList());
//    }
//
//
//    public Collection<Student> findByAge(int age) {
//        ArrayList<Student> result = new ArrayList<>();
//        for (Student student : students.values()) {
//            if (student.getAge() == age) {
//                result.add(student);
//            }
//        }
//        return result;
//    }
}
