package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> read(long searchById) {
        return facultyRepository.findById(searchById);
    }

    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(long deleteById) {
       facultyRepository.deleteById(deleteById);
    }


    @GetMapping
    public Collection<Faculty> findFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> filterByColor(String color) {
        List<Faculty> allFaculty = facultyRepository.findAll();
        return allFaculty.stream()
                .filter(el -> el.getColor().equals(color))
                .collect(Collectors.toList());
        }
}























//    public Faculty updateName(long updateById, String updateName) {
//        if (faculties.containsKey(updateById)) {
//            Faculty existFaculty = faculties.get(updateById);
//            existFaculty.setName(updateName);
//            return existFaculty;
//        }
//        return null;
//    }

//    public Faculty updateColor(long updateById, String updateColor) {
//        if (faculties.containsKey(updateById)) {
//            faculties.get(updateById).setColor(updateColor);
//            return faculties.get(updateById);
//        }
//        return null;
//    }

//    public Faculty updateAll(long updateById, Faculty updateInfo) {
//        if (faculties.containsKey(updateById)) {
//            return faculties.put(updateById, updateInfo);
//        }
//        return null;
//    }

//    public List<Faculty> filterByColor(String color) {
//        Map<Long, Faculty> facultyMap = faculties.entrySet().stream()
//                .filter((el) -> el.getValue().getColor().equals(color))
//                .collect(Collectors.toMap(Map.Entry::getKey, el -> el.getValue()));
//        return new ArrayList<>(facultyMap.values());
//    }

//    public Collection<Faculty> filterByColor(String color) {
//        ArrayList<Faculty> result = new ArrayList<>();
//        for (Faculty faculty : faculties.values()) {
//            if (Objects.equals(faculty.getColor(), color)) {
//                result.add(faculty);
//            }
//        }
//        return result;
//    }
//@GetMapping
//public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
//    if (color != null && !color.isBlank()) {
//        return ResponseEntity.ok(facultyService.findByColor(color));
//    }
//    return ResponseEntity.ok(Collections.emptyList());
//}


//    public Collection<Faculty> filterByColor(String color) {
//        ArrayList<Faculty> result = new ArrayList<>();
//        for (Faculty faculty : faculties.values()) {
//            if (Objects.equals(faculty.getColor(), color)) {
//                result.add(faculty);
//            }
//        }
//        return result;
//    }