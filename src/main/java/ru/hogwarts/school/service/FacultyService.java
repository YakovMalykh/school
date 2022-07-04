package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("был вызван метод createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("был вызван метод findFaculty");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("был вызван метод editFaculty");
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(long id) {
        logger.debug("был вызван метод removeFaculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.debug("был вызван метод getAllFaculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String str) {
        logger.debug("был вызван метод findByNameOrColor");
        return facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(str, str);
    }

    public Collection<Student> getStudentsOfFaculty(Long facultyId) {
        logger.debug("был вызван метод getStudentsOfFaculty");
        return findFaculty(facultyId).getStudents();
    }

    public String getMostLongFacultyName() {
        logger.debug("был вызван метод getMostLongFacultyName");
        return facultyRepository.findAll()
                .stream()
                .map(f -> f.getName())
                .max(Comparator.comparing(s -> s.toCharArray().length))
                .get();
    }

}
