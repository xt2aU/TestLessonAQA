import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanTest {

    @Test
    @DisplayName("test GET Request")
    public void testGetRequestWoops() {

        RestAssured.baseURI = "https://postman-echo.com";

        Response response = given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get");

        response.then().statusCode(200);

        response.then()
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"));
    }

    @Test
    @DisplayName("test GET Request Woops")
    public void testGetRequest() {

        RestAssured.baseURI = "https://postman-echo.com";

        Response response = given()
                    .get("/get");

        response.then().statusCode(200);
    }

    @Test
    @DisplayName("test POST Raw Text")
    public void testPostRequest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String requestBody = "{\"test\": \"value\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/post");

        response.then().statusCode(200);

        response.then()
                .body("data.test", equalTo("value"));
    }

    @Test
    @DisplayName("test POST Form Data")
    public void testPostRequestParam() {

        RestAssured.baseURI = "https://postman-echo.com";

        String jsonBody = "{\"foo1\": \"bar1\", \"foo2\": \"bar2\"}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/post");

        response.then().statusCode(200);

        response.then()
                .body("json.foo1", equalTo("bar1"))
                .body("json.foo2", equalTo("bar2"));
    }

    @Test
    @DisplayName("test PUT Request")
    public void testPutRequest() {

            RestAssured.baseURI = "https://postman-echo.com";

            String requestBody = "This is expected to be sent back as part of response body.";

            Response response = given()
                    .header("Content-Type", "text/plain")
                    .body(requestBody)
                    .when()
                    .put("/put");

            response.then().statusCode(200);

            response.then()
                    .body("data", equalTo("This is expected to be sent back as part of response body."));
    }

    @Test
    @DisplayName("test PATCH Request")
    public void testPatchRequest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String requestBody = "This is expected to be sent back as part of response body.";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch("/patch");

        response.then().statusCode(200);

        response.then()
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }

    @Test
    @DisplayName("test DELETE Request")
    public void testDeleteRequest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String requestBody = "This is expected to be sent back as part of response body.";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .delete("/delete");

        System.out.println("Response Body: " + response.getBody().asString());

        response.then().statusCode(200);

        response.then()
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }
}