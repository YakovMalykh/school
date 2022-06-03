package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private HashMap<Long, Student> allStudents = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        if (!allStudents.containsValue(student)) {
            student.setId(++lastId);
            allStudents.put(lastId, student);
            return student;
        }
        return null;
    }

    public Student findStudent(long id) {

        return allStudents.get(id);
    }

    public Student editStudent(Student student) {
        if (allStudents.containsKey(student.getId()) &&
                !allStudents.containsValue(student)) {
            allStudents.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student removeStudent(long id) {

        return allStudents.remove(id);
    }

    public Collection<Student> getAllStudents() {

        return allStudents.values();
    }

    public Collection<Student> findStudentsByAge(int age) {
        return allStudents.values().stream().filter(e -> e.getAge() == age).
                collect(Collectors.toList());
    }

}
