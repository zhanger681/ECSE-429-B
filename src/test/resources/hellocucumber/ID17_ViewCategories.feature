Feature: View categories
  As a user,
  I want to view categories
  so that I can keep track of the categories of tasks that need to be accomplished

# Normal Flow
  Scenario Outline: View category
    Given the category of the todolist exists "<existing_category>"
    When I view all categories
    Then the status code of the todo list system will now become "<statusCode>"
    And the category  "<existing_category>" will be shown to the user
    Examples:
      | existing_category   | statusCode   |
      | category1           | 200          |
      | category2           | 200          |
    
    
    #Alternate Flow
  Scenario Outline: View category with a specific id
    Given the category in the todolist app "<existing_category>" with an existing id of "<Id>"
    When I view the category of that specific id
    Then the status code of the management system will now be "<statusCode>"
    And the category  "<existing_category>" with an existing id "<Id>" will be shown to the user

    Examples:
      | existing_category| Id| statusCode|
      | category1        | 26| 200       |
  
    #Error flow

  Scenario Outline: View category with a wrong id

    Given a category "<existing_category>"
    When I view a category of the todolist with the wrong id <ID> and "<existing_category>"
    And category "<existing_category>" will be not be viewed to the user
    Examples:
      | existing_category  | ID    |statusCode   |error_message                              |
      | category1               | 5000  |404          |Could not find any instances with category|
      | category2              | 5000  |404          |Could not find any instances with category|