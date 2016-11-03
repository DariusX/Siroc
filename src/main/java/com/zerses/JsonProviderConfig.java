package com.zerses;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class JsonProviderConfig {

    //We could place this method in a specific class, but we put it here so that it is available across classes
    //Alternatively, we could place this method in the Applicationboot class, and have one less artifact
    @Bean(name = "jsonProvider")
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

}
