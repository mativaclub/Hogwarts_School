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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    public void getStudent() throws Exception {
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
}
