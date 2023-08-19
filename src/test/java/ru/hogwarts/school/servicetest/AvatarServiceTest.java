//package ru.hogwarts.school.servicetest;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.hogwarts.school.model.Avatar;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.repository.AvatarRepository;
//import ru.hogwarts.school.repository.StudentRepository;
//import ru.hogwarts.school.service.AvatarService;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//import static net.bytebuddy.matcher.ElementMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class AvatarServiceTest {
//
//    @Mock
//    private AvatarRepository avatarRepository;
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    @InjectMocks
//    private AvatarService avatarService;
//
//
//
//    @Test
//    public void uploadAvatarTest() {
//        Student student = new Student(2L, "name", 20);
//
//        try (
//                InputStream inputStream = new FileInputStream("test.png")
//        ) {
//            MockMultipartFile mockMultipartFile = new MockMultipartFile("name", inputStream);
//            when(studentRepository.getReferenceById(2L)).thenReturn(student);
////            when(avatarRepository.save(any(Avatar.class))).thenReturn()
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//}
















//@ExtendWith(MockitoExtension.class)
//class AvatarServiceTest {
//
//    private static final byte[] IMAGE = new byte[]{1, 2, 3, 4, 5, 6};
//
//    @Mock
//    AvatarRepository avatarRepository;
//    @Mock
//    StudentRepository studentRepository;
//
//    AvatarService service;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        var path = Files.createTempDirectory("avatar_test");
//        service = new AvatarService(avatarRepository, studentRepository, path.toString(), true);
//    }
//
//    @Test
//    void testFind() {
//        var a = new Avatar();
//        a.setId(1L);
//        a.setData(IMAGE);
//        when(avatarRepository.findByStudentId(anyLong())).thenReturn(Optional.empty());
//        when(avatarRepository.findByStudentId(1L)).thenReturn(Optional.of(a));
//
//        var nonNullResult = service.findAvatar(1L);
//        assertEquals(nonNullResult.getId(), 1L);
//
//        // странный тест
//        var nullResult = service.findAvatar(2L);
//        assertEquals(nullResult.getId(), 0);
//    }
//
//    @Test
//    void testUpload() {
//        var s = new Student();
//        s.setId(1L);
//        s.setName("test");
//        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(s));
//
//        //MockMultipartFile mockMultipartFile = new MockMultipartFile();
//        //service.upload();
