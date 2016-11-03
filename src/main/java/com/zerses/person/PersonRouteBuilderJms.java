package com.zerses.person;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PersonRouteBuilderJms extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:person.queue")
            .id(this.getClass().toString())
            .to("log:PersonJms?showAll=true")
            .to("direct:personFind");
    }

}
