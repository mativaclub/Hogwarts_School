package ru.hogwarts.school.controller;

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
    public Optional<Faculty> getById(@PathVariable long id) {
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
    public ResponseEntity delete(@PathVariable long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/filter")
    public Collection<Faculty> filterByColor(@RequestParam("color") String color) {
        return facultyService.filterByColor(color);
    }

}
