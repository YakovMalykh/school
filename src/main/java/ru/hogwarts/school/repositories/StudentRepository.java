package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int from, int to);

    Student findByName(String name);

    @Query(value = "SELECT COUNT(*) as total_students FROM student", nativeQuery = true)
    Integer countStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double averageAge();

    @Query(value = "select *from student order by id desc limit 5", nativeQuery = true)
    List<Student> lastFiveStudents();

}
