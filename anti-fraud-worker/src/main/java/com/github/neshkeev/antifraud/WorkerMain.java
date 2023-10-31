package com.github.neshkeev.antifraud;

import io.temporal.worker.WorkerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkerMain {
    public static void main(String[] args) {
        SpringApplication.run(WorkerMain.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(WorkerFactory factory) {
        return __ -> factory.start();
    }
}