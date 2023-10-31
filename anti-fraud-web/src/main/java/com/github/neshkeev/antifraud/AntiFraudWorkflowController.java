package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideWorkflow;
import io.temporal.client.WorkflowClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AntiFraudWorkflowController {

    private final ObjectFactory<BlackListDecideWorkflow> helloWorldWorkflowObjectFactory;

    public AntiFraudWorkflowController(ObjectFactory<BlackListDecideWorkflow> helloWorldWorkflowObjectFactory) {
        this.helloWorldWorkflowObjectFactory = helloWorldWorkflowObjectFactory;
    }

    @PostMapping("/check")
    public Mono<Boolean> check(@RequestBody Request request) {
        final var workflow = helloWorldWorkflowObjectFactory.getObject();

        var execute = WorkflowClient.execute(workflow::decide, request);
        return Mono.fromCompletionStage(execute);
    }
}
