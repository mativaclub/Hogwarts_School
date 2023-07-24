package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> read(long searchById) {
        return studentRepository.findById(searchById);
    }


    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long deleteById) {
        studentRepository.deleteById(deleteById);
    }

    @GetMapping
    public Collection<Student> findStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> filterByAge(int age) {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .filter(el -> el.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int fromAge, int toAge) {
//        return studentRepository.findAll().stream()
//                .filter(el -> el.getAge() >= fromAge && el.getAge() <= toAge)
//                .collect(Collectors.toList());
        return studentRepository.findByAgeBetween(fromAge, toAge);
    }

    public Collection<Student> findStudentsByFaculty_Id(long facultyId) {
        return studentRepository.findStudentsByFaculty_Id(facultyId);

//        return studentRepository.findAll().stream()
//                .filter(student -> student.getFaculty() != null &&
//                        student.getFaculty().getId().equals(facultyId))
//                .collect(Collectors.toList());
    }


}
