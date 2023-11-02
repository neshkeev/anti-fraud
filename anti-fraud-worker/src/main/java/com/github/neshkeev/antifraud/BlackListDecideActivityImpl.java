package com.github.neshkeev.antifraud;

import com.githib.neshkeev.antifraud.workflow.BlackListDecideActivity;
import com.githib.neshkeev.antifraud.workflow.BlacklistedRequest;
import com.githib.neshkeev.antifraud.workflow.Request;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
public class BlackListDecideActivityImpl implements BlackListDecideActivity {

    private final ObjectFactory<KieSession> kieSession;

    public BlackListDecideActivityImpl(ObjectFactory<KieSession> kieSession) {
        this.kieSession = kieSession;
    }

    @Override
    public boolean decide(Request o) {
        final var session = kieSession.getObject();
        session.insert(o);
        session.fireAllRules();
        try {
            return session.getFactHandles(e -> e instanceof BlacklistedRequest).isEmpty();
        } finally {
            session.dispose();
        }
    }
}
