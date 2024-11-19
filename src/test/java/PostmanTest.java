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
        // Установка базового URL
        RestAssured.baseURI = "https://postman-echo.com";

        // Выполнение GET-запроса
        Response response = given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get");

        // Проверка статуса ответа
        response.then().statusCode(200);

        // Проверка тела ответа на наличие ожидаемых параметров
        response.then()
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"));
    }

    @Test
    @DisplayName("test GET Request Woops")
    public void testGetRequest() {
        // Установка базового URL
        RestAssured.baseURI = "https://postman-echo.com";

        // Выполнение GET-запроса
        Response response = given()
                    .get("/get");

        // Проверка статуса ответа
        response.then().statusCode(200);
    }

    @Test
    @DisplayName("test POST Raw Text")
    public void testPostRequest() {
        // Установка базового URL
        RestAssured.baseURI = "https://postman-echo.com";

        // Создание тела запроса
        String requestBody = "{\"test\": \"value\"}";

        // Выполнение POST-запроса
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/post");

        // Проверка статуса ответа
        response.then().statusCode(200);

        // Проверка тела ответа на наличие ожидаемых параметров
        response.then()
                .body("data.test", equalTo("value"));
    }

    @Test
    @DisplayName("test POST Form Data")
    public void testPostRequestParam() {
        // Установка базового URL
        RestAssured.baseURI = "https://postman-echo.com";

        // Создание JSON-объекта
        String jsonBody = "{\"foo1\": \"bar1\", \"foo2\": \"bar2\"}";

        // Выполнение POST-запроса с JSON-телом
        Response response = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/post");

        // Проверка статуса ответа
        response.then().statusCode(200);

        // Проверка тела ответа на наличие ожидаемых параметров
        response.then()
                .body("json.foo1", equalTo("bar1"))
                .body("json.foo2", equalTo("bar2"));
    }

    @Test
    @DisplayName("test PUT Request")
    public void testPutRequest() {
            // Установка базового URL
            RestAssured.baseURI = "https://postman-echo.com";

            // Создание тела запроса
            String requestBody = "This is expected to be sent back as part of response body.";

            // Выполнение PUT-запроса
            Response response = given()
                    .header("Content-Type", "text/plain") // Устанавливаем Content-Type как текст
                    .body(requestBody) // Устанавливаем тело запроса
                    .when()
                    .put("/put");

            // Проверка статуса ответа
            response.then().statusCode(200);

            // Проверка тела ответа на наличие ожидаемого значения
            response.then()
                    .body("data", equalTo("This is expected to be sent back as part of response body."));
    }

    @Test
    @DisplayName("test PATCH Request")
    public void testPatchRequest() {
        // Установка базового URL
        RestAssured.baseURI = "https://postman-echo.com";

        // Создание JSON-объекта для тела запроса
        String requestBody = "This is expected to be sent back as part of response body.";

        // Выполнение PATCH-запроса
        Response response = given()
                .header("Content-Type", "application/json") // Устанавливаем Content-Type как application/json
                .body(requestBody) // Устанавливаем тело запроса
                .when()
                .patch("/patch");

        // Проверка статуса ответа
        response.then().statusCode(200);

        // Проверка тела ответа на наличие ожидаемого значения
        response.then()
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }

    @Test
    @DisplayName("test DELETE Request")
    public void testDeleteRequest() {
        // Установка базового URL
        RestAssured.baseURI = "https://postman-echo.com";

        // Создание тела запроса
        String requestBody = "This is expected to be sent back as part of response body.";

        // Выполнение DELETE-запроса с телом
        Response response = given()
                .header("Content-Type", "application/json") // Устанавливаем Content-Type как application/json
                .body(requestBody) // Устанавливаем тело запроса
                .when()
                .delete("/delete");

        // Печать тела ответа для отладки
        System.out.println("Response Body: " + response.getBody().asString());

        // Проверка статуса ответа
        response.then().statusCode(200);

        // Проверка тела ответа на наличие ожидаемого значения
        response.then()
                .body("data", equalTo("This is expected to be sent back as part of response body."));
    }
}