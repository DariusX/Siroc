package com.zerses.person;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zerses.BodyTransformProcessor;
import com.zerses.StdExceptionProcessor;
import com.zerses.canonical.PersonFindRequest;
import com.zerses.canonical.PersonFindResponse;
import com.zerses.entity.Person;

@Component
public class PersonFindRouteBuilder extends RouteBuilder {

    @Autowired
    private PersonDao personDao;

    @Override
    public void configure() throws Exception {

        onException(Throwable.class)
            .handled(true)
            .process(new StdExceptionProcessor(new PersonFindResponse()));

        from("direct:personFind")
            .id("direct:personFind")
            .log("body=${in.body}")
            .process(new BodyTransformProcessor<PersonFindRequest, List<Person>>(PersonFindRequest.class) { 
                @Override
                public List<Person> processBody(PersonFindRequest personFindRequest) throws Exception {

                    return personDao.findAll(personFindRequest);
                }
            })
            .to("log:TESTn?showAll=true");

    }
}
