package com.zerses.person;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.zerses.BodyTransformProcessor;
import com.zerses.StdExceptionProcessor;
import com.zerses.canonical.PersonAddRequest;
import com.zerses.canonical.PersonAddResponse;
import com.zerses.entity.Person;

@Component
public class PersonAddRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Throwable.class)
            .handled(true)
            .process(new StdExceptionProcessor(new PersonAddResponse()));

        from("direct:personAdd")
            .id("direct:personAdd")
            .to("log:TEST1?showAll=true")
            .log("body1=${in.body}")
            .process(new BodyTransformProcessor<PersonAddRequest, Person>(PersonAddRequest.class) {

                @Override
                public Person processBody(PersonAddRequest personAddRequest) throws Exception {
                    Person person = new Person();
                    BeanUtils.copyProperties(personAddRequest, person);
                    return person;
                }
            })
            .to("jpa://" + Person.class.getName() + "?persistenceUnit=test1jpa")
            .setBody(method(PersonAddResponse.class, "createSuccessfulResponse"))
            .to("log:TESTn?showAll=true");

    }
}
