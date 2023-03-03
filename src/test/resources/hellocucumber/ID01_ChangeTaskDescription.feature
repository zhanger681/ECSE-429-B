Feature: Changing task description
  As a user,
  I want to change a task's description,
  to better represent the work I have to do.

  # Normal Flow
  Scenario Outline: Change description of a current task to a new one
    Given a task with description A "<current_description>"
    When I update the task description A to "<new_description>"
    Then the task has description A "<resulting_description>"
    Examples:
      | current_description          | new_description           | resulting_description |
      | Project part A add comments  | new comment               | new comment           |
      | Project part C add comments  | study for midterms        | study for midterms    |

  # Alternate Flow (updating description with the same description or an empty one)
  Scenario Outline: Change description of a current task
    Given a task with description B "<current_description>"
    When I update the task description B to "<new_description>"
    Then the task has description B "<resulting_description>"
    Examples:
      | current_description          | new_description              | resulting_description       |
      | Project part A add comments  | Project part A add comments  | Project part A add comments |
      | watch Week 5 lectures        |                              |                             | 

  # Error Flow
  Scenario Outline: Change description of a non existent task
    Given a task with description C "<current_description>"
    When I update the task C "<non_existent_id>" with description "<new_description>"
    Then the returned status code is "<status_code>"
    Examples:
      | current_description      | non_existent_id        | new_description        | status_code |
      | watch 360 lectures       | 1790                   | catch up on lectures   | 404         |
      | watch lectures           | 17893                  | do assignments         | 404         |
      | watch recordings         | 378                    | do homework 3          | 404         |
