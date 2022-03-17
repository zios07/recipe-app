Feature:
  Update recipe

  Scenario: User updates an existing recipe
    Given I have one recipe saved in the database
    When I change some properties of a recipe having the same ID as the recipe in the database
    And I send it as a body of a PUT request
    Then I should have one recipe in the database
    And I should have the new values of the changed properties saved in the database

  Scenario: User updates a recipe that does not exist
    Given I have no recipe in the database
    When I change some properties of a recipe an ID that does not exist in the database
    And I send it as a body of a PUT request
    Then I should get an error response 404 telling me that the recipe I m trying to update does not exist

  Scenario: User updates a recipe with missing fields
    Given I have one recipe in the database
    When I set the value of a required field of a recipe to NULL or EMPTY
    And I send it as a body of a PUT request
    Then I should get an error response telling me that the field [name of the field] is required