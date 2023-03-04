Feature: Remove a todo task
  As a user,
  I want to remove a todo task
  so that I no longer have an unneeded todo task that I've finished


  # Normal Flow
  Scenario Outline: Remove todo task
    Given a task with title A "<current_todo_task>"
    When I remove the todo task title A "<current_todo_task>"
    Then the remove todo status code is "<statusCode>"
    And the todo task with title A "<current_todo_task>" will be removed
    Examples:
      | current_todo task |statusCode |
      | Home              | 200       |
      | Office            | 200       |

  # Alternate Flow (removing todo task with the same titles)
  Scenario Outline: Remove todo task with same title
    Given a todo task with title A "<current_todo_task_1>" and another todo task "<current_todo_task_2>"
    When I remove the todo task with id "<ID>"
    Then the remove todo status code is "<statusCode>"
    And the todo task with id "<ID>" is removed
    Examples:
      | current_todo_task_1 | current_todo_task_2 | statusCode |
      | Home                | Home                |   200      |
      | Office              | Office              |   200      |

  # Error Flow
  Scenario Outline: Remove todo task with inexistent id
    Given a todo task with title A "<current_todo_task>"
    When I remove the todo task ID <ID> and title A "<current_todo_task>"
    And the todo task with title A "<current_title>" will be not be removed
    Examples:
      | current_todo_task   | ID    |statusCode   |error_message                              |
      | Home                | 5000  |404          |Could not find any instances with todo task|
      | Office              | 5000  |404          |Could not find any instances with todo task|