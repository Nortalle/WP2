Feature: CRUD Operations on Rule

  Background:
    Given there is a Gamification server using badges

  Scenario: create a rule
    Given I have a rule payload
    When I POST it to the /rules endpoint
    Then I receive a 201 status code for rule creation
    Then I delete the created rule

  Scenario: get a rule after creating it
    Given I have a rule payload
    When I POST it to the /rules endpoint
    When I GET it to the /rules/ruleId endpoint
    Then I receive a response containing the rule
    Then I delete the created rule

  Scenario: cant get a rule created with another token
    Given I have a rule payload
    When I POST it to the /rules endpoint
    When I GET it to the /rule/ruleId endpoint with wrong token
    Then I receive a 401 status code for rule update unauthorized
    Then I delete the created rule

  Scenario: cant get a rule with wrong id
    Given I have a rule payload
    When I POST it to the /rules endpoint
    And  I GET to the /rules/ruleId with wrong ruleId
    Then I recieve a 404 error not found for rule
    Then I delete the created rule

  Scenario: get list of all rules
    When I GET it to the /rules endpoint
    Then I recieve an array of rules

  Scenario: update values of a rule after recently creating it
    Given I have a rule payload
    When I POST it to the /rules endpoint
    Given I have a update rule payload
    When I PUT it to the /rules/ruleId endpoint
    Then I receive a 204 status code for rule no content
    When I GET it to the /rules/ruleId endpoint
    Then I recieve the modified rule
    Then I delete the created rule

  Scenario: cant update rule values with a wrong token
    Given I have a rule payload
    When I POST it to the /rules endpoint
    Given I have a update rule payload
    When I PUT it to the /rules/ruleId endpoint with wrong token
    Then I receive a 401 status code for rule update unauthorized
    Then I delete the created rule

  Scenario: cant update rule values with a wrong id
    Given I have a rule payload
    When I POST it to the /rules endpoint
    Given I have a update rule payload
    When I PUT it to the /rules/ruleId endpoint with wrong ruleId
    Then I recieve a 404 error not found for rule
    Then I delete the created rule

  Scenario: cant delete a rule with wrong token
    Given I have a rule payload
    When I POST it to the /rules endpoint
    And I DETETE to the /rules/ruleId endpoint with wrong token
    Then I receive a 401 status code for rule update unauthorized
    Then I delete the created rule

  Scenario: cant delete a rule with wrong ruleId
    Given I have a rule payload
    When I POST it to the /rules endpoint
    And I DETETE to the /rules/ruleId endpoint with wrong ruleId
    Then I recieve a 404 error not found for rule
    Then I delete the created rule

  #Scenario: deleted all rules
  #  When I GET it to the /rules endpoint
  #  And I DETETE to the /rules/ruleId endpoint for all rules
  #  Then I receive a 204 status code for rule no content
  #  When I GET it to the /rules endpoint
  #  Then I recieve an empty list of rules