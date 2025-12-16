package com.devcaiqueoliveira.mercatopdvsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MercatoPdvSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercatoPdvSystemApplication.class, args);
    }

}