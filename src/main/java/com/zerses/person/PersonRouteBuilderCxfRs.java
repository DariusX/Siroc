package com.zerses.person;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PersonRouteBuilderCxfRs extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("cxfrs://http://localhost:9001?resourceClasses=" + PersonWsResource.class.getName() + "&bindingStyle=SimpleConsumer&providers=#jsonProvider")
            .id(this.getClass().getName() + ".personFind_WS")
            .to("direct:personFind");
    }

}
