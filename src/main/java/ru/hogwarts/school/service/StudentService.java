package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private static long counterId = 0;

//    public Student createStudent() {
//
//    }
//
//    public Student readStudent() {
//
//    }
//
//    public Student updateStudent() {
//
//    }
//
//    public Student deleteStudent() {
//
//
//    }










    public Student addStudent(Student student) {
        student.setId(++counterId);
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }


}
