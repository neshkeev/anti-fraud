package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideActivity;
import com.githib.neshkeev.antifraud.workflow.Request;
import org.springframework.stereotype.Component;

@Component
public class BlackListDecideActivityImpl implements BlackListDecideActivity {

    @Override
    public boolean decide(Request o) {
        System.out.println(o);
        return false;
    }
}
