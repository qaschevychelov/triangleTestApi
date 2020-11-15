package tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import commons.HttpClient;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;


public class NegativeCases {
    private HttpClient httpClient = new HttpClient();

    @BeforeMethod
    public void clean() {
        httpClient.clean();
    }

    @Test(description = "BUG - save more than 10 triangles", enabled = false)
    public void createMoreThanTen() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");
        for (int i = 0; i < 10; i++) {
            httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        }
        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(422);
        assertEquals("Unexpected status", "422", JsonPath.from(res).get("status"));
        assertEquals("Unexpected error", "Unprocessable Entity", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Limit exceeded", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle", JsonPath.from(res).get("path"));

        String all = httpClient.getAllTriangles();
        JsonArray jsonArray = new JsonParser().parse(all).getAsJsonArray();
        assertEquals("Unexpected number of triangles", 10, jsonArray.size());
    }

    @Test(description = "create without token", enabled = false)
    public void createWithoutToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPostWithoutToken(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle", JsonPath.from(res).get("path"));
    }

    @Test(description = "get without token", enabled = false)
    public void getWithoutToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGetWithoutToken("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id, JsonPath.from(res).get("path"));
    }

    @Test(description = "delete without token", enabled = false)
    public void deleteWithoutToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendDeleteWithoutToken("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id, JsonPath.from(res).get("path"));
    }

    @Test(description = "get all without token", enabled = false)
    public void getAllWithoutToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        String res = httpClient.sendGetWithoutToken("/triangle/all");

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/all", JsonPath.from(res).get("path"));
    }

    @Test(description = "get perimeter without token", enabled = false)
    public void getPerimeterWithoutToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGetWithoutToken("/triangle/" + id + "/perimeter");

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/perimeter", JsonPath.from(res).get("path"));
    }

    @Test(description = "get area without token", enabled = false)
    public void getAreaWithoutToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGetWithoutToken("/triangle/" + id + "/area");

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/area", JsonPath.from(res).get("path"));
    }
    
    @Test(description = "create with invalid token", enabled = false)
    public void createWithInvalidToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPostWithToken(map, Paths.SIMPLE, "/triangle", UUID.randomUUID().toString());

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle", JsonPath.from(res).get("path"));
    }

    @Test(description = "get with invalid token", enabled = false)
    public void getWithInvalidToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGetWithToken("/triangle/" + id, UUID.randomUUID().toString());

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id, JsonPath.from(res).get("path"));
    }

    @Test(description = "delete with invalid token", enabled = false)
    public void deleteWithInvalidToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendDeleteWithToken("/triangle/" + id, UUID.randomUUID().toString());

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id, JsonPath.from(res).get("path"));
    }

    @Test(description = "get all with invalid token", enabled = false)
    public void getAllWithInvalidToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        String res = httpClient.sendGetWithToken("/triangle/all", UUID.randomUUID().toString());

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/all", JsonPath.from(res).get("path"));
    }

    @Test(description = "get perimeter with invalid token", enabled = false)
    public void getPerimeterWithInvalidToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGetWithToken("/triangle/" + id + "/perimeter", UUID.randomUUID().toString());

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/perimeter", JsonPath.from(res).get("path"));
    }

    @Test(description = "get area with invalid token", enabled = false)
    public void getAreaWithInvalidToken() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGetWithToken("/triangle/" + id + "/area", UUID.randomUUID().toString());

        // assertions
        httpClient.assertStatusCode(401);
        assertEquals("Unexpected status", "401", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Unauthorized", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/area", JsonPath.from(res).get("path"));
    }

    @Test(description = "POST with invalid endpoint", enabled = false)
    public void postWithInvalidEndPoint() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/test");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/test", JsonPath.from(res).get("path"));
    }

    @Test(description = "get with invalid token", enabled = false)
    public void getWithInvalidEndPoint() {
        String res = httpClient.sendGet("/test");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/test", JsonPath.from(res).get("path"));
    }

    @Test(description = "delete with invalid token", enabled = false)
    public void deleteWithInvalidEndPoint() {
        String res = httpClient.sendDelete("/test");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "No message available", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/test", JsonPath.from(res).get("path"));
    }

    @Test(description = "get nonexistent triangle", enabled = false)
    public void getNonExistentTriangle() {
        String id = UUID.randomUUID().toString();
        String res = httpClient.sendGet("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Not Found", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id, JsonPath.from(res).get("path"));
    }

    @Test(description = "get deleted triangle", enabled = false)
    public void getDeletedTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");


        httpClient.sendDelete("/triangle/" + id);
        res = httpClient.sendGet("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Not Found", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id, JsonPath.from(res).get("path"));
    }

    @Test(description = "QUESTION TO ANALYST - delete nonexistent triangle", enabled = false)
    public void deleteNonExistentTriangle() {
        String id = UUID.randomUUID().toString();
        String res = httpClient.sendDelete("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected response body", "", res);
    }

    @Test(description = "QUESTION TO ANALYST - delete deleted triangle", enabled = false)
    public void deleteDeletedTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");


        httpClient.sendDelete("/triangle/" + id);
        res = httpClient.sendDelete("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected response body", "", res);
    }

    @Test(description = "get all triangles when 0", enabled = false)
    public void getAllTrianglesWhenZero() {
        String res = httpClient.sendGet("/triangle/all");

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected response body", "[]", res);
    }

    @Test(description = "get perimeter of nonexistent triangle", enabled = false)
    public void getPerimeterOfNonExistentTriangle() {
        String id = UUID.randomUUID().toString();
        String res = httpClient.sendGet("/triangle/" + id + "/perimeter");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Not Found", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/perimeter", JsonPath.from(res).get("path"));
    }

    @Test(description = "get perimeter of deleted triangle", enabled = false)
    public void getPerimeterOfDeletedTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        httpClient.sendDelete("/triangle/" + id);
        res = httpClient.sendGet("/triangle/" + id + "/perimeter");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Not Found", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/perimeter", JsonPath.from(res).get("path"));
    }

    @Test(description = "get area of nonexistent triangle", enabled = false)
    public void getAreaOfNonExistentTriangle() {
        String id = UUID.randomUUID().toString();
        String res = httpClient.sendGet("/triangle/" + id + "/area");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Not Found", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/area", JsonPath.from(res).get("path"));
    }

    @Test(description = "get area of deleted triangle", enabled = false)
    public void getAreaOfDeletedTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        httpClient.sendDelete("/triangle/" + id);
        res = httpClient.sendGet("/triangle/" + id + "/area");

        // assertions
        httpClient.assertStatusCode(404);
        assertEquals("Unexpected status", "404", JsonPath.from(res).get("status").toString());
        assertEquals("Unexpected error", "Not Found", JsonPath.from(res).get("error"));
        assertEquals("Unexpected message", "Not Found", JsonPath.from(res).get("message"));
        assertEquals("Unexpected path", "/triangle/" + id + "/area", JsonPath.from(res).get("path"));
    }
}
