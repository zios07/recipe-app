Feature:
  Get recipe by id

  Scenario: Get a recipe by a valid id
    Given I only have one recipe in the database and its ID is 1
    When I send a GET request for the recipe having ID = 1
    Then I should get the one recipe that I have in the database

  Scenario: Get a recipe by an invalid id
    Given I only have one recipe in the database and its ID is 1
    When I send a GET request for the recipe having ID = 2
    Then I should get an error response 404 telling me that there is no recipe with ID = 2