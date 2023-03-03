Feature: Change category title
  As a user,
  I want to change the title of a category
  so that there is a better recognition of the name for that category.

  # Normal Flow
  Scenario Outline: Change category title
    Given a category with title A "<current_title>"
    When I update the category title A to "<new_title>"
    Then the category has title A "<resulting_title>"
    Examples:
      | current_title | new_title | resulting_title  |
      | Home          | Office    | Office           |
      | Office        | Home      | Home             |

  # Alternate Flow (updating title with the same title)
  Scenario Outline: Change category title with same title
    Given a category with title A "<current_title>"
    When I update the category title A to "<new_title>"
    Then the category has title A "<resulting_title>"
    Examples:
      | current_title | new_title | resulting_title  |
      | Home          | Home      | Home             |
      | Office        | Office    | Office           |

  # Error Flow
  Scenario Outline: Change title with empty category title
    Given a category with title A "<current_title>"
    When I update the category title A to "<new_title>"
    Then the status code is "<statusCode>"
    And the error message "<error_message>" appears
    Examples:
      | current_title       | new_title    | statusCode  |error_message                                   |
      | Home                |              | 400         |Failed Validation: title : can not be empty     |
      | Office              |              | 400         |Failed Validation: title : can not be empty     |
