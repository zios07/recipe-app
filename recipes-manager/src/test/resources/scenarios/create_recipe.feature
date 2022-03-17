Feature:
  Create recipe

  Scenario: User creates a recipe
    Given I have no recipes saved in the database
    When I create new recipe
    Then I should have one recipe in the database

  Scenario: User creates a recipe with missing fields
    Given I have no recipes saved in the database
    When I create new recipe with no name
    Then I should get an error saying that the name is required for a recipe
      And I should have 0 recipes in the database