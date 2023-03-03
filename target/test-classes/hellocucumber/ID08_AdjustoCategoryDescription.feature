Feature: Adjust category description
  As a user,
  I want to adjust the description of a category
  so that there is a more precise understanding of the category.

  # Normal Flow
  Scenario Outline: Adjust a category description
    Given a category with description A "<current_description>"
    When I update the category description A to "<new_description>"
    Then the category has description A "<resulting_description>"
    Examples:
      | current_description          | new_description           | resulting_description  |
      | Home in my room              | Home in the living room   | Home in the living room|
      | Office in the conference room| Office in the main room   | Office in the main room|

  # Alternate Flow (updating description with the same description or an empty one)
  Scenario Outline: Adjust a category description
    Given a category with description A "<current_description>"
    When I update the category description A to "<new_description>"
    Then the category has description A "<resulting_description>"
    Examples:
      | current_description          | new_description           | resulting_description  |
      | Home in my room              | Home in my room           | Home in my room        |
      | Office in the conference room|                           |                        |

  # Error Flow
  Scenario Outline: Change description with empty category title
    Given a category with description A "<current_description>"
    When I update the category A "<empty_title>" with description "<new_description>"
    Then the status code is "<statusCode>"
    And the error message "<error_message>" appears
    Examples:
      | current_description      | empty_title       | new_description        | statusCode  |error_message                                   |
      | Home after dinner        |                   | During lunch break     | 400         |Failed Validation: title : can not be empty     |
      | Home before dinner       |                   | Home after dinner      | 400         |Failed Validation: title : can not be empty     |
      | During lunch break       |                   | Home after dinner      | 400         |Failed Validation: title : can not be empty     |