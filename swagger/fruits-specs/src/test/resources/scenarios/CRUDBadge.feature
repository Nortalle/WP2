Feature: CRUD Operations on Badge

  Background:
    Given there is a Gamification server using badges

  Scenario: create a badge
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 201 status code for badge creation
    Then I delete the created badge

  Scenario: get a badge after creating it
    Given I have a badge payload
    When I POST it to the /badges endpoint
    When I GET it to the /badges/badgeId endpoint
    Then I receive a response containing the badge
    Then I delete the created badge

  Scenario: cant create a badge with an already existing name
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 204 status code for badge no content
    Then I delete the created badge

  Scenario: cant get a badge created with another token
    Given I have a badge payload
    When I POST it to the /badges endpoint
    When I GET it to the /badges/badgeId endpoint with wrong token
    Then I a recieve a response containing a 401 error
    Then I delete the created badge

  Scenario: cant get a badge with wrong id
    Given I have a badge payload
    When I POST it to the /badges endpoint
    And  I GET to the /badges/badgeId with wrong badgeId
    Then I recieve a 404 error not found
    Then I delete the created badge

  Scenario: get list of all badges
    When I GET it to the /badges endpoint
    Then I recieve an array of badges

  Scenario: update values of a badge after recently creating it
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Given I have a update badge payload
    When I PUT it to the /badges/badgeId endpoint
    Then I receive a 204 status code for badge no content
    When I GET it to the /badges/badgeId endpoint
    Then I recieve the modified badge
    Then I delete the created badge

  Scenario: cant update badge values with a wrong token
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Given I have a update badge payload
    When I PUT it to the /badges/badgeId endpoint with wrong token
    Then I receive a 401 status code for badge update unothorized
    Then I delete the created badge

  Scenario: cant update badge values with a wrong id
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Given I have a update badge payload
    When I PUT it to the /badges/badgeId endpoint with wrong badgeId
    Then I recieve a 404 error not found
    Then I delete the created badge

  Scenario: cant delete a badge with wrong token
    Given I have a badge payload
    When I POST it to the /badges endpoint
    And I DETETE to the /badges/badgeId endpoint with wrong token
    Then I receive a 401 status code for badge update unothorized
    Then I delete the created badge

  Scenario: cant delete a badge with wrong badgeId
    Given I have a badge payload
    When I POST it to the /badges endpoint
    And I DETETE to the /badges/badgeId endpoint with wrong badgeId
    Then I recieve a 404 error not found
    Then I delete the created badge

  #Scenario: deleted all badges
  #  When I GET it to the /badges endpoint
  #  And I DETETE to the /badges/badgeId endpoint for all badges
  #  Then I receive a 204 status code for badge no content
  #  When I GET it to the /badges endpoint
  # Then I recieve an empty list of badges





