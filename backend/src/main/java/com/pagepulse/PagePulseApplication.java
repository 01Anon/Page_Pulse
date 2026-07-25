package com.pagepulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Page Pulse URL auditing application.
 * Bootstraps the Spring Boot context and starts the embedded server.
 */
@SpringBootApplication
public class PagePulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagePulseApplication.class, args);
    }
}
