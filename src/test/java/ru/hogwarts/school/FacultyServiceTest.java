package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    @Test
    public void shouldCreateFaculty() {
        Faculty faculty = new Faculty(1L, "Anna", "Red");
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        assertEquals(facultyService.create(faculty), faculty);
    }
//    @Test
//    public void shouldCreateFaculty1() {
//        Faculty expectedFaculty = new Faculty(1L, "Anna", "Red");
//        Faculty actualFaculty = new Faculty(1L, "Anna", "Red");
//        when(facultyRepository.save(expectedFaculty)).thenReturn(actualFaculty);
//        assertEquals(facultyService.create(expectedFaculty), actualFaculty);
//    }

    @Test
    public void shouldFindStudent() {
        Faculty faculty = new Faculty(1L, "Anna", "Red");
        Optional<Faculty> studentOptional = Optional.of(faculty);
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        assertEquals(facultyService.read(faculty.getId()), studentOptional);
    }

    @Test
    public void shouldUpdateStudent() {
        Faculty faculty = new Faculty(1L, "Anna", "Red");
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        assertEquals(facultyService.update(faculty), faculty);
    }

    @Test
    public void shouldDeleteStudent() {
        Faculty faculty = new Faculty(1L, "Anna", "Red");
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        facultyRepository.save(faculty);
        facultyService.delete(faculty.getId());
//        when(studentRepository.deleteById(student.getId())).thenReturn(null);
        assertEquals(facultyService.read(faculty.getId()), Optional.empty());
    }

    @Test
    public void shouldFilterStudentByAge() {
        Faculty faculty1 = new Faculty(1L, "Anna", "Red");
        Faculty faculty2 = new Faculty(2L, "Maria", "Green");
        when(facultyRepository.findAll()).thenReturn(List.of(faculty1, faculty2));
        assertEquals(facultyService.findByColorIgnoreCase(faculty2.getColor()), List.of(faculty2));
    }


    @Test
    public void shouldReturnAllStudents() {
        Faculty faculty1 = new Faculty(1L, "Anna", "Red");
        Faculty faculty2 = new Faculty(2L, "Maria", "Green");
        when(facultyRepository.findAll()).thenReturn(List.of(faculty1, faculty2));
        assertEquals(facultyService.findFaculties(), List.of(faculty1, faculty2));
    }

}