Feature: Strories

  Background:
    Given there is a Gamification server using pointScales

  Scenario: have a story
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code for pointScale creation
    Given I have a rule payLoad with pointScale id
    When I POST it to the /rules endpoint story
    Then I receive a 201 status code for pointScale creation
    Given I have an event payload
    When I POST it to the /events endpoint
    When I GET it from the /users/id/pointscales endpoint
    Then I delete the created pointScale
    Then I delete the created badge
    Then I delete the created rule
