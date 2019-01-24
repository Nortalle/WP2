Feature: CRUD Operations on Badge

  Background:
    Given there is a Gamification server using badges

  Scenario: create a badge
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 201 status code for badge creation