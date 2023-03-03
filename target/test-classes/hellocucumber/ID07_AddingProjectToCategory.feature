Feature: Add a project to a category
  As a user,
  I want to add a project to a category
  so that I can keep track of the category the project will be completed for.


  # Normal Flow
  Scenario Outline: Adding a project to a category
    Given the category "<existing_category>"
    When I create the project "<added_project_in_category>" in the category "<existing_category>"
    Then the status code of the system is "<statusCode>"
    And the project "<added_project_in_category>" shall be in the category "<existing_category>"
    Examples:
      | existing_category   | added_project_in_category| statusCode   |
      | Home                | project1                 | 200          |
      | School              | project2                 | 200          |

     # Alternate Flow (Adding a project with no title to a category)
  Scenario Outline: Adding a project to a category with empty title
    Given the category "<existing_category>"
    When I create the project "<added_project_in_category>" in the category "<existing_category>"
    Then the status code of the system is "<statusCode>"
    And the project "<added_project_in_category>" shall be in the category "<existing_category>"
    Examples:
      | existing_category   | added_project_in_category| statusCode   |
      | Home                |                          | 200          |
      | School              |                          | 200          |

    # Error Flow
  Scenario Outline: Adding a project to a category with an id
    Given the category "<existing_category>"
    When I create the project "<added_project_in_category>" with id <id> in the category "<existing_category>"
    And the error message "<error_message>" is shown for id <id>
    Examples:
      | existing_category   | added_project_in_category|  id  |error_message                             |
      | Home                | project1                 |  1   |Could not find thing matching value for id|
      | School              | project2                 |  5   |Could not find thing matching value for id|