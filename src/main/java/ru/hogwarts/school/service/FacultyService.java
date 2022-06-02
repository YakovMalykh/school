package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private HashMap<Long, Faculty> allFakulties = new HashMap<>();
    private long lastIdFaculty = 0;

    public Faculty createFaculty(Faculty faculty) {
        if (!allFakulties.containsValue(faculty)) {
            faculty.setId(++lastIdFaculty);
            allFakulties.put(lastIdFaculty, faculty);
            return faculty;
        }
        return null;
    }

    public Faculty findFaculry(long id) {
        return allFakulties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (allFakulties.containsKey(faculty.getId())) {
            allFakulties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty removeFaculty(long id) {
        return allFakulties.remove(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return allFakulties.values();
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        return allFakulties.values().stream().filter(e->e.getColor().equals(color))
                .collect(Collectors.toList());
    }

}
