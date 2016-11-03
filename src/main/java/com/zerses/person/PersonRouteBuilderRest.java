package com.zerses.person;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import com.zerses.StdExceptionProcessor;
import com.zerses.canonical.PersonAddRequest;
import com.zerses.canonical.PersonAddResponse;
import com.zerses.canonical.PersonFindRequest;
import com.zerses.canonical.PersonFindResponse;

@Component
public class PersonRouteBuilderRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Throwable.class)
            .handled(true)
            .process(new StdExceptionProcessor(new PersonFindResponse()));

        restConfiguration()
            .component("jetty")
            .contextPath("/")
            .host("0.0.0.0")
            .port(9000)
            .bindingMode(RestBindingMode.json)
            // Set up Swagger config
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Test API").apiProperty("api.version", "1.2.3")
            // and enable CORS so that Swagger UI can access this and present
            // its cool interface
            .apiProperty("cors", "true");
        ;
        

        rest()
            .get("/persons")
            .param().name("ageMin").type(RestParamType.query).defaultValue("0").description("Minimum Age").endParam()
            .param().name("ageMax").type(RestParamType.query).description("Maximum Age").endParam()
            .param().name("name").type(RestParamType.query).description("Name / Part of name").endParam()
            .outType(PersonFindResponse.class)
            .route()
            .bean(PersonFindRequest.class, "fromHeaders")
            .to("activemq:queue:person.queue?exchangePattern=InOut&receiveTimeout=10000");

        rest()
            .get("/person/{id}")
            .description("Find a specific Person")
            .outType(PersonFindResponse.class)
            .param().name("id").description("Person ID").dataType("int").endParam()
            .responseMessage().code(200).message("Person with the given id").endResponseMessage()
            .responseMessage().code(204).message("Person not found").endResponseMessage()
            .route()
            .routeId(this.getClass().getName() + ".personFind_REST_GET")
            .transform(header("id"))
            .to("log:TEST_Xfr?showAll=true")
            .bean(new PersonFindRequest(), "createRequest")
            .to("direct:personFind");


        rest("/person")
            .post("/add")
            .type(PersonAddRequest.class)
            .outType(PersonAddResponse.class)
            // .to("activemq:queue:person.queue?exchangePattern=InOut&receiveTimeout=10000");
            .to("direct:personAdd");

    }
}
