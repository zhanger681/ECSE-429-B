Feature: Create category
  As a user,
  I want to create a new category
  so that I can have more specific options of categories for my work to be done.

  # Normal Flow
  Scenario Outline: Creating a new category
    Given a categories list "<current_categories>"
    When I create the category "<added_category_title>"
    Then the returned status code of the system is "<statusCode>"
    And "<added_category_title>" will be in the list "<resulting_categories>"
    Examples:
      | current_categories   | added_category_title  | statusCode   |resulting_categories|
      | Home                 | Office                | 200          |Home,Office        |
      | School               | Library               | 200          |School,Library     |

     # Alternate Flow (Creating a category with the same title as an existing one)
  Scenario Outline: Creating a new category with existing title
    Given a categories list "<current_categories>"
    When I create the category "<added_category_title>"
    Then the returned status code of the system is "<statusCode>"
    And "<added_category_title>" will be in the list "<resulting_categories>"
    Examples:
      | current_categories   | added_category_title  | statusCode   |resulting_categories|
      | Home                 | Home                  | 200          |Home,Home           |
      | School               | School                | 200          |School,School       |

    # Error Flow
  Scenario Outline: Creating a new category with empty title
    Given a categories list "<current_categories>"
    When I create the category "<added_category_title>"
    Then the returned status code of the system is "<statusCode>"
    And the error message "<error_message>" is displayed
    Examples:
      | current_categories   | added_category_title  | statusCode  |error_message                                   |
      | Home                 |                       | 404         |Failed Validation: title : can not be empty     |
      | School               |                       | 404         |Failed Validation: title : can not be empty     |