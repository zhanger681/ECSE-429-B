Feature: Remove a project
  As a user,
  I want to remove a project
  so that I no longer have an unneeded project that I've finished or no longer need to complete


  # Normal Flow
  Scenario Outline: Remove project
    Given a project with title A "<current_todo_task>"
    When I remove the project with title A "<current_todo_task>"
    Then the removed project status code is "<status_code>"
    And the project with title A "<current_todo_task>" will be removed
    Examples:
      | current_todo_task | status_code     |
      | Home              | 200             |
      | Office            | 200             |
