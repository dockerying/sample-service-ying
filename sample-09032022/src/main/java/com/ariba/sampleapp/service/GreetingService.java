package com.ariba.sampleapp.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import com.ariba.sampleapp.model.Greeting;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

/**
 * This service talks to Greeting model and is able to get/update/reload
 * Greeting model
 */
@EnableConfigurationProperties(Greeting.class)
@Service
public class GreetingService {
    @Autowired
    private Greeting greeting;

    /**
     * Initializing the StatsDClient
     */
    private static final StatsDClient statsd = new NonBlockingStatsDClient(
            "sampleapp.greetingservice",          /* prefix to any stats; may be null or empty string */
            "localhost",                        /* common case: localhost */
            8125,                                 /* port */
            new String[]{"role:sampleapp"}            /* DataDog extension: Constant tags, always applied */
    );

    public String getGreeting() {
        /**
         * getGreeting counter to be pushed down to udp
         * on port 8125 via the statsd client.
         */
        statsd.incrementCounter("getGreeting");
        return greeting.getGreeting();
    }

    public void updateGreeting(String s) {

        /**
         * updateGreeting counter to be pushed down to udp
         * on port 8125 via the statsd client.
         */
        statsd.incrementCounter("updateGreeting");
        greeting.setGreeting(s);
    }

    /**
     * This method reads /config/application.yml file, and use snakeyaml to
     * convert to yaml structure, update the Greeting model with server/greeting
     * variable
     *
     * @throws FileNotFoundException
     */
    public void reloadGreetingFromConfig(String path)
            throws FileNotFoundException {

        // open config file: by default it is /config/application.yml
        Scanner fileIn = new Scanner(new File(path));
        String yamlString = "";

        // read that file
        while (fileIn.hasNext()) {
            String s = fileIn.nextLine();
            yamlString += s;
            yamlString += "\n";

        }

        // conver to yaml structure
        Yaml yaml = new Yaml();
        Map<String, Object> map = (Map<String, Object>) yaml.load(yamlString);

        // get server/greeting part
        String greeting = ((Map<String, String>) map.get("server"))
                .get("greeting");

        updateGreeting(greeting);
    }
}
