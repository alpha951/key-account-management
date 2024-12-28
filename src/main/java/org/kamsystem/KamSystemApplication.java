package org.kamsystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class KamSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(KamSystemApplication.class, args);
        log.trace("Trace level message");
        log.debug("Debug level message");
        log.info("Info level message");
        log.warn("Warning level message");
        log.error("Error level message");
    }
}
