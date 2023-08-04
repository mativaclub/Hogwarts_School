package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//это будет тест на рандомном порту, автоматически внутри спринга
public class StudentControllerTest {

    @LocalServerPort
    private int localPort;
    //создали переменную для хранения порта на котором загружено приложение

    @Autowired
    private StudentController studentController;
    //создали обьект для тестирования и аутовайрд - для того чтобы автоматически инициализировал this.

    @Autowired
    private TestRestTemplate testRestTemplate;
    //готовый тестресттемплейт спринга - служит для тестирования по шаблонным методам которые есть в нем


    @Test
    void testIfControllerExists() {
//        System.out.println(localPort);
        Assertions.assertThat(studentController).isNotNull();
    }
    //Проверяем что контроллер существует и проинициализирован

//    @Test
//    void getUserById2() {
//        Assertions.assertThat(testRestTemplate.getForObject("http://localhost:" + localPort +
//                "/student/2", String.class)).isEqualTo("{\"id\":2,\"name\":\"Maria\",\"age\":30}");
//    }

    @Test
    void getUserById() {
        Student student = new Student(2L, "Maria", 30);
        Assertions.assertThat(testRestTemplate.getForObject("http://localhost:" + localPort +
                "/student/2", Student.class)).isEqualTo(student);
    }

    @Test
    void createNewStudent() {
        Student actualStudent = new Student();
        actualStudent.setName("Olga");
        actualStudent.setAge(60);
        Faculty faculty = new Faculty();
        faculty.setId(3L);
        actualStudent.setFaculty(faculty);
        Student result = testRestTemplate.postForObject("http://localhost:" +
                localPort + "/student?facultyId=3", actualStudent, Student.class);
        Assertions.assertThat(result.getName()).isEqualTo(actualStudent.getName());
        Assertions.assertThat(result.getAge()).isEqualTo(actualStudent.getAge());
        Assertions.assertThat(result.getId()).isNotNull();
    }


    @Test
    void updateStudent() {
        Student updateStudent = new Student(26L, "Peter", 15);
        Faculty updateFaculty = new Faculty();
        updateFaculty.setId(2L);
        updateStudent.setFaculty(updateFaculty);
        Map<String, String> params = new HashMap<>();
        params.put("id", "26");
        testRestTemplate.put("http://localhost:" + localPort + "/student/26",
                updateStudent, params);
        Student result = testRestTemplate.getForObject("http://localhost:" + localPort +
                "/student/26", Student.class);
        Assertions.assertThat(result.getName()).isEqualTo(updateStudent.getName());
        Assertions.assertThat(result.getAge()).isEqualTo(updateStudent.getAge());
    }

    @Test
    void deleteStudent() {
        testRestTemplate.delete("http://localhost:" + localPort + "/student/26",
                new HashMap<String, Long>() {{
                    put("id", 26L);
                }});
        //Мы создаем мапу и кладем туда Id того студента, которого должны удалить
        Student result = testRestTemplate.getForObject("http://localhost:" + localPort +
                "/student/26", Student.class);
        //Сравнивает действительно ли был удален
        Assertions.assertThat(result).isNull();
    }

    @Test
    void filterStudentsByAge() {
        ResponseEntity<Collection<Student>> result =
                testRestTemplate.exchange("http://localhost:" + localPort +
                                "/student/filter?age=60", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Collection<Student>>() {
                        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getBody()).hasSize(1);
    }

}
