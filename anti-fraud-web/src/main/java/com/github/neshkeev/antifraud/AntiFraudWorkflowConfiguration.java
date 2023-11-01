package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AntiFraudWorkflowConfiguration {

    @Value("${temporal.target}")
    private String temporalTarget;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public BlackListDecideWorkflow blackListDecideWorkflow() {
        final var workflowClient = workflowClient();
        final var options = workflowOptions();
        return workflowClient.newWorkflowStub(BlackListDecideWorkflow.class, options);
    }

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        final var temporalServiceConfig =
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(temporalTarget)
                        .build();

        return WorkflowServiceStubs.newServiceStubs(temporalServiceConfig);
    }

    @Bean
    public WorkflowOptions workflowOptions() {
        return WorkflowOptions.newBuilder()
                .setTaskQueue("BLACK_LIST_REQUEST_QUEUE")
                .setWorkflowId("black-list-request")
                .build();
    }

    @Bean
    public WorkflowClient workflowClient() {
        return WorkflowClient.newInstance(workflowServiceStubs());
    }
}
