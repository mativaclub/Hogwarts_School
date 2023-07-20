package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;


    @Test
    public void shouldCreateStudent() {
        Student student = new Student(1L, "Anna", 25);
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(studentService.create(student), student);
    }

    @Test
    public void shouldFindStudent() {
        Student student = new Student(1L, "Anna", 25);
        Optional<Student> studentOptional = Optional.of(student);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(studentService.read(student.getId()), studentOptional);
    }

    @Test
    public void shouldUpdateStudent() {
        Student student = new Student(1L, "Anna", 25);
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(studentService.update(student), student);
    }

    @Test
    public void shouldDeleteStudent() {
        Student student = new Student(1L, "Anna", 25);
        when(studentRepository.save(student)).thenReturn(student);
        studentRepository.save(student);
        studentService.delete(student.getId());
//        when(studentRepository.deleteById(student.getId())).thenReturn(null);
        assertEquals(studentService.read(student.getId()), Optional.empty());
    }

    @Test
    public void shouldFilterStudentByAge() {
        Student student1 = new Student(1L, "Anna", 25);
        Student student2 = new Student(2L, "Maria", 30);
        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));
        assertEquals(studentService.filterByAge(student2.getAge()), List.of(student2));
    }


    @Test
    public void shouldReturnAllStudents() {
        Student student1 = new Student(1L, "Anna", 25);
        Student student2 = new Student(2L, "Maria", 30);
        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));
        assertEquals(studentService.findStudents(), List.of(student1, student2));
    }

}
