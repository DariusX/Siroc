package com.zerses;

import java.time.LocalTime;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class TimeServiceRouteBuilder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:getTime")
    .setExchangePattern(ExchangePattern.InOut)
    .process(new Processor() {
        
        @Override
        public void process(Exchange exchange) throws Exception {
            exchange.getOut().setBody(LocalTime.now());
            
        }
    });
    
    // .to("direct:time");
    // add HTTP interface
    from("jetty:http://0.0.0.0:{{port}}/time")
    .setExchangePattern(ExchangePattern.InOut)
    .to("direct:getTime");
    
    System.out.println(String.format("Configured routes"));
  }

}
