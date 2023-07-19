package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private static long counterId = 0;

    public Student create(Student student) {
        counterId++;
        student.setId(counterId);  //faculty.setId(++counterId)  without upper counterId++
        return students.put(counterId, student);
    }

    public Student read(long searchById) {
        if (students.containsKey(searchById)) {
            return students.get(searchById);
        }
        return null;
    }

    public Student updateName(long updateById, String updateName) {
        if (students.containsKey(updateById)) {
            Student existStudent = students.get(updateById);
            existStudent.setName(updateName);
            return existStudent;
        }
        return null;
    }

    public Student updateColor(long updateById, int updateAge) {
        if (students.containsKey(updateById)) {
            students.get(updateById).setAge(updateAge);
            return students.get(updateById);
        }
        return null;
    }


    public Student updateAll(long updateById, Student updateInfo) {
        if (students.containsKey(updateById)) {
            return students.put(updateById, updateInfo);
        }
        return null;
    }

    public Student update(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student delete(long deleteById) {
        if (students.containsKey(deleteById)) {
            return students.remove(deleteById);
        }
        return null;
    }

    public Map<Long, Student> filterByAge(int age) {
        return students.entrySet().stream()
                .filter(el -> el.getValue().getAge() == (age))
                .collect(Collectors.toMap(el -> el.getKey(), el -> el.getValue()));
    }



}
