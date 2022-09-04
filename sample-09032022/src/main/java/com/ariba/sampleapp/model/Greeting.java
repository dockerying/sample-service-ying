package com.ariba.sampleapp.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This model is consumed by Greeting controller and its greeting parameter is
 * binded by application.yaml file, server.greeting variable
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "server")
public class Greeting {
    @Value("${server.greeting}")
    private String greeting;

    public Greeting() {

    }

    public Greeting(String message) {
        this.greeting = message;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String s) {
        greeting = s;
    }

}
