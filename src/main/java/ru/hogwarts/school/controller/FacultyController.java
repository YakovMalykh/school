package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculry(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public Collection<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by")
    public Collection<Faculty> getFacultyByNameOrColor(
            @RequestParam String name,
            @RequestParam String color) {
        return facultyService.findByNameOrColor(name, color);
    }

    @GetMapping("/getStudents")
    public Collection<Student> getStudentsOfFaculty(@RequestParam long faculty_id) {
        return facultyService.getStudentsOfFaculty(faculty_id);
    }

}
