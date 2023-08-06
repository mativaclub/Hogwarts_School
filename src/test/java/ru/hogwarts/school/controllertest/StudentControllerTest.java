package ru.hogwarts.school.controllertest;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//это будет тест на рандомном порту, автоматически внутри спринга
public class StudentControllerTest {

    @LocalServerPort
    private int localPort;
    //создали переменную для хранения порта на котором загружено приложение

    @Autowired
    private StudentRepository studentRepository;
    //заинджектили репозиторий, чтобы потом сделать мметод и удалить данные которые будут в результате тестов в базе данных

    @Autowired
    private StudentController studentController;
    //создали обьект для тестирования и аутовайрд - для того чтобы автоматически инициализировал this.

    @Autowired
    private TestRestTemplate testRestTemplate;
    //готовый тестресттемплейт спринга - служит для тестирования по шаблонным методам которые есть в нем

    @AfterEach
    void setUp() {
        studentRepository.deleteAll();
    }

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
    void createNewStudent() throws Exception {
        //Для выбрасывания ошибки требует Идея для проброски выше в спринг
        JSONObject newStudent = new JSONObject();
        //Мы создаем JSON для наполнения данными и отправки
        newStudent.put("name", "Eva");
        newStudent.put("age", 40);
        JSONObject facultyForStudent = new JSONObject();
        //Создаем факультет для студента чтобы передать в JSON
        facultyForStudent.put("id", 2);
        //пишет какой факультет для студента
        newStudent.put("faculty", facultyForStudent);
        //кладем в студента поле факультет с обьектом JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Наполняем его Медиа - JSON, что именно JSON будет использовать в body
        HttpEntity<String> request = new HttpEntity<>(newStudent.toString(), headers);
        //Создаем запрос наполняя строковым значением JSON и заголовками, и этот запрос отправляем в postForObject

//        Student actualStudent = new Student();
//        actualStudent.setName("Olga");
//        actualStudent.setAge(60);
//        Faculty faculty = new Faculty();
//        faculty.setId(3L);
//        actualStudent.setFaculty(faculty);


        Student result = testRestTemplate.postForObject("http://localhost:" +
                localPort + "/student", request, Student.class);
        Assertions.assertThat(result.getName()).isEqualTo("Eva");
        Assertions.assertThat(result.getAge()).isEqualTo(40);
        Assertions.assertThat(result.getId()).isNotNull();
    }


    @Test
    void updateStudent() throws Exception {
        //Для выбрасывания ошибки требует Идея для проброски выше в спринг
        JSONObject newStudent = new JSONObject();
        //Мы создаем JSON для наполнения данными и отправки
        newStudent.put("name", "Eva");
        newStudent.put("age", 40);
        JSONObject facultyForStudent = new JSONObject();
        //Создаем факультет для студента чтобы передать в JSON
        facultyForStudent.put("id", 3);
        //пишет какой факультет для студента
        newStudent.put("faculty", facultyForStudent);
        //кладем в студента поле факультет с обьектом JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Наполняем его Медиа - JSON, что именно JSON будет использовать в body
        HttpEntity<String> request = new HttpEntity<>(newStudent.toString(), headers);
        //Создаем запрос наполняя строковым значением JSON и заголовками, и этот запрос отправляем в postForObject

//        Student updateStudent = new Student(26L, "Peter", 15);
//        Faculty updateFaculty = new Faculty();
//        updateFaculty.setId(2L);
//        updateStudent.setFaculty(updateFaculty);
        Map<String, String> params = new HashMap<>();
        params.put("id", "27");
        testRestTemplate.put("http://localhost:" + localPort + "/student/{id}",
                request, params);
        Student result = testRestTemplate.getForObject("http://localhost:" + localPort +
                "/student/27", Student.class);
        Assertions.assertThat(result.getName()).isEqualTo("Eva");
        Assertions.assertThat(result.getAge()).isEqualTo(40);
    }

    @Test
    void deleteStudent() {
        testRestTemplate.delete("http://localhost:" + localPort + "/student/{id}",
                Map.of("id", 7L));
        //Мы создаем мапу и кладем туда Id того студента, которого должны удалить
        Student result = testRestTemplate.getForObject("http://localhost:" + localPort +
                "/student/7", Student.class);
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
