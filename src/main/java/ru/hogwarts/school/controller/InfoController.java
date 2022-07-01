package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.PortService;

@RestController
public class InfoController {

    @Autowired
    private PortService portService;

    @GetMapping("/port")
    public String getPort () {
        return portService.getPort();
    }
}
