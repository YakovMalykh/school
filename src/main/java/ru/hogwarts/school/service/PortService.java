package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PortService {

    private Logger logger = LoggerFactory.getLogger(PortService.class);

    @Value("${server.port}")
    private String port;

    public String getPort() {
        logger.debug("получен Server port {}", port);
        return port;
    }
}
