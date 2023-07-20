package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Collection<Student> filterByAge(int age) {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .filter(el -> el.getAge() == age)
                .collect(Collectors.toList());

    }



}
