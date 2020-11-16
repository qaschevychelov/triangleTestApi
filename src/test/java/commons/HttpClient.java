package commons;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ConfigManager;
import utils.Paths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Stream;

import static org.testng.AssertJUnit.assertEquals;

public class HttpClient {
    Response response;
    /**
     * send POST with body
     * @param params request body params
     * @param path to the template body
     * @param endpoint API endpoint
     * @return String
     */
    @Step("send POST with body")
    public String sendPost(Map<String, String> params, Paths path, String endpoint) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", ConfigManager.getToken())
                .body(getBody(params, path))
                .when()
                .post(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send POST without token
     * @param params request body params
     * @param path to the template body
     * @param endpoint API endpoint
     * @return String
     */
    @Step("send POST without token")
    public String sendPostWithoutToken(Map<String, String> params, Paths path, String endpoint) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(getBody(params, path))
                .when()
                .post(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send GET
     * @param endpoint API endpoint
     * @return String
     */
    @Step("send GET")
    public String sendGet(String endpoint) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", ConfigManager.getToken())
                .when()
                .get(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send GET without token
     * @param endpoint API endpoint
     * @return String
     */
    @Step("send GET without token")
    public String sendGetWithoutToken(String endpoint) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send DELETE
     * @param endpoint API endpoint
     * @return String
     */
    @Step("send DELETE")
    public String sendDelete(String endpoint) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", ConfigManager.getToken())
                .when()
                .delete(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send DELETE without token
     * @param endpoint API endpoint
     * @return String
     */
    @Step("send DELETE without token")
    public String sendDeleteWithoutToken(String endpoint) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * @param params request body params
     * @param path to the template body
     * @return String
     */
    private String getBody(Map<String, String> params, Paths path) {
        String template = getTemplate(path);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            template = template.replaceAll("\\{\\{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }

    /**
     * @param path to the template body for the request
     * @return String
     */
    private String getTemplate(Paths path) {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(java.nio.file.Paths.get(System.getProperty("user.dir") + "/src/test/resources/templates/" + path.getPath()),
                StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * clean previously created triangles
     */
    @Step("clean previously created triangles")
    public void clean() {
        String res = getAllTriangles();
        JsonArray arr = new JsonParser().parse(res).getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("X-User", ConfigManager.getToken())
                    .when()
                    .delete(ConfigManager.getEnvUrl() + "/triangle/" + arr.get(i).getAsJsonObject().get("id").getAsString());
        }
    }

    /**
     * assert status code
     * @param code status code
     */
    @Step("assert status code")
    public void assertStatusCode(int code) {
        assertEquals("Unexpected response status code", code, response.statusCode());
    }

    /**
     * get all triangles
     * @return String
     */
    @Step("get all triangles")
    public String getAllTriangles() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", ConfigManager.getToken())
                .when()
                .get(ConfigManager.getEnvUrl() + "/triangle/all").thenReturn().getBody().asString();
    }

    /**
     * send POST with token
     * @param params request body params
     * @param path to the template body
     * @param endpoint API endpoint
     * @param token API token
     * @return String
     */
    @Step("send POST with token")
    public String sendPostWithToken(Map<String, String> params, Paths path, String endpoint, String token) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", token)
                .body(getBody(params, path))
                .when()
                .post(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send GET with token
     * @param endpoint API endpoint
     * @param token API token
     * @return String
     */
    @Step("send GET with token")
    public String sendGetWithToken(String endpoint, String token) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", token)
                .when()
                .get(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }

    /**
     * send DELETE with token
     * @param endpoint API endpoint
     * @param token API token
     * @return String
     */
    @Step("send DELETE with token")
    public String sendDeleteWithToken(String endpoint, String token) {
        this.response = null;
        this.response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-User", token)
                .when()
                .delete(ConfigManager.getEnvUrl() + endpoint).thenReturn();
        return this.response.getBody().asString();
    }
}
