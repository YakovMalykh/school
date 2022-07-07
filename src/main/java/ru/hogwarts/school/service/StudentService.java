package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
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

    public List<String> getAllStudentsNameStartedWith(String partName) {
//        решил сделать с вводом параметра, хотя по заданию требовалась только "А"
        logger.debug("был вызван метод getAllStudentsNameStartedWith");
        return studentRepository.findAll()
                .stream()
                .map(e -> e.getName())
                .filter(e -> e.startsWith(partName))
                .map(e -> e.toUpperCase())
                .sorted()
                .collect(Collectors.toList());
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

    public Double getAverageAgeByFindAllMethod() {
        logger.debug("был вызван метод getAverageAgeByFindAllMethod");
        return studentRepository.findAll()
                .stream()
                .mapToDouble(e -> e.getAge())
                .average()
                .getAsDouble();

    }

    public List<Student> getLastFiveStudents() {
        logger.debug("был вызван метод getLastFiveStudents");
        return studentRepository.lastFiveStudents();
    }

    private List<String> getSortedListOfStudentsNames() {
//        logger.debug("был вызван метод getAllStudentsNameParallelStreamTask");
        return studentRepository.getAllStudentsSortedByName().stream()
                .map(Student::getName)
                .toList();

// сначала пытался все в этом методе сделать, но при печати в консоль выводились потоки не парарллельно,
//        а поочереди все равно. Вынес печать и обращение к этому приватному методу в отдельный метод, где уже
//        обращаюсь к методу в разных потоках

//        System.out.println("mainThread " + studentsName.get(0));
//        System.out.println("mainThread " + studentsName.get(1));
//
//        new Thread(() -> {
//            System.out.println("secondThread " + studentsName.get(3));
//            System.out.println("secondThread " + studentsName.get(4));
//            System.out.println("secondThread " + studentsName.get(5));
//        }).start();
//
//        new Thread(() -> {
//            System.out.println("thirdThread " + studentsName.get(6));
//            System.out.println("thirdThread " + studentsName.get(7));
//            System.out.println("thirdThread " + studentsName.get(8));
//        }).start();
//
//        System.out.println("mainThread " + studentsName.get(9));
//        System.out.println("mainThread " + studentsName.get(2));

    }

    public void parallelTask() {

        Thread thread_2 = new Thread(() -> {
            System.out.println("thread 2: " + getSortedListOfStudentsNames().get(3));
            System.out.println("thread 2: " + getSortedListOfStudentsNames().get(4));
            System.out.println("thread 2: " + getSortedListOfStudentsNames().get(5));
        });

        Thread thread_3 = new Thread(() -> {
            System.out.println("thread 3: " + getSortedListOfStudentsNames().get(6));
            System.out.println("thread 3: " + getSortedListOfStudentsNames().get(7));
            System.out.println("thread 3: " + getSortedListOfStudentsNames().get(8));
        });

        thread_2.start();
        thread_3.start();
        System.out.println("thread 1: " + getSortedListOfStudentsNames().get(0));
        System.out.println("thread 1: " + getSortedListOfStudentsNames().get(1));
        System.out.println("thread 1: " + getSortedListOfStudentsNames().get(2));
        System.out.println("thread 1: " + getSortedListOfStudentsNames().get(9));


    }

    //    private int count = 0;
    private Object flag = new Object();

    private void synchronizedPrintName(String name) {
        System.out.println(name);
//        сначала пытался синхронизировать через этот метод, но обеспечить порядок, как в полученном списке
//        не удавалось. Введение count (ее подставлял в параметр getSortedListOfStudentsNames().get(count))
//        и ее инкрементация после вызова метода и без доп-й синхронизации обеспечили порядок первоначальный,
//        так и не понял почему, ведь потоки параллельно должны были вызывать метод, и даже если он был помечен
//        ключ словом sinchronized они могли вызывать его поочереди, но не по порядку.


//        count++;
//        if (count == getSortedListOfStudentsNames().size()) {
//            count=0;
//        }
    }


    public void synchronizedGetNames() {
        synchronizedPrintName(getSortedListOfStudentsNames().get(0));
        synchronizedPrintName(getSortedListOfStudentsNames().get(1));
        synchronizedPrintName(getSortedListOfStudentsNames().get(2));
        synchronizedPrintName(getSortedListOfStudentsNames().get(3));

        new Thread(() -> {
            synchronized (flag) {
                synchronizedPrintName(getSortedListOfStudentsNames().get(4));
                synchronizedPrintName(getSortedListOfStudentsNames().get(5));
                synchronizedPrintName(getSortedListOfStudentsNames().get(6));
            }
        }
        ).start();

        new Thread(() -> {
            synchronized (flag) {
                synchronizedPrintName(getSortedListOfStudentsNames().get(7));
                synchronizedPrintName(getSortedListOfStudentsNames().get(8));
                synchronizedPrintName(getSortedListOfStudentsNames().get(9));
            }
        }
        ).start();

    }

}
