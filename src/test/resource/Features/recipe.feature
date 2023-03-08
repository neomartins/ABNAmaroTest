Feature: recipe

  Scenario: get all recipe
    When i want get all recipe

  Scenario: get all vegetarian recipe
    When i want to get all vegetarian recipe
    Then i have only vegetarian recipe

  Scenario: get all recipe not have rice
    When i want to get all recipe that do not have rice
    Then i have only recipe without rice

  Scenario: get all recipe have rice and is made on oven
    When i want to get all recipe that have rice and is made on oven
    Then i have only recipe with rice and made on oven

  Scenario: update a recipe
    When i want to update a new recipe
    Then i get the updated recipe

  Scenario: insert a recipe
    When i want to insert a new recipe
    Then the status code is 200

  Scenario: delete a recipe
    When i want to delete a new recipe
    Then the status code is 200


