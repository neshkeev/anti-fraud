package com.githib.neshkeev.antifraud.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BlackListDecideWorkflow {

    @WorkflowMethod
    boolean decide(Request o);
}
