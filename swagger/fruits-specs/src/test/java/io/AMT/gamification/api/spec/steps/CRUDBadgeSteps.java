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

public class CRUDBadgeSteps {

    private Environment environment;
    private BadgesApi badgesApi;

    private BadgeWrite badgesWrite;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CRUDBadgeSteps(Environment environment){
        this.environment = environment;
        this.badgesApi = environment.getBadgesApi();
    }

    @Given("^there is a Gamification server using badges$")
    public void there_is_a_Gamification_server_using_badges() throws Throwable {
        assertNotNull(badgesApi);
    }

    @Given("^I have a badge payload$")
    public void i_have_a_badge_payload() throws Throwable {
        badgesWrite = new BadgeWrite();
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

    @Then("^I receive a (\\d+) status code for badge creation$")
    public void i_receive_a_status_code_for_badge_creation(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

}
