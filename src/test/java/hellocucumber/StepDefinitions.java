package hellocucumber;

import io.cucumber.java.an.E;
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
public class StepDefinitions {

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

    /* Story 1 */

    /* Normal Flow */

    String testid;
    String testid2;
    String statusCode;
    JSONArray errorMessage;

    @Given("^a task with description A \"([^\"]*)\"$")
    public void a_task_with_description(String current_description) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        //String description =  "Project part A add comments";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", current_description);

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

    @When("^I update the task description A to \"([^\"]*)\"$")
    public void i_update_the_task_description_to(String new_description) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        //String description =  "new comment";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", new_description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("^the task has description A \"([^\"]*)\"$")
    public void the_task_has_description(String resulting_description) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String description = (String) todo.get("description");
            assertEquals(resulting_description, description);
        }
    }

    /* Alternative Flow */

    @Given("a task with description B {string}")
    public void a_task_with_description_b(String string) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        //String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", string);

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

    @When("I update the task description B to {string}")
    public void i_update_the_task_description_b_to(String string) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        //String description =  "";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has description B {string}")
    public void the_task_has_description_b(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String description = (String) todo.get("description");
            assertEquals(string, description);
        }
    }

    /* Error Flow */

    @Given("a task with description C {string}")
    public void a_task_with_description_c(String string) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        String description =  "watch 360 lectures";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", string);

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

    @When("I update the task C {string} with description {string}")
    public void i_update_the_task_c_with_description(String string, String string2) throws Exception {
        String title = "Random todo";
        boolean doneStatus = false;
        String description =  "new description";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", string2);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/123456")
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the returned status code is {string}")
    public void the_returned_status_code_is(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/123456")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }

    /* Story 2 */

    @Given("a todo task list {string}")
    public void a_todo_task_list(String title) throws Exception{

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", true);
        obj.put("description", "existing todo item");

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

    @When("I create the todo task {string}")
    public void i_create_the_todo_task(String title) throws Exception {

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", true);
        obj.put("description", "new todo item");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessage = (JSONArray) responseJson.get("errorMessages");
        testid2 = (String) responseJson.get("id");
    }

    @Then("the returned todo status code of the system is {string}")
    public void the_returned_status_code_of_the_system_is(String statusCode) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(statusCode), response.code());
    }


    @Then("{string} todo will be in the list {string}")
    public void todo_will_be_in_the_list(String todo, String todoList) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");
        String[] parts = todoList.split(",");
        for (Object todoObject : todos) {
            JSONObject category = (JSONObject) todoObject;
            String title = (String) category.get("title");
            assertEquals(parts[1], title);
        }
    }

    /* Error Flow */

    @Then("the todo error message {string} is displayed")
    public void the_error_message_is_displayed(String string) throws Exception{

        for (Object errorObject : errorMessage) {
            assertEquals(string, errorObject.toString());
        }
    }


    /* Story 3 */

    // Normal flow

    @Given("a task with done status A {string}")
    public void a_task_with_done_status_a(String doneStatus) throws Exception {
        String title = "Random todo";
        //boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        boolean done = Boolean.parseBoolean(doneStatus);

        obj.put("title", title);
        obj.put("doneStatus", done);
        obj.put("description", description);

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

    @When("I update the task done status A to {string}")
    public void i_update_the_task_done_status_a_to(String doneStatus) throws Exception {
        String title = "Random todo";
        //boolean doneStatus = false;
        String description =  "new comment";

        JSONObject obj = new JSONObject();

        boolean done = Boolean.parseBoolean(doneStatus);

        obj.put("title", title);
        obj.put("doneStatus", done);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has done status A {string}")
    public void the_task_has_done_status_A(String doneStatus) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String ds = (String) todo.get("doneStatus");
            assertEquals(doneStatus, ds);
        }
    }

    // Alternative flow

    @Given("a task with done status B {string}")
    public void a_task_with_done_status_b(String doneStatus) throws Exception {
        String title = "Random todo";
        //boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        boolean done = Boolean.parseBoolean(doneStatus);

        obj.put("title", title);
        obj.put("doneStatus", done);
        obj.put("description", description);

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

    @When("I update the task done status B to {string}")
    public void i_update_the_task_done_status_b_to(String doneStatus) throws Exception {
        String title = "Random todo";
        //boolean doneStatus = false;
        String description =  "new comment";

        JSONObject obj = new JSONObject();

        boolean done = Boolean.parseBoolean(doneStatus);

        obj.put("title", title);
        obj.put("doneStatus", done);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has done status B {string}")
    public void the_task_has_done_status_b(String doneStatus) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String ds = (String) todo.get("doneStatus");
            assertEquals(doneStatus, ds);
        }
    }

    // Error flow

    String errorFlowID;

    @Given("a task with done status C {string}")
    public void a_task_with_done_status_c(String doneStatus) throws Exception {
        String title = "Random todo";
        //boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        boolean done = Boolean.parseBoolean(doneStatus);

        obj.put("title", title);
        obj.put("doneStatus", done);
        obj.put("description", description);

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

    @When("I update the task C {string} with done status {string}")
    public void i_update_the_task_c_with_done_status(String id, String doneStatus) throws Exception {
        errorFlowID = id;

        String title = "Random todo";
        //boolean doneStatus = false;
        String description =  "new comment";

        JSONObject obj = new JSONObject();

        boolean done = Boolean.parseBoolean(doneStatus);

        obj.put("title", title);
        obj.put("doneStatus", done);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+id)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has done status C {string}")
    public void the_task_has_done_status_c(String doneStatus) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+errorFlowID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }

    /* Story 4 */

    @Given("a task with title A {string}")
    public void a_task_with_title_a(String title) throws Exception {
        boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

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

    @When("I remove the todo task title A {string}")
    public void i_remove_the_todo_task_title_a(String string) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        statusCode = String.valueOf(putResponse.code());
    }

    @Then("the remove todo status code is {string}")
    public void the_remove_status_code_of_the_system_is(String statusCode) throws Exception{
        assertEquals(statusCode, statusCode);
    }

    @Then("the todo task with title A {string} will be removed")
    public void the_todo_with_title_a_will_be_removed(String string) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        errorMessage = (JSONArray) responseJson.get("errorMessages");
        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with todos/"+testid, errorObject.toString());
        }
    }

    @Given("a todo task with title A {string} and another todo task {string}")
    public void a_todo_with_title_a_and_another(String title1, String title2) throws Exception{
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();

        obj.put("title", title1);
        obj.put("doneStatus", true);
        obj.put("description", "todo1");

        obj2.put("title", title2);
        obj2.put("doneStatus", false);
        obj2.put("description", "todo2");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        RequestBody body2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj2.toString());

        Request request2 = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        // parsing first post request
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        // parsing second post request
        Response response2 = client.newCall(request2).execute();
        String responseBody2 = response2.body().string();

        JSONParser parser2 = new JSONParser();
        JSONObject responseJson2 = (JSONObject) parser2.parse(responseBody2);

        testid = (String) responseJson.get("id");
        testid2 = (String) responseJson2.get("id");
    }

    @When("I remove the todo task with id {string}")
    public void i_remove_the_todo_task_id(String id) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        statusCode = String.valueOf(putResponse.code());
    }

    @Then("the todo task with id {string} is removed")
    public void the_todo_task_with_id_is_removed (String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        errorMessage = (JSONArray) responseJson.get("errorMessages");
        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with todos/"+testid, errorObject.toString());
        }
    }

    @Given("a todo task with title A {string}")
    public void a_todo_task_with_title_a(String title) throws Exception{
        boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

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

    @When("I remove the todo task ID {int} and title A {string}")
    public void i_remove_the_todo_id_and_title_a(Integer int1, String string) throws Exception{
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+int1)
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

    @Then("the todo task with title A {string} will be not be removed")
    public void the_todo_with_title_a_will_be_not_be_removed(String string) throws Exception{
        assertEquals("404", statusCode);
    }

    /* Story 5 */

    // Normal Flow

    @When("I update the task title A to {string}")
    public void i_update_the_task_title_a_to(String title) throws Exception {
        boolean doneStatus = false;
        String description =  "new comment";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has title A {string}")
    public void the_task_has_title_a(String title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String ds = (String) todo.get("title");
            assertEquals(title, ds);
        }
    }

    // Alternative flow

    @Given("a task with title B {string}")
    public void a_task_with_title_b(String title) throws Exception {
        boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

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

    @When("I update the task title B to {string}")
    public void i_update_the_task_title_b_to(String title) throws Exception {
        boolean doneStatus = false;
        String description =  "new comment";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has title B {string}")
    public void the_task_has_title_b(String title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String ds = (String) todo.get("title");
            assertEquals(title, ds);
        }
    }

    // Error Flow

    @Given("a task with title C {string}")
    public void a_task_with_title_c(String title) throws Exception {
        boolean doneStatus = false;
        String description =  "watch Week 5 lectures";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

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

    @When("I update the task C {string} with title {string}")
    public void i_update_the_task_c_with_title(String id, String title) throws Exception {
        errorFlowID = id;

        boolean doneStatus = false;
        String description =  "new comment";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+id)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the task has title C {string}")
    public void the_task_has_title_c(String title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+errorFlowID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }
}
