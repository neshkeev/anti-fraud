package com.githib.neshkeev.antifraud.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.time.LocalDateTime;

@ActivityInterface
public interface HelloWorldActivity {

    @ActivityMethod
    void greet(String who, LocalDateTime when);
}
