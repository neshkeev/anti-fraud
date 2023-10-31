package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideWorkflow;
import com.githib.neshkeev.antifraud.workflow.Request;
import io.temporal.client.WorkflowClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
public class AntiFraudWorkflowController {

    private final ObjectFactory<BlackListDecideWorkflow> workflow;

    public AntiFraudWorkflowController(ObjectFactory<BlackListDecideWorkflow> workflow) {
        this.workflow = workflow;
    }

    @PostMapping("/check")
    public Mono<Boolean> check(@RequestBody Request request) {
        final var workflow = this.workflow.getObject();

        final CompletableFuture<Boolean> execute = WorkflowClient.execute(workflow::decide, request);
        return Mono.fromCompletionStage(execute);
    }
}
