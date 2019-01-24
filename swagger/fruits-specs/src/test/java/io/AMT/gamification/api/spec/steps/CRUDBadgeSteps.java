package io.AMT.gamification.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.AMT.gamification.api.spec.helpers.Environment;
import io.AMT.gamification.ApiException;
import io.AMT.gamification.ApiResponse;
import io.AMT.gamification.api.BadgesApi;
import io.AMT.gamification.api.dto.BadgeWrite;
import io.AMT.gamification.api.dto.BadgeRead;
import org.junit.Assert;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CRUDBadgeSteps {

    private Environment environment;
    private BadgesApi badgesApi;

    private BadgeWrite badgesWrite;
    private BadgeRead badgeRead;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private long badgeId;


    private String token1 = "token1";
    private String wrongToken = "token2";


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
        badgesWrite.setImage("testBadge");
        badgesWrite.setName("testBadge");
    }

    @When("^I POST it to the /badges endpoint$")
    public void i_POST_it_to_the_badges_endpoint() throws Throwable {
        try {
            lastApiResponse = badgesApi.createBadgeWithHttpInfo(token1, badgesWrite);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();

            //get the id of the badge created
            getIdFromLocation(lastApiResponse.getHeaders().get("Location").toString());
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



    @When("^I GET it to the /badges/badgeId endpoint$")
    public void i_GET_it_to_the_badges_badgeId_endpoint() throws Throwable {
        badgeRead = badgesApi.getBadge(badgeId, token1);
    }

    @Then("^I receive a response containing the badge$")
    public void i_receive_a_response_containing_the_badge() throws Throwable {
        BadgeRead expected = new BadgeRead();
        expected.setId(badgeId);
        expected.setImage("testBadge");
        expected.setName("testBadge");
        expected.setVisible(true);
        assertEquals(expected, badgeRead);
    }

    /*
    @When("^I GET it to the /badges/badgeId endpoint with wrong token$")
    public void i_GET_it_to_the_badges_badgeId_endpoint_with_wrong_token() throws Throwable {
        badgeRead = badgesApi.getBadge(badgeId, wrongToken);
    }

    @Then("^I a recieve a response containing a null$")
    public void i_a_recieve_a_response_containing_a_null() throws Throwable {
        Assert.assertNull(badgeRead);
    }
    */

    private void getIdFromLocation(String location){
        String[] segments = location.split("/");
        String almostIdStr =  segments[segments.length-1];
        String idStr = almostIdStr.substring(0,almostIdStr.length()-1);
        badgeId = Long.parseLong(idStr);
    }




}
