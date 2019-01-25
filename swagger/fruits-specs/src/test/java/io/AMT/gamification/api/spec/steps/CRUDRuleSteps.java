package io.AMT.gamification.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.AMT.gamification.api.spec.helpers.Environment;
import io.AMT.gamification.ApiException;
import io.AMT.gamification.ApiResponse;
import io.AMT.gamification.api.RulesApi;
import io.AMT.gamification.api.dto.RuleWrite;
import io.AMT.gamification.api.dto.RuleRead;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CRUDRuleSteps {

    private Environment environment;
    private RulesApi rulesApi;

    private RuleWrite ruleWrite;
    private RuleWrite newRule;
    private RuleRead ruleRead;
    List<RuleRead> ruleReads = new ArrayList<>();


    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    private long ruleId;

    private String token1 = "token1";
    private String wrongToken = "token2";

    public CRUDRuleSteps(Environment environment){
        this.environment = environment;
        this.rulesApi = environment.getRulesApi();
    }

    @Given("^there is a Gamification server using rules$")
    public void there_is_a_Gamification_server_using_rules() throws Throwable {
        assertNotNull(rulesApi);
    }

    @Given("^I have a rule payload$")
    public void i_have_a_rule_payload() throws Throwable {
        ruleWrite = new RuleWrite();
        ruleWrite.setIfEventType("Rule");
        ruleWrite.setIfPropertyCondition("Rule");
        ruleWrite.setIfPropertyName("Rule");
        ruleWrite.setThenAwardPoint(1);
        ruleWrite.setThenBadgeId(1L);
        ruleWrite.setThenPointScaleId(1L);
        assertNotNull(ruleWrite);
    }

    @When("^I POST it to the /rule endpoint$")
    public void i_POST_it_to_the_rule_endpoint() throws Throwable {
        try {
            String token = "salut";
            lastApiResponse = rulesApi.createRuleWithHttpInfo(token1, ruleWrite);
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

    @Then("^I receive a (\\d+) status code for rule creation$")
    public void i_receive_a_status_code_forrule_creation(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

    @Then("^I delete the created rule")
    public void i_delete_the_created_rule() throws Throwable {
        try {
            lastApiResponse = rulesApi.deleteRuleWithHttpInfo(ruleId, token1);
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

    @When("^I GET it to the /rules/ruleId endpoint$")
    public void i_GET_it_to_the_rules_ruleId_endpoint() throws Throwable {
        ruleRead = rulesApi.getRule(ruleId, token1);
    }

    @Then("^I receive a response containing the rule")
    public void i_receive_a_response_containing_the_rule() throws Throwable {
        RuleRead expected = new RuleRead();
        expected.setIfEventType("Rule");
        expected.setIfPropertyCondition("Rule");
        expected.setIfPropertyName("Rule");
        expected.setThenAwardPoint(1);
        expected.setThenBadgeId(1L);
        expected.setThenPointScaleId(1L);
        expected.setId(ruleId);
        assertEquals(expected, ruleRead);
    }

    @When("^I GET it to the /rule/ruleId endpoint with wrong token$")
    public void i_GET_it_to_the_rule_ruleId_endpoint_with_wrong_token() throws Throwable {
        try {
            lastApiResponse = rulesApi.getRuleWithHttpInfo(ruleId, wrongToken);
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

    @Then("^I receive a (\\d+) status code for rule update unauthorized$")
    public void i_receive_a_status_code_for_rule_update_unauthorized(int arg1) throws Throwable {
        Assert.assertEquals(401, lastStatusCode);
    }

    @When("^I GET to the /rules/ruleId with wrong ruleId$")
    public void i_GET_to_the_rules_ruleId_with_wrong_ruleId() throws Throwable {
        try {
            lastApiResponse = rulesApi.getRuleWithHttpInfo(ruleId + 1, token1);
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

    @Then("^I recieve a (\\d+) error not found for rule")
    public void i_recieve_a_error_not_found_for_rule(int arg1) throws Throwable {
        assertEquals(404, lastStatusCode);
    }

    @When("^I GET it to the /rules endpoint$")
    public void i_GET_it_to_the_rules_endpoint() throws Throwable {
        ruleReads = rulesApi.getRules(token1);
    }

    @Then("^I recieve an array of rules$")
    public void i_recieve_an_array_of_rules() throws Throwable {
        assertNotNull(ruleReads);
    }

    @Given("^I have a update rule payload$")
    public void i_have_a_update_rule_payload() throws Throwable {
        newRule = new RuleWrite();
        newRule.setIfEventType("Rule1");
        newRule.setIfPropertyCondition("Rule1");
        newRule.setIfPropertyName("Rule1");
        newRule.setThenAwardPoint(11);
        newRule.setThenBadgeId(11L);
        newRule.setThenPointScaleId(11L);
    }

    @When("^I PUT it to the /rules/ruleId endpoint$")
    public void i_PUT_it_to_the_rules_ruleId_endpoint() throws Throwable {
        try {
            lastApiResponse = rulesApi.updateRuleWithHttpInfo(ruleId, token1, newRule);
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

    @Then("^I receive a (\\d+) status code for rule no content$")
    public void i_receive_a_status_code_for_rule_no_content(int arg1) throws Throwable {
        assertEquals(204, lastStatusCode);
    }

    @Then("^I recieve the modified rule")
    public void i_recieve_the_modified_rule() throws Throwable {
        RuleRead expected = new RuleRead();
        expected.setIfEventType("Rule1");
        expected.setIfPropertyCondition("Rule1");
        expected.setIfPropertyName("Rule1");
        expected.setThenAwardPoint(11);
        expected.setThenBadgeId(11L);
        expected.setThenPointScaleId(11L);
        expected.setId(ruleId);
        assertEquals(expected, ruleRead);
    }

    @When("^I PUT it to the /rules/ruleId endpoint with wrong token$")
    public void i_PUT_it_to_the_rules_ruleId_endpoint_with_wrong_token() throws Throwable {
        try {
            lastApiResponse = rulesApi.updateRuleWithHttpInfo(ruleId, wrongToken, newRule);
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

    @When("^I PUT it to the /rules/ruleId endpoint with wrong ruleId$")
    public void i_PUT_it_to_the_rules_ruleId_endpoint_with_wrong_ruleId() throws Throwable {
        try {
            lastApiResponse = rulesApi.updateRuleWithHttpInfo(ruleId + 1, token1, newRule);
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

    @When("^I DETETE to the /rules/ruleId endpoint with wrong token$")
    public void i_DETETE_to_the_rules_ruleId_endpoint_with_wrong_token() throws Throwable {
        try {
            lastApiResponse = rulesApi.deleteRuleWithHttpInfo(ruleId, wrongToken);
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

    @When("^I DETETE to the /rules/ruleId endpoint with wrong ruleId$")
    public void i_DETETE_to_the_rules_ruleId_endpoint_with_wrong_ruleId() throws Throwable {
        try {
            lastApiResponse = rulesApi.deleteRuleWithHttpInfo(ruleId + 1, token1);
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

    @When("^I DETETE to the /rules/ruleId endpoint for all rules$")
    public void i_DETETE_to_the_rules_ruleId_endpoint_for_all_rules() throws Throwable {
        for(RuleRead pointScaleRead : ruleReads){
            try {
                lastApiResponse = rulesApi.deleteRuleWithHttpInfo(pointScaleRead.getId(), token1);
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

    @Then("^I recieve an empty list of rules$")
    public void i_recieve_an_empty_list_of_rules() throws Throwable {
        Assert.assertTrue(ruleReads.isEmpty());
    }


    private void getIdFromLocation(String location){
        String[] segments = location.split("/");
        String almostIdStr =  segments[segments.length-1];
        String idStr = almostIdStr.substring(0,almostIdStr.length()-1);
        ruleId = Long.parseLong(idStr);
    }

}
