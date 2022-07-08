package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {

        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public Collection<Student> getDifferentListOfStudents(
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo
    ) {
        if (ageFrom != null && ageFrom != null) {
            return studentService.findByAgeBetween(ageFrom, ageTo);
        }

        return studentService.getAllStudents();
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {

        return studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byage/{age}")
    public Collection<Student> getStudentsByAge(@PathVariable int age) {

        return studentService.findStudentsByAge(age);
    }

    @GetMapping("/getFaculty")
    public Faculty getfacultyOfStudent(@RequestParam long studentId) {
        return studentService.getFacultyOfStudent(studentId);
    }

    @GetMapping("/number-of-students")
    public Integer getNumberOfStudents() {
        return studentService.getNumberOfStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/average-age-second-method")
    public Double getAverageAgeSecondMethod() {
        return studentService.getAverageAgeByFindAllMethod();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/by-part-name")
    public List<String> getAllStudentsNameStartedWith(@RequestParam String partName) {
        return studentService.getAllStudentsNameStartedWith(partName);
    }
    @GetMapping("getStudents-parallel")
    public void getAllStudentsNameParallelStream() {
        studentService.parallelTask();
    }

    @GetMapping("/synchronizeStreams")
    public void getAllStudentsBySynchronisedMethod() {
        studentService.synchronizedGetNames();
    }
}
