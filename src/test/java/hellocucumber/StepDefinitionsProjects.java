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
    // Normal Flow
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


    // Story #12 - changing the title of a project
    // Alternate Flow
    @Given("a project with title B {string}")
    public void a_project_with_title_b(String current_title) throws Exception {
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

    @When("I update the project title B to {string}")
    public void i_update_the_project_title_b_to(String new_title) throws Exception {
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

    @Then("the project has title B {string}")
    public void the_project_has_title_b(String new_title) throws Exception {
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

    // Story #12 - changing the title of a project
    // Error Flow
    String errorFlowID;
    @Given("a project with title C {string}")
    public void a_project_with_title_C(String current_title) throws Exception {
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

    @When("I update the project C {string} with title {string}")
    public void i_update_the_project_title_C_to(String id, String new_title) throws Exception {
        errorFlowID = id;

        boolean completed = false;
        boolean active = false;
        String description = "sample description";

        JSONObject obj = new JSONObject();
        obj.put("title", new_title);
        obj.put("completed", completed);
        obj.put("active", active);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + id)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
    }

    @Then("the returned status code is: {string}")
    public void the_project_has_done_status_c(String new_title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + errorFlowID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }

    // Story #13 - changing the completedStatus of a project
    // Normal Flow
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

    // Story #13 - changing the completed status of a project
    // Alternate Flow
    @Given("a project with completedStatus B {string}")
    public void a_project_with_completedStatus_b(String current_completedStatus) throws Exception {
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

    @When("I update the project completedStatus B to {string}")
    public void i_update_the_project_completedStatus_b_to(String new_completedStatus) throws Exception {
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

    @Then("the project has completedStatus B {string}")
    public void the_project_has_completedStatus_b(String resulting_completedStatus) throws Exception {
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

    // Story #13 - changing the completed status of a project
    // Error Flow
    @Given("a project with completed status C {string}")
    public void a_project_with_completed_status_C(String current_title) throws Exception {
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

    @When("I update the project C {string} with completed status {string}")
    public void i_update_the_project_C_with_completed_status(String id, String completed_status) throws Exception {
        errorFlowID = id;

        String title = "Sample Title";
        boolean active = true;
        String description = "sample description";

        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("completed", completed_status);
        obj.put("active", active);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + id)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
    }

    @Then("the returned status code for the project is: {string}")
    public void the_project_has_completed_status_c(String new_title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + errorFlowID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }

    // Story #14 - changing the description of a project
    // Normal Flow
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

    // Story #14 - changing the description of a project
    // Alternate Flow
    @Given("a project with description B {string}")
    public void a_project_with_description_b(String current_description) throws Exception {
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

    @When("I update the project description B to {string}")
    public void i_update_the_project_description_b_to(String new_description) throws Exception {
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

    @Then("the project has description B {string}")
    public void the_project_has_description_b(String string) throws Exception {
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

    // Story #14 - changing the description of a project
    // Error Flow
    @Given("a project with description D {string}")
    public void a_project_with_description_D(String current_description) throws Exception {
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
        testid = (String) responseJson.get("id");
    }

    @When("I update the project {string} with description {string}")
    public void i_update_the_project_description_D(String id, String new_description) throws Exception {

        errorFlowID = id;
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
                .url("http://localhost:4567/projects/" + id)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }


    @Then("the returned status code is then: {string}")
    public void the_project_has_status_code_(String new_title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + errorFlowID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }

    // Story #15 - changing the active status of a project
    // Normal flow
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

        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", active);
        obj.put("description", description);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

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

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray projects = (JSONArray) jsonObject.get("projects");

        for (Object projectObject : projects) {
            JSONObject project = (JSONObject) projectObject;
            String activeStatus = (String) project.get("active");
            assertEquals(resulting_activeStatus, activeStatus);
        }
    }

    // Story #15 - changing the active status of a project
    // Alternate flow
    @Given("a project with active status E {string}")
    public void a_project_with_activeStatus_E(String current_activeStatus) throws Exception {
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

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }

    @When("I update the project active status E to {string}")
    public void i_update_the_project_activeStatus_E_to(String new_activeStatus) throws Exception {
        String title = "New Project Title";
        boolean completed = false;
        String description = "sample description 2";

        JSONObject obj = new JSONObject();//
        boolean active = Boolean.parseBoolean(new_activeStatus);

        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", active);
        obj.put("description", description);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();
    }

    @Then("the project has active status E {string}")
    public void the_project_has_activeStatus_E(String resulting_activeStatus) throws Exception {
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
            String activeStatus = (String) project.get("active");
            assertEquals(resulting_activeStatus, activeStatus);
        }
    }

    // Story #15 - changing the active status of a project
    // Error Flow
    @Given("a project with active status F {string}")
    public void a_project_with_description_F(String current_active_status) throws Exception {
        String title = "Random Project";
        boolean completed = true;
        String description = "Sample description";

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", current_active_status);
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

    @When("I update the project F {string} with active status {string}")
    public void i_update_the_project_active_status_F(String id, String current_active_status) throws Exception {

        errorFlowID = id;
        String title = "Random Project";
        boolean completed = true;
        String description = "very nice description";

        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", current_active_status);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + id)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
    }


    @Then("the returned status code for the project is equal to: {string}")
    public void status_code_for_nonexistant_project(String new_title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + errorFlowID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(404, response.code());
    }


/*
    // Story 11: As a user, I want to remove a specific project, to keep track of my ongoing projects
    @Given("the project with title A {string}")
    public void the_project_with_title_a(String title) throws Exception {

        boolean completed = true;
        boolean active = true;
        String description = "sample description 2";

        JSONObject obj = new JSONObject();//

        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", active);
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

    @When("I remove the project with title A {string}")
    public void i_remove_the_project_title_a(String title) throws Exception {
        JSONObject obj = new JSONObject();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        statusCode = String.valueOf(putResponse.code());
    }

   @Then("the removed project status code is {string}")
    public void the_removed_status_code_of_the_system_is(String statusCode) throws Exception{
        assertEquals(statusCode, statusCode);
    }

    @Then("the project with title A {string} will be removed")
    public void the_todo_with_title_a_will_be_removed(String title) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", title);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+testid)
                .get()
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        errorMessage = (JSONArray) responseJson.get("errorMessages");
        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with projects/"+testid, errorObject.toString());
        }
    }*/
}
