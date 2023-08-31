package ru.hogwarts.school.controllertest;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
//это будет тест на рандомном порту, автоматически внутри спринга

    @LocalServerPort
    private int localPort;
    //создали переменную для хранения порта на котором загружено приложение

    @Autowired
    private FacultyController facultyController;
    //создали обьект для тестирования и аутовайрд - для того чтобы автоматически инициализировал this.

    @Autowired
    private TestRestTemplate testRestTemplate;
    //готовый тестресттемплейт спринга - служит для тестирования по шаблонным методам которые есть в нем

    @Test
    void testIfControllerExists() {
//        System.out.println(localPort);
        Assertions.assertThat(facultyController).isNotNull();
    }

    //Проверяем что контроллер существует и проинициализирован
    @Test
    void getFacultyById() {
        Faculty faculty = new Faculty(2L, "Two", "White");
        Faculty result = testRestTemplate.getForObject("http://localhost:" + localPort +
                "/faculty/2", Faculty.class);
        Assertions.assertThat(result.getId()).isEqualTo(2L);
        Assertions.assertThat(result.getName()).isEqualTo("Two");
        Assertions.assertThat(result.getColor()).isEqualTo("White");
    }

    @Test
    void createNewFaculty() throws Exception {
        Faculty actualFaculty = new Faculty();
        actualFaculty.setName("Test");
        actualFaculty.setColor("Black");
        Faculty result = testRestTemplate.postForObject("http://localhost:" +
                localPort + "/faculty", actualFaculty, Faculty.class);
        Assertions.assertThat(result.getName()).isEqualTo("Test");
        Assertions.assertThat(result.getColor()).isEqualTo("Black");
        Assertions.assertThat(result.getId()).isNotNull();
    }

    @Test
    void updateFaculty() throws Exception {
        Faculty updateFaculty = new Faculty();
        updateFaculty.setName("One");
        updateFaculty.setColor("Red");
        testRestTemplate.put("http://localhost:" + localPort + "/faculty/{id}",
                updateFaculty, Faculty.class);
        Assertions.assertThat(updateFaculty.getName()).isEqualTo("One");
        Assertions.assertThat(updateFaculty.getColor()).isEqualTo("Red");
    }

    @Test
    void deleteFaculty() {
        testRestTemplate.delete("http://localhost:" + localPort + "/faculty/{id}",
                Map.of("id", 1L));
        //Мы создаем мапу и кладем туда Id того faculty, которого должны удалить
        Faculty result = testRestTemplate.getForObject("http://localhost:" + localPort +
                "/faculty/1", Faculty.class);
        //Сравнивает действительно ли был удален
        Assertions.assertThat(result).isNull();
    }

    @Test
    void filterFacultyByName() {
        ResponseEntity<Collection<Faculty>> result =
                testRestTemplate.exchange("http://localhost:" + localPort +
                                "/faculty/filter/name?name=two", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Collection<Faculty>>() {
                        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getBody()).hasSize(1);
    }

    @Test
    void filterFacultyByColor() {
        ResponseEntity<Collection<Faculty>> result =
                testRestTemplate.exchange("http://localhost:" + localPort +
                                "/faculty/filter/color?color=white", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Collection<Faculty>>() {
                        });
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getBody()).hasSize(1);
    }

}
