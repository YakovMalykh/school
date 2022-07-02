package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("был вызван метод createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("был вызван метод findStudent");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.debug("был вызван метод editStudent");
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        logger.debug("был вызван метод removeStudent");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.debug("был вызван метод getAllStudents");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.debug("был вызван метод findStudentsByAge");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int from, int to) {
        logger.debug("был вызван метод findByAgeBetween");
        return studentRepository.findByAgeBetween(from, to);
    }

    public Faculty getFacultyOfStudent(long studentId) {
        logger.debug("был вызван метод getFacultyOfStudent");
        return findStudent(studentId).getFaculty();
    }

    public Integer getNumberOfStudents() {
        logger.debug("был вызван метод getNumberOfStudents");
        return studentRepository.countStudents();
    }

    public Double getAverageAge() {
        logger.debug("был вызван метод getAverageAge");
        return studentRepository.averageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("был вызван метод getLastFiveStudents");
        return studentRepository.lastFiveStudents();
    }
}
