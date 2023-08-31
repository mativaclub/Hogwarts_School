package ru.hogwarts.school.webmvc;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMVCTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FacultyRepository facultyRepository;

    //Для использования базы без ее изменений

    @SpyBean
    FacultyService facultyService;
    //связь с логикой
    @InjectMocks
    FacultyController facultyController;


    @Test
    public void createFacultyTest() throws Exception {
        final Long id = 1L;
        final String name = "Test";
        final String color = "testColor";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("id", id);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty") //send
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/1") //send
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1") //send
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); //receive

//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/faculty/filter/" + color) //send
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) //receive
//                .andExpect(jsonPath("$[0].id").value(id))
//                .andExpect(jsonPath("$[0].name").value(name))
//                .andExpect(jsonPath("$[0].color").value(color));

    }

    @Test
    public void filterFacultyByNameTest() throws Exception {
        final Long id = 1L;
        final String name = "Test";
        final String color = "testColor";
        Faculty faculty1 = new Faculty(id, name, color);
        Faculty faculty2 = new Faculty(2L, "name", "color");
        when(facultyRepository.findByNameIgnoreCase("Test")).thenReturn(List.of(faculty1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter/name?name=Test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].id").value(id));
    }

    @Test
    public void filterFacultyByColorTest() {
    }

    @Test
    public void getFacultyOfStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Test";
        final String color = "testColor";
        Faculty faculty1 = new Faculty(id, name, color);
        Student student1 = new Student(1L, "Student1", 21);
        student1.setFaculty(faculty1);
        Student student2 = new Student(2L, "Student2", 22);
        student2.setFaculty(faculty1);
        when(facultyRepository.findByStudents_id(1L)).thenReturn(faculty1);
        when(facultyRepository.findByStudents_id(2L)).thenReturn(faculty1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getFacultyOfStudent?studentId=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void filterFacultyTest() {
    }

    @Test
    public void findFacultyById() throws Exception {
        final Long id = 1L;
        final String name = "Test";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("id", id);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

}
