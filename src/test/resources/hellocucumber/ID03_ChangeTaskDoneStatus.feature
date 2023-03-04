Feature: Changing task Done Status
  As a user,
  I want to change a task's done status,
  to better represent the work that I have left to do.

  # Normal Flow
  Scenario Outline: Change done status of a current task to a new one
    Given a task with done status A "<current_done_status>"
    When I update the task done status A to "<new_done_status>"
    Then the task has done status A "<resulting_done_status>"
    Examples:
      | current_done_status          | new_done_status           | resulting_done_status |
      | true                         | false                     | false                 |

  # Alternate Flow (updating done status with the same done status value)
  Scenario Outline: Change done status of a current task
    Given a task with done status B "<current_done_status>"
    When I update the task done status B to "<new_done_status>"
    Then the task has done status B "<resulting_description>"
    Examples:
      | current_done_status          | new_done_status              | resulting_description       |
      | true                         | true                         | true                        |

  # Error Flow
  Scenario Outline: Change done status of a non existent task
    Given a task with done status C "<current_done_status>"
    When I update the task C "<non_existent_id>" with done status "<new_done_status>"
    Then the returned status code is "<status_code>"
    Examples:
      | current_done_status      | non_existent_id        | new_done_status        | status_code |
      | false                    | 1790                   | true                   | 404         |
      | true                     | 17893                  | false                  | 404         |