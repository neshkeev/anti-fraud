package com.github.neshkeev.antifraud;

import io.grpc.ServerBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorkflowGRPCServer {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowGRPCServer.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AntiFraudService service) {
        return __ -> {
            final var server = ServerBuilder.forPort(7777).addService(service).build();
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
            server.start();
            System.out.println("GRPC Server Started");
            server.awaitTermination();
        };
    }
}