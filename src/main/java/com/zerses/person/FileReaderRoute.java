package com.zerses.person;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import com.zerses.canonical.PersonAddRequest;

public class FileReaderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        BindyCsvDataFormat bindyFormat = new BindyCsvDataFormat(PersonAddRequest.class);
        
        from("file:/staging/person/in?move=/arch/personAdd/in/${date:now:yyyyMMddhhmmss}.${file:name}&readLock=changed&idempotent=true")
        .id("personFileReader")
        .log(LoggingLevel.INFO, "Reading file: ${file:name}")
        .split(body().tokenize("\n")).streaming()
        .unmarshal(bindyFormat)
        .to("direct:personUpdate")
        .end()
        ;
    }

}
