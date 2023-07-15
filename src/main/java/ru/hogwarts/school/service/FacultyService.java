package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long counterId = 0;
    public Faculty create(Faculty faculty) {
        counterId++;
        faculty.setId(counterId);  //faculty.setId(++counterId)  without upper counterId++
        return faculties.put(counterId, faculty);
    }

    public Faculty read(long searchById) {
        if (faculties.containsKey(searchById)) {
            return faculties.get(searchById);
        }
        return null;
    }

    public Faculty updateName(long updateById, String updateName) {
        if (faculties.containsKey(updateById)) {
            Faculty existFaculty = faculties.get(updateById);
            existFaculty.setName(updateName);
            return existFaculty;
        }
        return null;
    }

    public Faculty updateColor(long updateById, String updateColor) {
        if (faculties.containsKey(updateById)) {
            faculties.get(updateById).setColor(updateColor);
            return faculties.get(updateById);
        }
        return null;
    }

    public Faculty updateAll(long updateById, Faculty updateInfo) {
        if (faculties.containsKey(updateById)) {
            return faculties.put(updateById, updateInfo);
        }
        return null;
    }

    public Faculty update(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty delete(long deleteById) {
        if (faculties.containsKey(deleteById)) {
            return faculties.remove(deleteById);
        }
        return null;
    }

}
