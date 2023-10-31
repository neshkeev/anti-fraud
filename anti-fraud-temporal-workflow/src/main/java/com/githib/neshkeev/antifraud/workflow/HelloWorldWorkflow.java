package com.githib.neshkeev.antifraud.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.time.LocalDateTime;

@WorkflowInterface
public interface HelloWorldWorkflow {

    // The Workflow method is called by the initiator either via code or CLI.
    @WorkflowMethod
    void greet(String who, LocalDateTime when);
}
