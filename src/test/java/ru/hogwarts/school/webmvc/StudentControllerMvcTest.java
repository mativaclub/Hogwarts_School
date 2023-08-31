package ru.hogwarts.school.webmvc;

import net.minidev.json.JSONObject;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentRepository studentRepository;

    //Для использования базы без ее изменений

    @SpyBean
    StudentService studentService;
    //связь с логикой
    @InjectMocks
    StudentController studentController;


    @Test
    public void createStudentTest() throws Exception {
        final int age = 1;
        final String name = "Test";
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setAge(age);
        student.setName(name);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        //когда вызываем save репозитория то передаем любого - any студента и хотим получить исходного студента - которого создали

        mockMvc.perform(MockMvcRequestBuilders  // RequestBuilders по частям собираем обьект
                        .post("/student") //то что сейчас тестируем
                        .content(studentObject.toString()) //есть ли какие-то данные - тут передает студента в строковом формате
                        .contentType(MediaType.APPLICATION_JSON) //передаем в формате JSON
                        .accept(MediaType.APPLICATION_JSON))  //принимает ответ также в виде JSON
                .andExpect(status().isOk()) // какой статус ожидаем после выполенения - 200
                .andExpect(jsonPath("$.age").value(age)) //надо проверить что в строке есть id соответсвующий тому который мы создали
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void getStudentTest() throws Exception {
        final int age = 1;
        final String name = "Test";
        final Long id = 1L;
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        student.setId(id);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        //findById возвращает Optional  и тут мы показываем программе как работает метод findById так как репозиторий у нас мок и программа не знает как он работает
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON)) //принимает ответ также в виде JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void updateStudentTest() throws Exception {
        final int age = 1;
        final String name = "Test";
        final Long id = 1L;
        JSONObject studentObject = new JSONObject();
        studentObject.put("age", age);
        studentObject.put("name", name);
        studentObject.put("id", id);
        //Если мы отдаем студента у которого есть body то создаем JSONObject
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        student.setId(id);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/1")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        final int age = 1;
        final String name = "Test";
        final Long id = 1L;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setAge(age);
        student.setName(name);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        //когда вызываем save репозитория то передаем любого - any студента и хотим получить исходного студента - которого создали

        mockMvc.perform(MockMvcRequestBuilders  // RequestBuilders по частям собираем обьект
                        .post("/student") //то что сейчас тестируем
                        .content(studentObject.toString()) //есть ли какие-то данные - тут передает студента в строковом формате
                        .contentType(MediaType.APPLICATION_JSON) //передаем в формате JSON
                        .accept(MediaType.APPLICATION_JSON))  //принимает ответ также в виде JSON
                .andExpect(status().isOk()) // какой статус ожидаем после выполенения - 200
                .andExpect(jsonPath("$.age").value(age)) //надо проверить что в строке есть id соответсвующий тому который мы создали
                .andExpect(jsonPath("$.name").value(name));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1"))
                .andExpect(status().isOk());
    }


    @Test
    public void filterByAgeTest() throws Exception {
        List<Student> students = new ArrayList<>(List.of(
                new Student(1L, "Test1", 1),
                new Student(2L, "Test2", 1)
        ));
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filterByAge?age=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[0].name").value("Test1"));
    }

    @Test
    public void filterByAgeBetweenTest() throws Exception {
        List<Student> students = new ArrayList<>(List.of(
                new Student(1L, "Test1", 1),
                new Student(2L, "Test2", 4),
                new Student(4L, "Test4", 9),
                new Student(3L, "Test3", 6)
        ));
        List<Student> filterStudents = students.stream()
                .filter((el) -> el.getAge() >= 1 && el.getAge() <= 6)
                .toList();
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(filterStudents);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filterBetween?from=1&to=6")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[1].age").value(4))
                .andExpect(jsonPath("$.[2].age").value(6));
    }

    @Test
    public void getStudentByFacultyTest() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Name1", "Color1");
        Faculty faculty2 = new Faculty(2L, "Name2", "Color2");
        Student student1 = new Student(1L, "Test1", 1);
        Student student2 = new Student(2L, "Test2", 2);
        student1.setFaculty(faculty1);
        student2.setFaculty(faculty2);
        when(studentRepository.findStudentsByFaculty_Id(3)).thenReturn(Collections.emptyList());
        when(studentRepository.findStudentsByFaculty_Id(2)).thenReturn(List.of(student2));
        when(studentRepository.findStudentsByFaculty_Id(1)).thenReturn(List.of(student1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filterByFaculty?facultyId=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(2));
        //[0] тут уже student2 становиться нулевым по порядку
    }
}
