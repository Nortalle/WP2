package io.AMT.gamification.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.AMT.gamification.api.spec.helpers.Environment;
import io.AMT.gamification.ApiException;
import io.AMT.gamification.ApiResponse;
import io.AMT.gamification.api.PointScalesApi;
import io.AMT.gamification.api.dto.PointScaleWrite;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CRUDPointScaleSteps {

    private Environment environment;
    private PointScalesApi pointScalesApi;

    private PointScaleWrite pointScaleWrite;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CRUDPointScaleSteps(Environment environment){
        this.environment = environment;
        this.pointScalesApi = environment.getPointScalesApi();
    }

    @Given("^there is a Gamification server using pointScales$")
    public void there_is_a_Gamification_server_using_pointScales() throws Throwable {
        assertNotNull(pointScalesApi);
    }

    @Given("^I have a pointScale payload$")
    public void i_have_a_pointScale_payload() throws Throwable {
        pointScaleWrite = new PointScaleWrite();
        assertNotNull(pointScaleWrite);
    }

    @When("^I POST it to the /pointScales endpoint$")
    public void i_POST_it_to_the_pointScales_endpoint() throws Throwable {
        try {
            String token = "salut";
            lastApiResponse = pointScalesApi.createPointScaleWithHttpInfo(token, pointScaleWrite);
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

    @Then("^I receive a (\\d+) status code for pointScale creation$")
    public void i_receive_a_status_code_for_pointScale_creation(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

}
