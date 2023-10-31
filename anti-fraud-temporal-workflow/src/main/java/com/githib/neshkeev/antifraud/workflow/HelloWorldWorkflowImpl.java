package com.githib.neshkeev.antifraud.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class HelloWorldWorkflowImpl implements HelloWorldWorkflow {
    int i = 0;

    private static final String WITHDRAW = "greet";
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();
    private final ActivityOptions activityOptions = ActivityOptions
            .newBuilder()
            .setHeartbeatTimeout(Duration.ofSeconds(5))
            .build();

    // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
    private final Map<String, ActivityOptions> perActivityMethodOptions = Map.of(WITHDRAW, activityOptions);

    private final HelloWorldActivity activity = Workflow.newActivityStub(HelloWorldActivity.class, defaultActivityOptions, perActivityMethodOptions);

    // The transfer method is the entry point to the Workflow.
    // Activity method executions can be orchestrated here or from within other Activity methods.
    @Override
    public void greet(String who, LocalDateTime when) {
        System.out.println(i + ".Workflow");
        activity.greet(who, when);
    }
}
