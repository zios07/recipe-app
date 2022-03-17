Feature:
  Get all recipes in the database with pagination

  Scenario: User requests all the recipes
    Given I have three recipes in the database
    When I request all recipes
    Then I should get a list containing three recipes