package com.githib.neshkeev.antifraud.workflow;

import java.time.LocalDateTime;

public class HelloWorldActivityImpl implements HelloWorldActivity {

    int i = 0;

    @Override
    public void greet(String who, LocalDateTime when) {
        System.out.println(i + ".Hello, " + who + " at " + when);
        i ++;
    }
}
