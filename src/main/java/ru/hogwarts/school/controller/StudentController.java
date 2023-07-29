package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
//    private final FacultyService facultyService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
//        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public Optional<Student> getById(@PathVariable long id) {
        return studentService.read(id);
    }

    @PostMapping()
    public Student create(@RequestBody() Student student, @RequestParam(required = false) Long facultyId) {
//        Optional<Faculty> faculty = facultyService.read(facultyId);
//        if (faculty.isPresent()) {
//            student.setFaculty(faculty.get());
//        }
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public Student update(@RequestBody() Student student, @PathVariable long id) {
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/filter")
    public ResponseEntity<Collection<Student>> filterByAge(@RequestParam("age") int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.filterByAge(age));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filterBetween")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam("from") int fromAge,
                                                                @RequestParam("to") int toAge) {
        if (fromAge > 0 && toAge > 0 && fromAge <= toAge) {
            return ResponseEntity.ok(studentService.findByAgeBetween(fromAge, toAge));
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/filterByFaculty")
    public ResponseEntity<Collection<Student>> filterStudentsByFaculty(
            @RequestParam("facultyId") long facultyId) {
        return ResponseEntity.ok(studentService.findStudentsByFaculty_Id(facultyId));
    }



}

