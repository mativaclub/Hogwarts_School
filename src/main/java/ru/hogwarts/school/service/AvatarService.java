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

    @Value("${path.to.avatars.folder}") //это ссылка на папку где аватары хранятся - в пропертис то есть path.to.avatars.folder
    private String avatarsDir; //она хранит значение пропертис - то есть /avatars
    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        //MultipartFile комментарий в контроллере

        //Path. Это интерфейс. В нем будем хранить путь до директории с загружаемыми файлами.
        //avatarsDir — это переменная, которая содержит значение из свойств.

        Student student = studentRepository.getReferenceById(studentId); //getById это старый
        String fileResource = new File("src\\main\\resources").getAbsolutePath();
        // указываем где будет находиться наша папка с аватарами - resources - это все с чем работает джава
        String fullAvatarsDir = fileResource + "\\" + avatarsDir;
        //формируем полный путь до папки аватара
        Path filePath = Path.of(fullAvatarsDir, student + "." +
                getExtensions(avatar.getOriginalFilename()));
        //формируется название полного файла с расширением
        Files.createDirectories(filePath.getParent());
        //берет родителя - папку аватарс - и создает если ее нету
        Files.deleteIfExists(filePath);
        //если етсь аватар по такому имени то его удаляем
        try (
                InputStream is = avatar.getInputStream();//Получаем поток из аватара который пришел - загружается аватар в поток
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW); //новый поток для записи нового файла в который запишем новый аватар
                BufferedInputStream bis = new BufferedInputStream(is, 1024); //будем считывать по одному мб - это сжатый поток
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);//будем записывать по одному мб - это сжатый поток
        ) {
            bis.transferTo(bos);//перенос из потока ввода в поток вывода - результат - файл на диске
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
