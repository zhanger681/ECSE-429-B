Feature: Changing task title
  As a user,
  I want to change a task's title,
  to better represent the work that I have to do.

  # Normal Flow
  Scenario Outline: Change title of a current task to a new one
    Given a task with title A "<current_title>"
    When I update the task title A to "<new_title>"
    Then the task has title A "<resulting_title>"
    Examples:
      | current_title                | new_title                 | resulting_title       |
      | Read book                    | Read English books        | Read English books    |
      | Complete A2                  | Complete Comp 360 A2      | Complete Comp 360 A2  |

  # Alternate Flow (updating title with the same title)
  Scenario Outline: Change title of a current task
    Given a task with title B "<current_title>"
    When I update the task title B to "<new_title>"
    Then the task has title B "<resulting_title>"
    Examples:
      | current_title                 | new_title                         | resulting_title       |
      | Watch lecture recording       | Watch lecture recording           | Watch lecture recording                        |

  # Error Flow
  Scenario Outline: Change title of a non existent task
    Given a task with title C "<current_title>"
    When I update the task C "<non_existent_id>" with title "<new_title>"
    Then the returned status code is "<status_code>"
    Examples:
      | current_title            | non_existent_id        | new_title              | status_code |
      | Gardening                | 17907                  | Go on a run            | 404         |
      | Wash dishes              | 178                    | Assist Brother         | 404         |