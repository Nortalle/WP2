Feature: CRUD Operations on PointScale

  Background:
    Given there is a Gamification server using pointScales

  Scenario: create a pointScale
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code for pointScale creation