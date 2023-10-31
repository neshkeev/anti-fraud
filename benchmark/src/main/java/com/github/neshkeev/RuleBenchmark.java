package com.github.neshkeev;

import com.githib.neshkeev.antifraud.Main;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.KieSession;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class RuleBenchmark {

    private KieContainerSessionsPool pool;

    @Setup
    public void setup() {
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();
        pool = kc.newKieSessionsPool(10);
    }

    @Benchmark
    public Object evalMVEL() {
        return evalRule("HelloWorldKSMVEL");
    }

    @Benchmark
    public Object evalJava() {
        return evalRule("HelloWorldKSJava");
    }

    public Object evalRule(String helloWorldKSMVEL) {
        KieSession kSession = pool.newKieSession(helloWorldKSMVEL);
        Set<String> set = Set.of("Hello, World!");
        kSession.setGlobal("values", set);
        kSession.insert(new Main.Message("Hello, World!", Main.MessageStatus.HELLO));
        kSession.fireAllRules();
        kSession.setGlobal("values", null);
        try {
            return kSession.getFactHandles();
        } finally {
            kSession.dispose();
        }
    }

    @Benchmark
    public List<Main.Message> evalNative() {
        final var message = new Main.Message("Hello, World!", Main.MessageStatus.HELLO);
        final var messages = new ArrayList<Main.Message>();
        messages.add(message);

        Set<String> set = Set.of("Hello, World!");
        if (set.contains(message.message())) {
            messages.add(new Main.Message(message.message(), Main.MessageStatus.GOODBYE));
        }
        return messages;
    }
}
