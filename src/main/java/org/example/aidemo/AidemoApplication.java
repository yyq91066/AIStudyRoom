package org.example.aidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AidemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidemoApplication.class, args);
    }

}
