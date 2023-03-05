Feature: Changing project description
  As a user,
  I want to change a project's description,
  to better represent the work I have to do.

  # Normal Flow
  Scenario Outline: Change the description of a project to a new one
    Given a project with description A "<current_description>"
    When I update the project description A to "<new_description>"
    Then the project has description A "<resulting_description>"
    Examples:
      | current_description          | new_description                             | resulting_description         |
      | Unit Testing                 | Unit Testing and User Testing               | Unit Testing and User Testing |
      | Project part B comments      | Project part C comments                     | Project part C comments       |

  # Alternate Flow
  Scenario Outline: Change description of a current project with the same description or an empty description
    Given a project with description B "<current_description>"
    When I update the project description B to "<new_description>"
    Then the project has description B "<resulting_description>"
    Examples:
      | current_description          | new_description              | resulting_description       |
      | Project part A add comments  | Project part A add comments  | Project part A add comments |
      | watch Week 5 lectures        |                              |                             |

  # Error Flow
  Scenario Outline: Change description of a non existent project
    Given a project with description D "<current_description>"
    When I update the project "<non_existent_id>" with description "<new_description>"
    Then the returned status code is then: "<status_code>"
    Examples:
      | current_description      | non_existent_id        | new_description        | status_code |
      | watch 360 lectures       | 1790                   | catch up on lectures   | 404         |
      | watch lectures           | 17893                  | do assignments         | 404         |