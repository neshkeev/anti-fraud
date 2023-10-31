package com.githib.neshkeev.antifraud.workflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.time.Duration;
import java.time.LocalDateTime;

public class WorkflowInitiator {
    public static void main(String[] args) throws InterruptedException {
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        final var service = WorkflowServiceStubs.newLocalServiceStubs();
        final var options = WorkflowOptions.newBuilder()
                .setTaskQueue("HELLO_WORLD_TASK_QUEUE")
                // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
                .setWorkflowId("money-transfer-workflow")
                .build();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        final var client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        for (int i = 0; i < 5; i++) {
            final var workflow = client.newWorkflowStub(HelloWorldWorkflow.class, options);
            Thread.sleep(Duration.ofSeconds(1).toMillis());
            workflow.greet("World " + i + " " + Thread.currentThread().getName(), LocalDateTime.now());
        }
    }
}