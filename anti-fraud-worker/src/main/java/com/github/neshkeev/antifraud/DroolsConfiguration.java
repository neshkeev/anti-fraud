package com.github.neshkeev.antifraud;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DroolsConfiguration {

    @Bean
    public KieServices kieServices() {
        return KieServices.get();
    }

    @Bean
    public KieContainer kieContainer() {
        return kieServices().getKieClasspathContainer();
    }

    @Bean
    public KieContainerSessionsPool pool() {
        return kieContainer().newKieSessionsPool(10);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KieSession kieSession(BlackListLoader blackListLoader) {
        KieSession kieSession = pool().newKieSession("BlackList");
        kieSession.setGlobal("blackList", blackListLoader.getBlackList());
        return kieSession;
    }
}
