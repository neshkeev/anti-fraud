package com.githib.neshkeev.antifraud;

import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.Set;

public class Main {
    public enum MessageStatus {
        HELLO,
        @SuppressWarnings("unused") GOODBYE
    }

    public record Message(String message, MessageStatus status) implements Command<Message> { }

    public static void main(String[] args) {
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieContainerSessionsPool pool = kc.newKieSessionsPool(10);

        KieSession kSession = pool.newKieSession("HelloWorldKS");
        kSession.setGlobal("values", Set.of("Hello, World!"));
        FactHandle insert = kSession.insert(new Message("Hello, World!", MessageStatus.HELLO));
        kSession.fireAllRules();
        kSession.getFactHandles().forEach(System.out::println);
        System.out.println("After");
        System.out.println(insert);
    }
}