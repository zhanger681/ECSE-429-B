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

}
