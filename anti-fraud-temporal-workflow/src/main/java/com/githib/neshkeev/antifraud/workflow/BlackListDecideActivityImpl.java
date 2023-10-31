package com.githib.neshkeev.antifraud.workflow;

public class BlackListDecideActivityImpl implements BlackListDecideActivity {

    @Override
    public boolean decide(Object o) {
        System.out.println(o);
        return false;
    }
}
