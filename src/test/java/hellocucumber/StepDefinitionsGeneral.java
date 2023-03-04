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


    /* Story 20 */

    /* Normal Flow */

    @Given("a projects list {string}")
    public void a_projects_list(String string) throws Exception{

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
        testid2 = (String) responseJson.get("id");
    }

    @Then("the returned status code of the system is {string}")
    public void the_returned_status_code_of_the_system_is(String string) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(string), response.code());
    }


    @Then("{string} will be in the list {string}")
    public void will_be_in_the_list(String string, String string2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/"+testid2)
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
        for (Object todoObject : projects ) {
            JSONObject project = (JSONObject) todoObject;
            String title = (String) project.get("title");
            assertEquals(parts[1], title);
        }
    }
    /* Error Flow */

    @Then("the error message {string} is displayed")
    public void the_error_message_is_displayed(String string) throws Exception{

        for (Object errorObject : errorMessage) {
            assertEquals(string, errorObject.toString());
        }
    }




}
