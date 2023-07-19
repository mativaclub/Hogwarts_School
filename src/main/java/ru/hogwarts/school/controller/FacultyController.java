package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public Faculty getById(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PostMapping()
    public Faculty create(@RequestBody() Faculty faculty) {
        return facultyService.create(faculty);
    }

    @PutMapping("/{id}")
    public Faculty update(@RequestBody() Faculty faculty, @PathVariable() long id) {
        faculty.setId(id);
        return facultyService.update(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }


    @GetMapping("/filter")
    public Map<Long, Faculty> filterByColor(@RequestParam("color") String color) {
        return facultyService.filterByColor(color);
    }



//    @GetMapping("{id}")
//    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
//        Faculty faculty = facultyService.read(id);
//        if (faculty == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(faculty);
//    }


//    @GetMapping
//    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
//        if (color != null && !color.isBlank()) {
//            return ResponseEntity.ok(facultyService.findByColor(color));
//        }
//        return ResponseEntity.ok(Collections.emptyList());
//    }
//
//    // Service
//    public Collection<Faculty>
//    findByColor(String color) {
//        ArrayList<Faculty> result = new ArrayList<>();
//        for (Faculty faculty : faculties.values()) {
//            if (Objects.equals(faculty.getColor(), color)) {
//                result.add(faculty);
//            }
//        }
//        return result;
//    }

}
