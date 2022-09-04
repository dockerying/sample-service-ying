package com.ariba.sampleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry class of SampleApp
 */
@SpringBootApplication()
public class MainApp {

    /**
     * Entry point of SampleApp. Run the spring application
     *
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(MainApp.class, args);

    }
}
