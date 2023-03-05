Feature: Adjust category priority
  As a user,
  I want to adjust the priority of a category
  so that there is a more precise understanding of the priority of the category.

  # Normal Flow
  Scenario Outline: Adjust a category priority
    Given a category with priority A "<current_priority>"
    When I update the category priority A to "<new_priority>"
    Then the category has priority A "<resulting_priority>"
    Examples:
      | current_priority     | new_priority   | resulting_priority  |
      | HIGH                 | MEDIUM         | MEDIUM              |
      | HIGH                 | LOW            | LOW                 |

  # Alternate Flow (updating priority with the same priority or an empty one)
  Scenario Outline: Adjust a category priority
    Given a category with priority A "<current_priority>"
    When I update the category priority A to "<new_priority>"
    Then the category has priority A "<resulting_priority>"
    Examples:
      | current_priority   | new_priority   | resulting_priority  |
      | HIGH               | HIGH           | HIGH                |
      | LOW                |                |                     |

 # Error Flow
  Scenario Outline: Change priority of a non existent category
    Given a task with priority  "<current_priority>"
    When I update the priority  "<non_existent_id>" with a valid priority "<new_priority>"
    Then the status code that is returned is is "<status_code>"
    Examples:
      | current_priority         | non_existent_id        | new_priority        | status_code |
      | HIGH                     | 1790                   | LOW                 | 404         |
      | HIGH                     | 17893                  | LOW                 | 404         |
      | HIGH                     | 378                    | LOW                 | 404         |