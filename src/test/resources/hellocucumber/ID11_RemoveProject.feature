Feature: Remove a project
  As a user,
  I want to remove a project
  so that I no longer have an unneeded project


  # Normal Flow
  Scenario Outline: Remove project
    Given a project with title A "<current_title>"
    When I remove the project title A "<current_title>"
    Then the status code is "<statusCode>"
    And the project with title A "<current_title>" will be removed
    Examples:
      | current_title |statusCode |
      | Home          | 200       |
      | Office        | 200       |

#  # Alternate Flow (updating title with the same title)
#  Scenario Outline: Remove category
#    Given a category with title A "<current_title>" and project "<project>"
#    When I remove the category title A "<current_title>"
#    Then the status code is "<statusCode>"
#    And the relationship between "<project>" and the category "<current_title>" is destroyed
#    Examples:
#      | current_title |project |statusCode|
#      | Home          |project1|   200    |
#      | Office        |project2|   200    |
#
#  # Error Flow
#  Scenario Outline: Remove category with wrong id
#    Given a category with title A "<current_title>"
#    When I remove the category ID <ID> and title A "<current_title>"
#    Then the status code is "<statusCode>"
#    And the category with title A "<current_title>" will be not be removed
#    And the error message "<error_message>" appears
#    Examples:
#      | current_title       | ID   |statusCode   |error_message                                    |
#      | Home                | 5000  |404         |Could not find any instances with categories/5000|
#      | Office              | 5000  |404         |Could not find any instances with categories/5000|