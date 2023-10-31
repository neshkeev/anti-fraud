package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideActivity;
import com.githib.neshkeev.antifraud.workflow.BlackListDecideWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.Map;

public class BlackListDecideWorkflowImpl implements BlackListDecideWorkflow {

    private static final String DECIDE = "decide";
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofMillis(1))
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
    private final Map<String, ActivityOptions> perActivityMethodOptions = Map.of(DECIDE, activityOptions);

    private final BlackListDecideActivity activity = Workflow.newActivityStub(BlackListDecideActivity.class, defaultActivityOptions, perActivityMethodOptions);

    // The method is the entry point to the Workflow.
    @Override
    public boolean decide(Object o) {
        return activity.decide(o);
    }
}
