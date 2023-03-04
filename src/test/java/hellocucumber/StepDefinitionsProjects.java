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
public class StepDefinitionsProjects {
    String testid;
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

    // Story #12 - changing the title of a project
    @Given("a project with title A {string}")
    public void a_project_with_title_a(String current_title) throws Exception {
        boolean completed = true;
        boolean doneStatus = false;
        String description = "sample description";

        JSONObject obj = new JSONObject();

        obj.put("title", current_title);
        obj.put("active", completed);
        obj.put("completed", doneStatus);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        String responseTitle = (String) responseJson.get("title");
        String responseDescription = (String) responseJson.get("description");
        testid = (String) responseJson.get("id");
    }

    @When("I update the project title A to {string}")
    public void i_update_the_project_title_a_to(String new_title) throws Exception {
        boolean completed = true;
        boolean doneStatus = false;
        String description = "sample description";

        JSONObject obj = new JSONObject();
        obj.put("title", new_title);
        obj.put("active", completed);
        obj.put("completed", doneStatus);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        if (!statusCode.equals(200)) {
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }
    }

    @Then("the project has title A {string}")
    public void the_project_has_title_a(String new_title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");

        for (Object projectObject : projects) {
            JSONObject project = (JSONObject) projectObject;
            String title = (String) project.get("title");
            assertEquals(new_title, title);
        }
    }

    // Story #13 - changing the completedStatus of a project
    @Given("a project with completedStatus A {string}")
    public void a_project_with_completedStatus_a(String current_completedStatus) throws Exception {
        String title = "New Project Title";
        boolean activeStatus = false;
        String description = "sample description 2";

        JSONObject obj = new JSONObject();
        boolean completed = Boolean.parseBoolean(current_completedStatus);


        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", activeStatus);
        obj.put("description", description);

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

    @When("I update the project completedStatus A to {string}")
    public void i_update_the_project_completedStatus_a_to(String new_completedStatus) throws Exception {
        String title = "New Project Title";
        boolean activeStatus = false;
        String description = "sample description 2";

        JSONObject obj = new JSONObject();

        boolean completed_new = Boolean.parseBoolean(new_completedStatus);

        obj.put("title", title);
        obj.put("completed", completed_new);
        obj.put("active", activeStatus);
        obj.put("description", description);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        if (!statusCode.equals(200)) {
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }
    }

    @Then("the project has completedStatus A {string}")
    public void the_project_has_completedStatus_a(String resulting_completedStatus) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");

        for (Object projectObject : projects) {
            JSONObject project = (JSONObject) projectObject;
            String completedStatus = (String) project.get("completed");
            assertEquals(resulting_completedStatus, completedStatus);
        }
    }


    // Story #14 - changing the description of a project
    @Given("a project with description A {string}")
    public void a_project_with_description_a(String current_description) throws Exception {
        String title = "Random Project";
        boolean completed = true;
        boolean doneStatus = false;

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("active", completed);
        obj.put("completed", doneStatus);
        obj.put("description", current_description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        String responseTitle = (String) responseJson.get("title");
        String responseDescription = (String) responseJson.get("description");
        testid = (String) responseJson.get("id");
    }

    @When("I update the project description A to {string}")
    public void i_update_the_project_description_to(String new_description) throws Exception {
        String title = "Random Project";
        boolean completed = true;
        boolean doneStatus = false;

        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("active", completed);
        obj.put("completed", doneStatus);
        obj.put("description", new_description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }

    @Then("the project has description A {string}")
    public void the_project_has_description_a(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");

        for (Object projectObject : projects) {
            JSONObject project = (JSONObject) projectObject;
            String description = (String) project.get("description");
            if (testid.equals(project.get("id"))) {
                assertEquals(string, description);
            }
        }
    }

    // Story #15 - changing the activeStatus of a project
    @Given("a project with active status A {string}")
    public void a_project_with_activeStatus_a(String current_activeStatus) throws Exception {
        String title = "sample title";
        boolean completedStatus = false;
        String description = "sample description 2";

        JSONObject obj = new JSONObject();
        boolean active = Boolean.parseBoolean(current_activeStatus);
        obj.put("title", title);
        obj.put("completed", completedStatus);
        obj.put("active", active);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();
//
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }

    @When("I update the project active status A to {string}")
    public void i_update_the_project_activeStatus_a_to(String new_activeStatus) throws Exception {
        String title = "New Project Title";
        boolean completed = false;
        String description = "sample description 2";

        JSONObject obj = new JSONObject();//
        boolean active = Boolean.parseBoolean(new_activeStatus);
//
        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", active);
        obj.put("description", description);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
//
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();
    }

    @Then("the project has active status A {string}")
    public void the_project_has_activeStatus_a(String resulting_activeStatus) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
//
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
//
        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");
//
        for (Object projectObject : projects) {
            JSONObject project = (JSONObject) projectObject;
            String activeStatus = (String) project.get("active");
            assertEquals(resulting_activeStatus, activeStatus);
        }
    }
}


/*

    */
/* Story 11 *//*

    */
/* Normal Flow *//*

    @Given("a project with title A {string}")
    public void a_project_with_title_a(String current_title) throws Exception{
        boolean doneStatus = false;
        String description =  "This project must be completed by monday";
        boolean active = true;



        JSONObject obj = new JSONObject();

        obj.put("title", current_title);
        obj.put("active", active);
        obj.put("completed", doneStatus);
        obj.put("description", "This project must be completed by monday");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        String responseTitle = (String) responseJson.get("title");
        String responseDescription = (String) responseJson.get("description");

        testid = (String) responseJson.get("id");
    }
    @When("I remove the project title A {string}")
    public void i_remove_the_project_title_a(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+testid)
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
    @Then("the project with title A {string} will be removed")
    public void the_project_with_title_a_will_be_removed(String string) throws Exception{
       assertEquals(200, statusCode);
    }
}
*/
