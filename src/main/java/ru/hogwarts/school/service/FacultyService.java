package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculry(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {

        return facultyRepository.findAll();
    }
//решил убарть этот метод, т.к. появлися поиск по имени и цвету
//    public Collection<Faculty> findFacultyByColor(String color) {
//        return facultyRepository.findByColor(color);
//    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(name, color);
    }

    public Collection<Student> getStudentsOfFaculty(Long faculty_id) {
        return findFaculry(faculty_id).getStudents();
    }

}
