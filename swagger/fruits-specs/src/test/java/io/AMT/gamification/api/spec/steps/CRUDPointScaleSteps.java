package io.AMT.gamification.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.AMT.gamification.api.spec.helpers.Environment;
import io.AMT.gamification.ApiException;
import io.AMT.gamification.ApiResponse;
import io.AMT.gamification.api.PointScalesApi;
import io.AMT.gamification.api.dto.PointScaleWrite;
import io.AMT.gamification.api.dto.PointScaleRead;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CRUDPointScaleSteps {

    private Environment environment;
    private PointScalesApi pointScalesApi;

    private PointScaleWrite pointScaleWrite;
    private PointScaleWrite newPointScale;
    private PointScaleRead pointScaleRead;
    List<PointScaleRead> pointScaleReads = new ArrayList<>();


    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private long pointScaleId;

    private String token1 = "token1";
    private String wrongToken = "token2";

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
        pointScaleWrite.setName("pointScale");
        pointScaleWrite.setDescription("pointScale");
        assertNotNull(pointScaleWrite);
    }

    @When("^I POST it to the /pointScales endpoint$")
    public void i_POST_it_to_the_pointScales_endpoint() throws Throwable {
        try {
            String token = "salut";
            lastApiResponse = pointScalesApi.createPointScaleWithHttpInfo(token1, pointScaleWrite);
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

    @Then("^I receive a (\\d+) status code for pointScale creation$")
    public void i_receive_a_status_code_for_pointScale_creation(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

    @Then("^I delete the created pointScale$")
    public void i_delete_the_created_pointScale() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.deletePointScaleWithHttpInfo(pointScaleId, token1);
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

    @When("^I GET it to the /pointScales/pointScaleId endpoint$")
    public void i_GET_it_to_the_pointScales_pointScaleId_endpoint() throws Throwable {
        pointScaleRead = pointScalesApi.getPointScale(pointScaleId, token1);
    }

    @Then("^I receive a response containing the pointScale$")
    public void i_receive_a_response_containing_the_pointScale() throws Throwable {
        PointScaleRead expected = new PointScaleRead();
        expected.setDescription("pointScale");
        expected.setName("pointScale");
        expected.setId(pointScaleId);
        assertEquals(expected, pointScaleRead);
    }

    @When("^I GET it to the /pointScale/pointScaleId endpoint with wrong token$")
    public void i_GET_it_to_the_pointScale_pointScaleId_endpoint_with_wrong_token() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.getPointScaleWithHttpInfo(pointScaleId, wrongToken);
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

    @Then("^I receive a (\\d+) status code for pointScale update unauthorized$")
    public void i_receive_a_status_code_for_pointScale_update_unauthorized(int arg1) throws Throwable {
        Assert.assertEquals(401, lastStatusCode);
    }

    @When("^I GET to the /pointScales/pointScaleId with wrong pointScaleId$")
    public void i_GET_to_the_pointScales_pointScaleId_with_wrong_pointScaleId() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.getPointScaleWithHttpInfo(pointScaleId + 1, token1);
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

    @Then("^I recieve a (\\d+) error not found for pointScale$")
    public void i_recieve_a_error_not_found_for_pointScale(int arg1) throws Throwable {
        assertEquals(404, lastStatusCode);
    }

    @When("^I GET it to the /pointScales endpoint$")
    public void i_GET_it_to_the_pointScales_endpoint() throws Throwable {
        pointScaleReads = pointScalesApi.getPointScales(token1);
    }

    @Then("^I recieve an array of pointScales$")
    public void i_recieve_an_array_of_pointScales() throws Throwable {
        assertNotNull(pointScaleReads);
    }

    @Given("^I have a update pointScale payload$")
    public void i_have_a_update_pointScale_payload() throws Throwable {
        newPointScale = new PointScaleWrite();
        newPointScale.setName("newPointScale");
        newPointScale.description("newPointScale");
        assertNotNull(newPointScale);
    }

    @When("^I PUT it to the /pointScales/pointScaleId endpoint$")
    public void i_PUT_it_to_the_pointScales_pointScaleId_endpoint() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.updatePointScaleWithHttpInfo(pointScaleId, token1, newPointScale);
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

    @Then("^I receive a (\\d+) status code for pointScale no content$")
    public void i_receive_a_status_code_for_pointScale_no_content(int arg1) throws Throwable {
        assertEquals(204, lastStatusCode);
    }

    @Then("^I recieve the modified pointScale$")
    public void i_recieve_the_modified_pointScale() throws Throwable {
        PointScaleRead expected = new PointScaleRead();
        expected.setName("newPointScale");
        expected.description("newPointScale");
        expected.setId(pointScaleId);
        assertEquals(expected, pointScaleRead);
    }

    @When("^I PUT it to the /pointScales/pointScaleId endpoint with wrong token$")
    public void i_PUT_it_to_the_pointScales_pointScaleId_endpoint_with_wrong_token() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.updatePointScaleWithHttpInfo(pointScaleId, wrongToken, newPointScale);
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

    @When("^I PUT it to the /pointScales/pointScaleId endpoint with wrong pointScaleId$")
    public void i_PUT_it_to_the_pointScales_pointScaleId_endpoint_with_wrong_pointScaleId() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.updatePointScaleWithHttpInfo(pointScaleId + 1, token1, newPointScale);
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

    @When("^I DETETE to the /pointScales/pointScaleId endpoint with wrong token$")
    public void i_DETETE_to_the_pointScales_pointScaleId_endpoint_with_wrong_token() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.deletePointScaleWithHttpInfo(pointScaleId, wrongToken);
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

    @When("^I DETETE to the /pointScales/pointScaleId endpoint with wrong pointScaleId$")
    public void i_DETETE_to_the_pointScales_pointScaleId_endpoint_with_wrong_pointScaleId() throws Throwable {
        try {
            lastApiResponse = pointScalesApi.deletePointScaleWithHttpInfo(pointScaleId + 1, token1);
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

    @When("^I DETETE to the /pointScales/pointScaleId endpoint for all pointScales$")
    public void i_DETETE_to_the_pointScales_pointScaleId_endpoint_for_all_pointScales() throws Throwable {
        for(PointScaleRead pointScaleRead : pointScaleReads){
            try {
                lastApiResponse = pointScalesApi.deletePointScaleWithHttpInfo(pointScaleRead.getId(), token1);
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
    }

    @Then("^I recieve an empty list of pointScales$")
    public void i_recieve_an_empty_list_of_pointScales() throws Throwable {
        Assert.assertTrue(pointScaleReads.isEmpty());
    }


    private void getIdFromLocation(String location){
        String[] segments = location.split("/");
        String almostIdStr =  segments[segments.length-1];
        String idStr = almostIdStr.substring(0,almostIdStr.length()-1);
        pointScaleId = Long.parseLong(idStr);
    }

}
