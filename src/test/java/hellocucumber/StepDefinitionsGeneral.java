package hellocucumber;

import io.cucumber.java.en.*;

import okhttp3.*;

import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.MethodOrderer.Random;

import java.io.IOException;

@TestMethodOrder(Random.class)
public class StepDefinitionsGeneral {

    String testid;
    String testid1;//added for projects
    String statusCode;
    JSONArray errorMessage;

    private static boolean isApiAvailable;
    OkHttpClient client = new OkHttpClient();
    @BeforeAll
    public void checkApiAvailability() {
        //OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:4567/")
                .build();
        try {
            Response response = client.newCall(request).execute();
            isApiAvailable = (response.code() == 200);
        } catch (IOException e) {
            Assert.fail("We are not connected, please make sure we are connected to the API");
            isApiAvailable = false;
        }
    }

    /* Story 16 */

    /*Normal flow */

    @Given("the project {string}")
    public void the_project(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the project");
        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view all projects")
    public void i_view_all_projects() throws Exception {


        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(response.code());


    }
    @Then("the status code of the system will now be {string}")
    public void the_status_code_of_the_system_will_now_be(String string) {
        assertEquals(string,statusCode);

    }
    @Then("the project  {string} will be shown")
    public void the_project_will_be_shown(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);


        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");

        for (Object projectObject : projects) {
            JSONObject project = (JSONObject) projectObject;
            String title = (String) project.get("title");
            if(testid.equals(project.get("id"))) {
                assertEquals(string, title);
            }
        }

    }

    /*Alternate Flow */
    @Given("the project {string} with an id of {string}")
    public void the_project_with_an_id_of(String string, String string2) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the project");
        obj.put("title", string);
        obj.put("26",string2);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+ string2)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view the project")
    public void i_view_the_project() throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects" )
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(response.code());
    }
    @Then("the project  {string} with an id {string} will be shown")
    public void the_project_with_an_id_will_be_shown(String string, String string2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" +string2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);

    }

    /*Error flow */

    @Given("a project {string}")
    public void a_project(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the project");
        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view a project with id {int} and {string}")
    public void i_view_a_project_with_id_and(Integer int1, String string) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+int1)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();

        statusCode = String.valueOf(putResponse.code());

        if(!statusCode.equals(200)) {
            String responseBody = putResponse.body().string();

            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(responseBody);
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }
    }
    @Then("project {string} will be not be viewed")
    public void project_will_be_not_be_viewed(String string) {
        assertEquals("404", statusCode);
    }


    /* Story 17 */

    /*Normal flow */
    @Given("the category of the todolist exists {string}")
    public void the_category_of_the_todolist_exists(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the category");
        obj.put("title category", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view all categories")
    public void i_view_all_categories() throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(response.code());
    }
    @Then("the status code of the todo list system will now become {string}")
    public void the_status_code_of_the_todo_list_system_will_now_become(String string) {
        assertEquals(string,statusCode);
    }
    @Then("the category  {string} will be shown to the user")
    public void the_category_will_be_shown_to_the_user(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);


        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray categories = (JSONArray) jsonObject.get("categories");

        for (Object projectObject : categories) {
            JSONObject project = (JSONObject) projectObject;
            String title = (String) project.get("title");
        }
    }


    /*Alternate flow */

    @Given("the category in the todolist app {string} with an existing id of {string}")
    public void the_category_in_the_todolist_app_with_an_existing_id_of(String string, String string2) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the category");
        obj.put("title", string);
        obj.put("26",string2);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+ string2)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view the category of that specific id")
    public void i_view_the_category_of_that_specific_id() throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories" )
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(response.code());
    }
    @Then("the status code of the management system will now be {string}")
    public void the_status_code_of_the_management_system_will_now_be(String string) {
        assertEquals(string,statusCode);
    }
    @Then("the category  {string} with an existing id {string} will be shown to the user")
    public void the_category_with_an_existing_id_will_be_shown_to_the_user(String string, String string2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray categories = (JSONArray) jsonObject.get("categories");

        for (Object projectObject : categories) {
            JSONObject project = (JSONObject) projectObject;
            String title = (String) project.get("title");
        }
    }

    /*Error flow */

    @Given("a category {string}")
    public void a_category(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the category");
        obj.put("title category", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view a category of the todolist with the wrong id {int} and {string}")
    public void i_view_a_category_of_the_todolist_with_the_wrong_id_and(Integer int1, String string) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+int1)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();

        statusCode = String.valueOf(putResponse.code());

        if(!statusCode.equals(200)) {
            String responseBody = putResponse.body().string();

            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(responseBody);
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }
    }
    @Then("category {string} will be not be viewed to the user")
    public void category_will_be_not_be_viewed_to_the_user(String string) {
        assertEquals("404", statusCode);
    }

    /* Story 18 */
    /*Normal flow */
    /*Alternate flow */
    @Given("a category with priority A {string}")
    public void a_category_with_priority_a(String string) throws Exception {
        String title = "Random category";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("description", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I update the category priority A to {string}")
    public void i_update_the_category_priority_a_to(String string) throws Exception {
        String title = "Random category";


        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("description", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @When("I update the category A {string} with priority {string}")
    public void i_update_the_category_a_with_priority(String string, String string2) throws Exception {
        String title = "Random category";


        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("description", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }
    @Then("the category has priority A {string}")
    public void the_category_has_priority_a(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray categories = (JSONArray) jsonObject.get("categories");

        for (Object categoryObject : categories) {
            JSONObject category = (JSONObject) categoryObject;
            String description = (String) category.get("description");
            if(testid.equals(category.get("id"))) {
                assertEquals(string, description);
            }
        }
    }



    /* Error flow */

    @Given("a task with priority  {string}")
    public void a_task_with_priority(String string) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("HIGH", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I update the priority  {string} with a valid priority {string}")
    public void i_update_the_priority_with_a_valid_priority(String string, String string2) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        String description =  "new description";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("LOW", string2);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/123456")
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }
    @Then("the status code that is returned is is {string}")
    public void the_status_code_that_is_returned_is_is(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/123456")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }


    
    /* Story 19 */

    /*Normal flow */

    @Given("the todos of the list exists {string}")
    public void the_todos_of_the_list_exists(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the todo");
        obj.put("title category", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid1 = (String) responseJson.get("id");
    }
    @When("I view all todos")
    public void i_view_all_todos() throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(response.code());
    }
    @Then("the status code of the todo list system will now be the {string}")
    public void the_status_code_of_the_todo_list_system_will_now_be_the(String string) {
        assertEquals(string,statusCode);
    }
    @Then("the todos  {string} will be viewed to the user")
    public void the_todos_will_be_viewed_to_the_user(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);


        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");
    }

    /*Alternate flow */

    @Given("the todos in the todolist app {string} with an existing id of {string}")
    public void the_todos_in_the_todolist_app_with_an_existing_id_of(String string, String string2) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the todo");
        obj.put("title", string);
        obj.put("26",string2);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+ string2)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view the todos of that specific id")
    public void i_view_the_todos_of_that_specific_id() throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos" )
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(response.code());
    }
    @Then("the todos  {string} with an existing id {string} will be shown to the user")
    public void the_todos_with_an_existing_id_will_be_shown_to_the_user(String string, String string2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/" +string2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
    }


    /*Error flow */

    @Given("a todos {string}")
    public void a_todos(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the todo");
        obj.put("title todo", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I view a todos of the todolist with the wrong id {int} and {string}")
    public void i_view_a_todos_of_the_todolist_with_the_wrong_id_and(Integer int1, String string) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+int1)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();

        statusCode = String.valueOf(putResponse.code());

        if(!statusCode.equals(200)) {
            String responseBody = putResponse.body().string();

            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(responseBody);
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }
    }
    @When("todos {string} will be not be viewed to the user")
    public void todos_will_be_not_be_viewed_to_the_user(String string) {
        assertEquals("404", statusCode);
    }




    /* Story 20 */

    /*Normal flow */
    /*Alternate flow */
    @Given("a project list {string}")
    public void a_project_list(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", string);


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I create the project {string}")
    public void i_create_the_project(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessage = (JSONArray) responseJson.get("errorMessages");
        testid1 = (String) responseJson.get("id");
    }
    @Then("the returned project status code of the system is {string}")
    public void the_returned_project_status_code_of_the_system_is(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+testid1)
                .get()
                .build();

        Response response = client.newCall(request).execute();
    }
    @Then("{string} project will be in the list {string}")
    public void project_will_be_in_the_list(String string, String string2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+testid1)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");
        String[] parts = string2.split(",");
        for (Object todoObject : projects) {
            JSONObject category = (JSONObject) todoObject;
            String title = (String) category.get("title");
            assertEquals(parts[1], title);
        }
    }

    /*Error flow*/

    @Given("an existing project {string}")
    public void an_existing_project(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", "this is the description of the project");
        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }
    @When("I create a project of the todolist with ang id {int} and {string}")
    public void i_create_a_project_of_the_todolist_with_ang_id_and(Integer int1, String string) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+int1)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();

        statusCode = String.valueOf(putResponse.code());

        if(!statusCode.equals(200)) {
            String responseBody = putResponse.body().string();

            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(responseBody);
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }
    }
    @When("project {string} will be not be created to the user")
    public void project_will_be_not_be_created_to_the_user(String string) {
        assertEquals("404", statusCode);
    }
















}
