package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {

        //Path. Это интерфейс. В нем будем хранить путь до директории с загружаемыми файлами.
        //avatarsDir — это переменная, которая содержит значение из свойств.

        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." +
                getExtensions(avatar.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatar.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Avatar currentAvatarToDB = findAvatar(studentId); //if was present before and put it in DB
        if (currentAvatarToDB == null) {
            currentAvatarToDB = new Avatar();
        }
        currentAvatarToDB.setStudent(student);
        currentAvatarToDB.setFilePath(filePath.toString());  //from path get string, because it gets only string
        currentAvatarToDB.setFileSize(avatar.getSize());   //when we get new avatar, we get its size and set to current avatar
        currentAvatarToDB.setMediaType(avatar.getContentType());  //which type is avatar
        currentAvatarToDB.setData(avatar.getBytes());  //from avatar get bytes and put it in database
        avatarRepository.save(currentAvatarToDB);
    }

    public Avatar findAvatar(Long studentId) {
        Optional<Avatar> avatar = avatarRepository.findByStudentId(studentId);
        if (avatar.isPresent()) {
            return avatar.get();
        }
        return null;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
