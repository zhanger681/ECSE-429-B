Feature: View todos
  As a user,
  I want to view todos
  so that I can keep track of the todos of tasks that need to be finished

# Normal Flow
  Scenario Outline: View todos
    Given the todos of the list exists "<existing_todos>"
    When I view all todos
    Then the status code of the todo list system will now be the "<statusCode>"
    And the todos  "<existing_todos>" will be viewed to the user
    Examples:
      | existing_todos   | statusCode   |
      | todos1           | 200          |
      | todos2           | 200          |
    
    
       #Alternate Flow
  Scenario Outline: View todos with a specific id
    Given the todos in the todolist app "<existing_todos>" with an existing id of "<Id>"
    When I view the todos of that specific id
    Then the status code of the management system will now be "<statusCode>"
    And the todos  "<existing_todos>" with an existing id "<Id>" will be shown to the user

    Examples:
      | existing_todos| Id| statusCode|
      | todos1        | 26| 200       |
    
    #Errow Flow

  Scenario Outline: View todos with a wrong id

    Given a todos "<existing_todos>"
    When I view a todos of the todolist with the wrong id <ID> and "<existing_todos>"
    And todos "<existing_todos>" will be not be viewed to the user
    Examples:
      | existing_todos  | ID    |statusCode   |error_message                              |
      | todos1               | 5000  |404          |Could not find any instances with todos|
      | todos2              | 5000  |404          |Could not find any instances with todos|