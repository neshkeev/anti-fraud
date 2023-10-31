package com.githib.neshkeev.antifraud.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface BlackListDecideActivity {

    @ActivityMethod
    boolean decide(Object o);
}
