Feature: Changing project Active Status
  As a user,
  I want to change a project's active status,
  to better represent the work that I have left to do.

  # Normal Flow
  Scenario Outline: Change active status of a current project to a new one
    Given a project with active status A "<current_active_status>"
    When I update the project active status A to "<new_active_status>"
    Then the project has active status A "<resulting_active_status>"
    Examples:
      | current_active_status          | new_active_status           | resulting_active_status |
      | true                           | false                       | false                   |

#  # Alternate Flow (updating done status with the same done status value)
#  Scenario Outline: Change done status of a current task
#    Given a task with done status B "<current_done_status>"
#    When I update the task done status B to "<new_done_status>"
#    Then the task has done status B "<resulting_description>"
#    Examples:
#      | current_done_status          | new_done_status              | resulting_description       |
#      | true                         | true                         | true                        |
#
#  # Error Flow
#  Scenario Outline: Change done status of a non existent task
#    Given a task with done status C "<current_done_status>"
#    When I update the task C "<non_existent_id>" with done status "<new_done_status>"
#    Then the returned status code is "<status_code>"
#    Examples:
#      | current_done_status      | non_existent_id        | new_done_status        | status_code |
#      | false                    | 1790                   | true                   | 404         |
#      | true                     | 17893                  | false                  | 404         |