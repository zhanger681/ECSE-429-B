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
public class StepDefinitionsCategories {
    /* Story 6 */
    String testid;
    String testid2;//added for Categories
    String statusCode;
    JSONArray errorMessage;
    /* Normal Flow */
    /* Alternative Flow */

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

    @Given("a categories list {string}")
    public void a_categories_list(String string) throws Exception{

        JSONObject obj = new JSONObject();

        obj.put("title", string);

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
    @When("I create the category {string}")
    public void i_create_the_category(String string) throws Exception {

        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
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
                .url("http://localhost:4567/categories/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(string), response.code());
    }


    @Then("{string} will be in the list {string}")
    public void will_be_in_the_list(String string, String string2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray categories = (JSONArray) jsonObject.get("categories");
        String[] parts = string2.split(",");
        for (Object todoObject : categories) {
            JSONObject category = (JSONObject) todoObject;
            String title = (String) category.get("title");
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


    /* Story 7 */

    /* Normal Flow */
    /* Alternative Flow */
    @Given("the category {string}")
    public void the_category(String string) throws Exception{

        JSONObject obj = new JSONObject();

        obj.put("title", string);


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
    @When("I create the project {string} in the category {string}")
    public void i_create_the_project_in_the_category(String string, String string2) throws Exception{

        JSONObject obj = new JSONObject();

        boolean active = true;

        obj.put("active", active);
        obj.put("description", "this is the description of the project");
        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid+"/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid2 = (String) responseJson.get("id");
    }
    @Then("the status code of the system is {string}")
    public void the_status_code_of_the_system_is(String string) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid+"/projects")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(string), response.code());
    }

    @Then("the project {string} shall be in the category {string}")
    public void the_project_shall_be_in_the_category(String string, String string2) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid+"/projects")
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
            if(testid2.equals(project.get("id"))) {
                assertEquals(string, title);
            }
        }
    }

    @When("I create the project {string} with id {int} in the category {string}")
    public void i_create_the_project_with_id_in_the_category(String string, Integer int1, String string2) throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("id", int1);
        obj.put("description", "this is the description of the project");
        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid+"/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessage = (JSONArray) responseJson.get("errorMessages");
        testid2 = (String) responseJson.get("id");
    }

    /* Error Flow */

    @Then("the error message {string} is shown for id {int}")
    public void the_error_message_is_shown_for_id(String string, int int1) throws Exception{

        JSONObject obj = new JSONObject();


        obj.put("id", int1);
        obj.put("description", "this is the description of the project");
        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid+"/projects")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONArray errorMessage = (JSONArray) responseJson.get("errorMessages");

        for (Object errorObject : errorMessage) {
            assertEquals(string, errorObject.toString());
        }
    }

    /* Story 8 */

    /* Normal Flow */
    /* Alternative Flow */
    @Given("a category with description A {string}")
    public void a_category_with_description_a(String string) throws Exception{
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
    @When("I update the category description A to {string}")
    public void i_update_the_category_description_a_to(String string) throws Exception{
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
    @Then("the category has description A {string}")
    public void the_category_has_description_a(String string) throws Exception{
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

    /* Error Flow */

    @When("I update the category A {string} with description {string}")
    public void i_update_the_category_a_with_description(String string, String string2) throws Exception{
        String title = "Random category";


        JSONObject obj = new JSONObject();

        obj.put("title", string);
        obj.put("description", string2);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessage = (JSONArray) responseJson.get("errorMessages");
        statusCode = String.valueOf(putResponse.code());
    }

    @Then("the status code is {string}")
    public void the_status_code_is(String string) {
        assertEquals(string, statusCode);
    }
    @Then("the error message {string} appears")
    public void the_error_message_appears(String string) {
        for (Object errorObject : errorMessage) {
            assertEquals(string, errorObject.toString());
        }
    }


    /* Story 9 */

    /* Normal Flow */
    /* Alternative Flow */
    @Given("a category with title A {string}")
    public void a_category_with_title_a(String string) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", string);


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
    @When("I update the category title A to {string}")
    public void i_update_the_category_title_a_to(String string) throws Exception{



        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .put(body)
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        if(!statusCode.equals(200)) {
            errorMessage = (JSONArray) responseJson.get("errorMessages");
        }

    }
    @Then("the category has title A {string}")
    public void the_category_has_title_a(String string) throws Exception{
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
            String title = (String) category.get("title");
            assertEquals(string, title);
        }
    }



    /* Story 10 */

    /* Normal Flow */
    /* Alternative Flow */

    @When("I remove the category title A {string}")
    public void i_remove_the_category_title_a(String string) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .delete(body)
                .build();

        Response putResponse = client.newCall(request).execute();
//        String responseBody = putResponse.body().string();
//
//        JSONParser parser = new JSONParser();
//        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
//        if(!statusCode.equals(200)) {
//            errorMessage = (JSONArray) responseJson.get("errorMessages");
//        }
    }
    @Then("the category with title A {string} will be removed")
    public void the_category_with_title_a_will_be_removed(String string) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .get()
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        errorMessage = (JSONArray) responseJson.get("errorMessages");
        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with categories/"+testid, errorObject.toString());
        }
    }

    @Given("a category with title A {string} and project {string}")
    public void a_category_with_title_a_and_project(String string, String string2) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", string);


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



        JSONObject obj2 = new JSONObject();

        boolean active = true;

        obj.put("active", active);
        obj.put("description", "this is the description of the project");
        obj.put("title", string2);

        RequestBody body2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj2.toString());

        Request request2 = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid+"/projects")
                .post(body2)
                .build();

        Response response2 = client.newCall(request2).execute();
        String responseBody2 = response2.body().string();

        JSONParser parser2 = new JSONParser();
        JSONObject responseJson2 = (JSONObject) parser2.parse(responseBody2);

        testid2 = (String) responseJson2.get("id");

    }
    @Then("the relationship between {string} and the category {string} is destroyed")
    public void the_relationship_between_and_the_category_is_destroyed(String string, String string2) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", string);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+testid)
                .get()
                .build();

        Response putResponse = client.newCall(request).execute();
        String responseBody = putResponse.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        statusCode = String.valueOf(putResponse.code());
        errorMessage = (JSONArray) responseJson.get("errorMessages");
        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with categories/"+testid, errorObject.toString());
        }
    }


}
