package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideWorkflow;
import com.githib.neshkeev.antifraud.workflow.Request;
import io.temporal.client.WorkflowClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RestController
public class AntiFraudWorkflowController {

    private final ObjectFactory<BlackListDecideWorkflow> workflow;

    public AntiFraudWorkflowController(ObjectFactory<BlackListDecideWorkflow> workflow) {
        this.workflow = workflow;
    }

//    @PostMapping("/check")
//    public Boolean check(@RequestBody Request request) {
//        final var workflow = this.workflow.getObject();
//
//        return workflow.decide(request);
//    }
    @PostMapping("/check")
    public Mono<Boolean> check(@RequestBody Request request) {
        final var current = LocalDateTime.now();
        final var workflow = this.workflow.getObject();

        final CompletableFuture<Boolean> execute = WorkflowClient.execute(workflow::decide, request);
        try {
            return Mono.fromCompletionStage(execute);
        } finally {
            System.out.println(Duration.between(current, LocalDateTime.now()).toMillis());
        }
    }

    @PostMapping("/empty")
    public Mono<Boolean> empty(@RequestBody Request request) {
        return Mono.just(false);
    }
}
