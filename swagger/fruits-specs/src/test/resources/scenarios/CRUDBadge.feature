Feature: CRUD Operations on Badge

  Background:
    Given there is a Gamification server using badges

  Scenario: create a badge
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 201 status code for badge creation

  Scenario: get a badge after creating it
    Given I have a badge payload
    When I POST it to the /badges endpoint
    When I GET it to the /badges/badgeId endpoint
    Then I receive a response containing the badge

  #Scenario: cant get a badge created with another token
  #  Given I have a badge payload
  #  When I POST it to the /badges endpoint
  #  When I GET it to the /badges/badgeId endpoint with wrong token
  #  Then I a recieve a response containing a null
