package com.zerses;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelContextConfig {

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                System.out.println("=========================================================================================================");
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
                connectionFactory.setTrustAllPackages(true);
                connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList("com.zerses.canonical".split(","))));
                
                context.addComponent("activemq", ActiveMQComponent.jmsComponent(connectionFactory));
           //     context.addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));

            }

            @Override
            public void afterApplicationStart(CamelContext context) {
                // Nothing here

            }
        };
    }
}
