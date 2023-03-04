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
    String testid2;//added for projects
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



    /*Error Flow */







    /* Story 17 */



    /* Story 18 */




    /* Story 19 */







}
