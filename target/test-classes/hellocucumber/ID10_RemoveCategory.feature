Feature: Remove a category
  As a user,
  I want to remove a category
  so that I no longer have an unneeded category


  # Normal Flow
  Scenario Outline: Remove category
    Given a category with title A "<current_title>"
    When I remove the category title A "<current_title>"
    Then the status code is "<statusCode>"
    And the category with title A "<current_title>" will be removed
    Examples:
      | current_title |statusCode |
      | Home          | 200       |
      | Office        | 200       |

  # Alternate Flow (updating title with the same title)
  Scenario Outline: Remove category
    Given a category with title A "<current_title>" and project "<project>"
    When I remove the category title A "<current_title>"
    Then the status code is "<statusCode>"
    And the relationship between "<project>" and the category "<current_title>" is destroyed
    Examples:
      | current_title |project |statusCode|
      | Home          |project1|   200    |
      | Office        |project2|   200    |
#
#  # Error Flow
#  Scenario Outline: Remove title with empty category title
#    Given a category with title A "<current_title>"
#    When I update the category title A to "<new_title>"
#    Then the status code is "<statusCode>"
#    And the error message "<error_message>" appears
#    Examples:
#      | current_title       | new_title    | statusCode  |error_message                                   |
#      | Home                |              | 400         |Failed Validation: title : can not be empty     |
#      | Office              |              | 400         |Failed Validation: title : can not be empty     |