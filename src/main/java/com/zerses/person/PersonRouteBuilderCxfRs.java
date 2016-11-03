package com.zerses.person;

import javax.ws.rs.HttpMethod;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.zerses.canonical.PersonFindRequest;

@Component
public class PersonRouteBuilderCxfRs extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("cxfrs://http://localhost:9001?resourceClasses=" + PersonWsResource.class.getName() + "&bindingStyle=SimpleConsumer&providers=#jsonProvider")
            .id(this.getClass().getName() + ".personFind_WS")
            .choice()
              .when(header(Exchange.HTTP_METHOD).isEqualTo(HttpMethod.GET))
                .choice()
                .when(header("personId").isNotNull())
                  .to("log:From_CXF_findOne?showAll=true")
                  .transform(header("personId"))
                  .to("direct:personFindOne")
                .otherwise()
                  .to("log:From_CXF_find?showAll=true")
                  .bean(PersonFindRequest.class, "fromHeaders")
                  .to("direct:personFind")
                .endChoice()

              .when(header(Exchange.HTTP_METHOD).isEqualTo(HttpMethod.POST))
                .to("log:From_CXF_add?showAll=true")
                .to("direct:personAdd")
            .endChoice();
    }

}
