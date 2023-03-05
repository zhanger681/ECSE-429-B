Feature: Changing project description
  As a user,
  I want to change a project's completedStatus,
  to better represent the work I have to do.

  # Normal Flow
  Scenario Outline: Change the completed status of a project to a new one
    Given a project with completedStatus A "<current_completedStatus>"
    When I update the project completedStatus A to "<new_completedStatus>"
    Then the project has completedStatus A "<resulting_completedStatus>"
    Examples:
      | current_completedStatus          | new_completedStatus           | resulting_completedStatus |
      | false                            | true                          | true                      |
      | true                             | false                         | false                     |

  # Alternate Flow
  Scenario Outline: Change completed status of a project with the same completed status value
    Given a project with completedStatus B "<current_completedStatus>"
    When I update the project completedStatus B to "<new_completedStatus>"
    Then the project has completedStatus B "<resulting_completedStatus>"
    Examples:
      | current_completedStatus      | new_completedStatus          | resulting_completedStatus   |
      | true                         | true                         | true                        |
      | false                        | false                        | false                       |

  # Error Flow
  Scenario Outline: Change completed status of a non existent project
    Given a project with completed status C "<current_completedStatus>"
    When I update the project C "<non_existent_id>" with completed status "<new_completedStatus>"
    Then the returned status code for the project is: "<status_code>"
    Examples:
      | current_completedStatus  | non_existent_id        | new_completedStatus    | status_code |
      | false                    | 9999                   | true                   | 404         |
      | true                     | 12345                  | false                  | 404         |