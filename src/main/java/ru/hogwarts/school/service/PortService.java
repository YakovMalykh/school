package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class PortService {

    private Logger logger = LoggerFactory.getLogger(PortService.class);

    @Value("${server.port}")
    private String port;

    public String getPort() {
        logger.debug("получен Server port {}", port);
        return port;
    }
// практика работы с параллеьными стримами
    public int returnInteger() {
//        добавил проверку времени исполнения с выводом в консоль
        long timeStart = System.currentTimeMillis();
        logger.debug("был вызван метод returnInteger");
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        System.out.println(System.currentTimeMillis()-timeStart);
        return sum;
    }
}
