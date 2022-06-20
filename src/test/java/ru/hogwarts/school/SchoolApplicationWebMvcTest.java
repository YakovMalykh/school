package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SchoolApplicationWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;


    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private FacultyController facultyController;

    private String testName = "факультет";
    private String testColor = "цветаной";
    private Long id = 8L;

    @Test
    public void testPostFaculty() throws Exception {

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", testName);
        facultyObject.put("color", testColor);

        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);
        faculty.setId(id);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(testName))
                .andExpect(jsonPath("$.color").value(testColor));
    }

    @Test
    public void testGetFacultyById() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);
        faculty.setId(id);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + any(Long.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(testName))
                .andExpect(jsonPath("$.color").value(testColor));
    }

    @Test
    public void testGetAllFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);
        faculty.setId(id);

        Faculty faculty2 = new Faculty();
        faculty2.setName(testName + 2);
        faculty2.setColor(testColor + 2);
        faculty2.setId(id + 1);

        Collection<Faculty> forTest = new ArrayList<>(List.of(faculty, faculty2));

        when(facultyRepository.findAll()).thenReturn(List.of(faculty, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(forTest);
    }

    @Test
    public void testPutFaculty() throws Exception {

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", testName);
        facultyObject.put("color", testColor);

        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);
        faculty.setId(id);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(testName))
                .andExpect(jsonPath("$.color").value(testColor));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        //facultyRepository.deleteById() замокать не дает, т.к метод ничего не возвращает(void)
        //не понимаю как его протестировать, если мы в реальную БД не идем,
        // а только подкладываем нужные ответы... как мен подложить нужный ответ?
        doNothing().when(facultyRepository).deleteById(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + any(Long.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCollectionFacultyByNameOrColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);
        faculty.setId(id);

        Faculty faculty2 = new Faculty();
        faculty2.setName(testName + 2);
        faculty2.setColor(testColor + 2);
        faculty2.setId(id + 1);

        Collection<Faculty> forTest = new ArrayList<>(List.of(faculty, faculty2));

        when(facultyRepository.
                findByNameContainsIgnoreCaseOrColorContainsIgnoreCase
                        (any(String.class), any(String.class)))
                .thenReturn(List.of(faculty, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/by?str=" + any(String.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(forTest);

    }

    @Test
    public void testGetStudentsOfFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName(testName);
        faculty.setColor(testColor);
        faculty.setId(id);

        Student student = new Student();
        student.setName("student1");
        student.setAge(18);
        student.setFaculty(faculty);

        Student student2 = new Student();
        student2.setName("student2");
        student2.setAge(19);
        student.setFaculty(faculty);

        Collection<Student> forTest = new ArrayList<>(List.of(student, student2));

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudents?facultyId=" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(forTest);

    }

}
