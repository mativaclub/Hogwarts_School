package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId,
                                               @RequestParam MultipartFile avatar) {
        // MultipartFile - это в спринге интерфейс для загруженных файлов-то есть при загрузке файл попадает в переменную avatar
        try {
            avatarService.uploadAvatar(studentId, avatar);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    //Это эндпоинт, который прослушивает POST-запросы по URL "/{studentId}/avatar".
// В адресе запроса передаем идентификатор студента, к которому относятся загружаемые данные.
    //Это значит, что приложение ожидает в запросе тип содержимого multipart/form-data.
// Именно он используется для передачи бинарных данных методом POST.
    //Из тела запроса получаем объект класса MultipartFile.
    // Он содержит всю информацию о загружаемом файле, начиная с пути, где он лежит,
    // заканчивая размером файла


    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatarFromDB(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("".getBytes());
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatarFromFile(@PathVariable Long id, HttpServletResponse response)
            throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<List<Avatar>> getAvatarsByPage(@RequestParam int page,
                                                         @RequestParam int size) {
        return ResponseEntity.ok(avatarService.pagesOFAvatar(page, size));
    }
}
