Feature: View projects
  As a user,
  I want to view projects
  so that I can keep track of the project the project will be completed for.


  # Normal Flow
  Scenario Outline: View project
    Given the project "<existing_project>"
    When I view all projects
    Then the status code of the system will now be "<statusCode>"
    And the project  "<existing_project>" will be shown
    Examples:
      | existing_project   | statusCode   |
      | Project1           | 200          |
      | Project2           | 200          |

    # Alternate Flow
  Scenario Outline: View project with a specific id
    Given the project "<existing_project>" with an id of "<Id>"
    When I view the project
    Then the status code of the system will now be "<statusCode>"
    And the project  "<existing_project>" with an id "<Id>" will be shown

    Examples:
      | existing_project| Id| statusCode|
      | Project1        | 26| 200       |

  # Error Flow

  Scenario Outline: View project with a wrong id

    Given a project "<existing_project>"
    When I view a project with id <ID> and "<existing_project>"
    And project "<existing_project>" will be not be viewed
    Examples:
      | existing_project  | ID    |statusCode   |error_message                              |
      | Project1               | 5000  |404          |Could not find any instances with project|
      | Project2              | 5000  |404          |Could not find any instances with project|





