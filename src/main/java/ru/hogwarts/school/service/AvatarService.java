package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentService studentService;

    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }


    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.debug("был вызван метод uploadAvatar");
        Student student = studentService.findStudent(studentId);// в шпаргалке использован StudentRepository
        Path filePath = Path.of(avatarsDir, student + "." + getExtention(avatarFile.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath,CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is,1024);
                BufferedOutputStream bos = new BufferedOutputStream(os,1024);
                ){
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        logger.debug("был вызван метод findAvatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    public List<Avatar> getAvatarsOfAllStudents(Integer pageNumber, Integer pageSize) {
        logger.debug("был вызван метод getAvatarsOfAllStudents");
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtention(String fileName) {
        logger.debug("был вызван метод getExtention");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String getAvatarsDir() {
        return avatarsDir;
    }
}
