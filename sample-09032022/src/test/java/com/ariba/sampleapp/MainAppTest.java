package com.ariba.sampleapp;
/**
 * Abstract Test Class
 *  - Common functions, initialization code etc. can be added here
 *
 */

import com.ariba.sampleapp.model.Greeting;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApp.class)
public abstract class MainAppTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}