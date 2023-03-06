Feature: Change project title
  As a user,
  I want to change the title of a project
  so that there is a better recognition of the name for that project.

  # Normal Flow
  Scenario Outline: Change project title
    Given a project with title A "<current_title>"
    When I update the project title A to "<new_title>"
    Then the project has title A "<resulting_title>"

    Examples:
      | current_title | new_title     | resulting_title |
      | ECSE429       | ECSE429_PartA | ECSE429_PartA   |
      | ECSE444       | ECSE444_Lab1  | ECSE444_Lab1    |

  # Alternate Flow
  Scenario Outline: Change project title with the same title
    Given a project with title B "<current_title>"
    When I update the project title B to "<new_title>"
    Then the project has title B "<resulting_title>"

    Examples:
      | current_title    | new_title        | resulting_title  |
      | ECSE428_Project1 | ECSE428_Project1 | ECSE428_Project1 |

   # Error Flow
  Scenario Outline: Change title of a non existent task
    Given a project with title C "<current_title>"
    When I update the project C "<non_existent_id>" with title "<new_title>"
    Then the returned status code is: "<status_code>"
    Examples:
      | current_title    | non_existent_id | new_title        | status_code |
      | ECSE428_Project1 | 99999           | ECSE428_Project2 | 404         |
      | ECSE428_Project1 | 0               | ECSE428_Project1 | 404         |
