Feature: Create project
  As a user,
  I want to create a new project
  so that I add more projects to do

  # Normal Flow
  Scenario Outline: Creating a new project
    Given a project list "<current_project_projects>"
    When I create the project "<added_project_project>"
    Then the returned project status code of the system is "<statusCode>"
    And "<added_project_project>" project will be in the list "<resulting_project_projects>"
    Examples:
      | current_project_projects  | added_project_project  | statusCode   |resulting_project_projects|
      | Project1               | Office work         | 200          |Home,Office work       |
      | Project2               | Library             | 200          |School,Library         |
    
    # Alternate Flow (Creating a project with the same title as an existing one)
  Scenario Outline: Creating a new project
    Given a project list "<current_project_projects>"
    When I create the project "<added_project_project>"
    Then the returned project status code of the system is "<statusCode>"
    And "<added_project_project>" project will be in the list "<resulting_project_projects>"
    Examples:
      | current_project_projects  | added_project_project  | statusCode   |resulting_project_projects|
      | Project1               | Office work         | 200          |Home,Office work       |
      | Project2               | Library             | 200          |School,Library         |

    #Errow Flow

  Scenario Outline: Creating project with an id

    Given an existing project "<existing_project>"
    When I create a project of the todolist with ang id <ID> and "<existing_project>"
    And project "<existing_project>" will be not be created to the user
    Examples:
      | existing_project  | ID    |statusCode   |error_message                              |
      | project1               | 5000  |404          |Could not find any instances with project|
      | project2              | 5000  |404          |Could not find any instances with project|