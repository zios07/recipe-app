Feature:
  Delete recipe by id

  Scenario: Delete a recipe by a valid id
    Given I only have one recipe in the database and its ID is 1
    When I send a DELETE request for the recipe having ID = 1
    Then I should have 0 recipes in the database

  Scenario: Delete a recipe by an invalid id
    Given I only have one recipe in the database and its ID is 1
    When I send a DELETE request for the recipe having ID = 2
    Then I should get an error response 404 telling me that there is no recipe with ID = 2