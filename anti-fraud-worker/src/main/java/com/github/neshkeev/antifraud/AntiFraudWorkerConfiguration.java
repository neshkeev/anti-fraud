package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideActivity;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AntiFraudWorkerConfiguration {

    @Bean
    public Worker worker(BlackListDecideActivity activity) {
        Worker worker = factory().newWorker("BLACK_LIST_REQUEST_QUEUE");
        worker.registerWorkflowImplementationTypes(BlackListDecideWorkflowImpl.class);
        worker.registerActivitiesImplementations(activity);

        return worker;
    }

    @Bean
    public WorkerFactory factory() {
        WorkflowClient workflowClient = workflowClient();
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs.newLocalServiceStubs();
    }

    @Bean
    public WorkflowClient workflowClient() {
        return WorkflowClient.newInstance(workflowServiceStubs());
    }
}
