package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTestsRestTemplate {

    @LocalServerPort
    private int port;

    private String testName = "ivanTest";
    private int testAge = 20;


    @Autowired
    private StudentRepository studentRepository;// заинжектил репозиторий, чтобы перед
    // проверкой записывать в БД нужный элемент

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setName(testName + "post");
        student.setAge(testAge);

        Assertions
                .assertThat(this.testRestTemplate.postForObject(
                        "http://localhost:" + port + "/student", student, Student.class))
                .isNotNull();
        //удаляю созданный при тесте элемент
        Student testStudent = studentRepository.findByName(testName + "post");
        clearAfterTest(testStudent);
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setName(testName + "byId");
        student.setAge(testAge);

        studentRepository.save(student);//иначе не получилось получить заданный эелемент,
        // только если указывать конкретный ID из БД, но в таком случае, если в какой-то момент этот
        // элемент удалят из БД, тест провалится, поэтому заинжектил репозиторий...

        Long id = student.getId();

        Assertions
                .assertThat(this.testRestTemplate.getForObject(
                        "http://localhost:" + port + "/student/" + id, Student.class))
                .isEqualTo(student);

        //удаляю созданный при тесте элемент
        clearAfterTest(student);
    }


    @Test
    public void testGetListOfStudents() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForEntity(
                        "http://localhost:" + port + "/student", Collection.class
                )).isNotNull();

        Assertions
                .assertThat(this.testRestTemplate.getForEntity(
                        "http://localhost:" + port + "/student?ageFrom=19&ageTo=20", Collection.class
                )).isNotNull();
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student = new Student();
        student.setName(testName);
        student.setAge(testAge);

        studentRepository.save(student);
        assertEquals(student.getName(), testName);

        student.setName("Miron");

        this.testRestTemplate.put("http://localhost:" + port + "/student", student);
        assertEquals(student.getName(), "Miron");

        //удаляю созданный при тесте элемент
        clearAfterTest(student);

    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName(testName + "delete");
        student.setAge(testAge);

        studentRepository.save(student);

        Long id = student.getId();
        assertTrue(studentRepository.existsById(id));

        this.testRestTemplate.delete(
                "http://localhost:" + port + "/student/" + id);

        assertFalse(studentRepository.existsById(id));
    }

    @Test
    public void testGetStudentsByAge() {
        Assertions
                .assertThat(this.testRestTemplate.getForEntity(
                        "http://localhost:" + port + "/student/byage/20", Collection.class))
                .isNotNull();
    }

    @Test
    public void testGetFacultyOfStudent() {
        Student student = new Student();
        student.setName(testName + "faculty");
        student.setAge(testAge);

        studentRepository.save(student);
        Long id = student.getId();

        Assertions.assertThat(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/student/getFaculty?studentId=" + id, Faculty.class
        )).isNull();

        //удаляю созданный при тесте элемент
        clearAfterTest(student);
    }

    private void clearAfterTest(Student student) {
        Long id = student.getId();
        this.testRestTemplate.delete(
                "http://localhost:" + port + "/student/" + id);
    }

}
