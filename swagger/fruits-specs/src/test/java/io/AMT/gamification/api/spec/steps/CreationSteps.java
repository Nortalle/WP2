package io.AMT.gamification.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.AMT.gamification.api.spec.helpers.Environment;
import io.AMT.gamification.ApiException;
import io.AMT.gamification.ApiResponse;
import io.AMT.gamification.api.BadgesApi;
import io.AMT.gamification.api.dto.BadgeWrite;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private BadgesApi badgesApi;

    BadgeWrite badgesWrite;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.badgesApi = environment.getBadgesApi();
    }


    @Given("^there is a Gamification server$")
    public void there_is_a_Fruits_server() throws Throwable {
        assertNotNull(badgesApi);
    }

    @Given("^I have a badge payload$")
    public void i_have_a_badge_payload() throws Throwable {
        badgesWrite = new io.AMT.gamification.api.dto.BadgeWrite();
        assertNotNull(badgesWrite);
        badgesWrite.setVisible(true);
    }

    @When("^I POST it to the /badges endpoint$")
    public void i_POST_it_to_the_badges_endpoint() throws Throwable {
        try {
            String token = "salut";
            lastApiResponse = badgesApi.createBadgeWithHttpInfo(token, badgesWrite);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

}
