package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty create(Faculty faculty) {
        logger.info("Creating faculty");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> read(long searchById) {
        logger.info("searching by id faculty " + searchById);
        return facultyRepository.findById(searchById);
    }

    public Faculty update(Faculty faculty) {
        logger.info("Updating faculty " + faculty);
        return facultyRepository.save(faculty);
    }

    public void delete(long deleteById) {
        logger.warn("Deleting faculty with id " + deleteById);
        facultyRepository.deleteById(deleteById);
    }


    @GetMapping
    public Collection<Faculty> findFaculties() {
        logger.info("Getting all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameIgnoreCase(String name) {
        logger.info("getting faculty by name");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> findByColorIgnoreCase(String color) {
        logger.info("getting faculty by color");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Faculty getFacultyOfStudent(long studentId) {
        logger.info("getting faculty by student id");
        return facultyRepository.findByStudents_id(studentId);

    }

    public Collection<Faculty> findByColorOrNameIgnoreCase(String color, String name) {
        logger.info("getting faculty by color or name of student");
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    public String findLongestName() {
        logger.info("find longest name");
//        int maxLength = facultyRepository.findAll()
//                .stream()
//                .parallel()
//                .mapToInt(el -> el.getName().length())
//                .max()
//                .orElse(0);
        Optional<Faculty> result = facultyRepository.findAll()
                .stream()
                .parallel()
                .max(Comparator.comparingInt(el -> el.getName().length()));
        if (result.isPresent()) {
            return result.get().getName();
        }
        return "";

//                .max((el1, el2) -> el1.getName().length() - el2.getName().length())
//                .filter(el->el.getName().length() == maxLength)
//                .get(0)
//                .getName();
        //if need to return only one name, then using without List, only String
    }
}


//    public Collection<Faculty> filterByColor(String color) {
//        List<Faculty> allFaculty = facultyRepository.findAll();
//        return allFaculty.stream()
//                .filter(el -> el.getColor().equals(color))
//                .collect(Collectors.toList());
//    }


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