Feature: CRUD Operations on PointScale

  Background:
    Given there is a Gamification server using pointScales

  Scenario: create a pointScale
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code for pointScale creation
    Then I delete the created pointScale

Scenario: get a pointScale after creating it
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    When I GET it to the /pointScales/pointScaleId endpoint
    Then I receive a response containing the pointScale
    Then I delete the created pointScale

Scenario: cant create a pointScale with an already existing name
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Then I receive a 204 status code for pointScale no content
  Then I delete the created pointScale

  Scenario: cant get a pointScale created with another token
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    When I GET it to the /pointScale/pointScaleId endpoint with wrong token
    Then I receive a 401 status code for pointScale update unauthorized
    Then I delete the created pointScale

  Scenario: cant get a pointScale with wrong id
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    And  I GET to the /pointScales/pointScaleId with wrong pointScaleId
    Then I recieve a 404 error not found for pointScale
    Then I delete the created pointScale

  Scenario: get list of all pointScales
    When I GET it to the /pointScales endpoint
    Then I recieve an array of pointScales

  Scenario: update values of a pointScale after recently creating it
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Given I have a update pointScale payload
    When I PUT it to the /pointScales/pointScaleId endpoint
    Then I receive a 204 status code for pointScale no content
    When I GET it to the /pointScales/pointScaleId endpoint
    Then I recieve the modified pointScale
    Then I delete the created pointScale

  Scenario: cant update pointScale values with a wrong token
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Given I have a update pointScale payload
    When I PUT it to the /pointScales/pointScaleId endpoint with wrong token
    Then I receive a 401 status code for pointScale update unauthorized
    Then I delete the created pointScale

  Scenario: cant update pointScale values with a wrong id
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Given I have a update pointScale payload
    When I PUT it to the /pointScales/pointScaleId endpoint with wrong pointScaleId
    Then I recieve a 404 error not found for pointScale
    Then I delete the created pointScale

  Scenario: cant delete a pointScale with wrong token
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    And I DETETE to the /pointScales/pointScaleId endpoint with wrong token
    Then I receive a 401 status code for pointScale update unauthorized
    Then I delete the created pointScale

  Scenario: cant delete a pointScale with wrong pointScaleId
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    And I DETETE to the /pointScales/pointScaleId endpoint with wrong pointScaleId
    Then I recieve a 404 error not found for pointScale
    Then I delete the created pointScale

  # Scenario: deleted all pointScales
    #When I GET it to the /pointScales endpoint
    #And I DETETE to the /pointScales/pointScaleId endpoint for all pointScales
    #Then I receive a 204 status code for pointScale no content
    #When I GET it to the /pointScales endpoint
    #Then I recieve an empty list of pointScales