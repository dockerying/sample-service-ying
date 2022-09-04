package com.ariba.test.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Test
public class TestWebService {
    private String url = "http://" + System.getenv("NOMAD_JOB_NAME") + ".service";


    @Test(groups = {"smoke", "functional"})
    public void checkGreetingStatus() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println(e);
        }

        given().
                when().
                get(url  + "/greeting").
                then().
                assertThat().
                statusCode(200);
    }
}
