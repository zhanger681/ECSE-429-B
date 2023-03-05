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
    
