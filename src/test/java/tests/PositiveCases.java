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

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class PositiveCases {
    private HttpClient httpClient = new HttpClient();

    @BeforeMethod
    public void clean() {
        httpClient.clean();
    }

    @Test(description = "get existing triangle", enabled = true)
    public void getExistingTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGet("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected triangle ID", id, JsonPath.from(res).get("id"));
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "delete existing triangle", enabled = true)
    public void deleteExistingTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendDelete("/triangle/" + id);

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected response body", "", res);
    }

    @Test(description = "get all triangles when 0", enabled = true)
    public void getAllTrianglesWhenZero() {
        String res = httpClient.sendGet("/triangle/all");

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected response body", "[]", res);
    }

    @Test(description = "create triangle with zero length sides", enabled = true)
    public void createTriangleWithZeroLengthSides() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "0;0;0");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "0.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "0.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "0.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "get all triangles when 10", enabled = true)
    public void getAllTrianglesWhenTen() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");
        for (int i = 0; i < 10; i++) {
            httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        }
        String all = httpClient.getAllTriangles();
        JsonArray jsonArray = new JsonParser().parse(all).getAsJsonArray();

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected number of triangles", 10, jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {

            assertTrue(
                    "Unexpected triangle ID",
                    jsonArray.get(i).getAsJsonObject().get("id").getAsString() != null
                            &&
                        jsonArray.get(i).getAsJsonObject().get("id").getAsString().length() == 36
            );
            assertEquals(
                    "Unexpected firstSide for triangle with UUID " + jsonArray.get(i).getAsJsonObject().get("id"),
                    "2.0",
                    jsonArray.get(i).getAsJsonObject().get("firstSide").getAsString()
            );
            assertEquals(
                    "Unexpected secondSide for triangle with UUID " + jsonArray.get(i).getAsJsonObject().get("id"),
                    "3.0",
                    jsonArray.get(i).getAsJsonObject().get("secondSide").getAsString()
            );
            assertEquals(
                    "Unexpected thirdSide for triangle with UUID " + jsonArray.get(i).getAsJsonObject().get("id"),
                    "4.0",
                    jsonArray.get(i).getAsJsonObject().get("thirdSide").getAsString()
            );
        }
    }

    @Test(description = "get perimeter of existing triangle", enabled = true)
    public void getPerimeterOfExistingTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGet("/triangle/" + id + "/perimeter");

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected triangle ID", "9.0", JsonPath.from(res).get("result").toString());
    }

    @Test(description = "get area of existing triangle", enabled = true)
    public void getAreaOfExistingTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGet("/triangle/" + id + "/area");

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected triangle ID", "2.9047375", JsonPath.from(res).get("result").toString());
    }

    @Test(description = "create simple triangle", enabled = true)
    public void createSimpleTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create simple triangle with float", enabled = true)
    public void createSimpleTriangleWithFloat() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2.5;3.6;4.1");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.5", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.6", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.1", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create simple triangle with max double", enabled = true)
    public void createSimpleTriangleWithMaxDouble() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", Double.MAX_VALUE + ";" + Double.MAX_VALUE + ";" + Double.MAX_VALUE);

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", String.valueOf(Double.MAX_VALUE), JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", String.valueOf(Double.MAX_VALUE), JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", String.valueOf(Double.MAX_VALUE), JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create simple triangle with max double + 1", enabled = true)
    public void createSimpleTriangleWithMaxDoublePlusOne() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", (Double.MAX_VALUE + 1) + ";" + (Double.MAX_VALUE + 1) + ";" + (Double.MAX_VALUE + 1));

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", String.valueOf((Double.MAX_VALUE + 1)), JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", String.valueOf((Double.MAX_VALUE + 1)), JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", String.valueOf((Double.MAX_VALUE + 1)), JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "BUG - create triangle with math operator separator", enabled = true)
    public void createTriangleWithMathOperatorSeparator() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", "*");
        map.put("input", "2*3*4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "BUG - create triangle with another separator", enabled = true)
    public void createTriangleWithAnotherSeparator() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", "|");
        map.put("input", "2|3|4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "BUG - create triangle with special character separator", enabled = true)
    public void createTriangleWithSpecialCharacterSeparator() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", "?");
        map.put("input", "2?3?4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create triangle with logic operator separator", enabled = true)
    public void createTriangleWithLogicOperatorSeparator() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", "&&");
        map.put("input", "2&&3&&4");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create triangle with default separator", enabled = true)
    public void createTriangleWithDefaultSeparator() {
        Map<String, String> map = new HashMap<>();
        map.put("input", "2;3;4");

        String res = httpClient.sendPost(map, Paths.WITHOUT_SEPARATOR, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "4.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create isosceles triangle", enabled = true)
    public void createIsoscelesTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "2;3;3");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "2.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "3.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "create equilateral triangle", enabled = true)
    public void createEquilateralTriangle() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", "3;3;3");

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");

        // assertions
        httpClient.assertStatusCode(200);
        assertTrue("Unexpected triangle ID", JsonPath.from(res).get("id") != null && JsonPath.from(res).get("id").toString().length() == 36);
        assertEquals("Unexpected firstSide", "3.0", JsonPath.from(res).get("firstSide").toString());
        assertEquals("Unexpected secondSide", "3.0", JsonPath.from(res).get("secondSide").toString());
        assertEquals("Unexpected thirdSide", "3.0", JsonPath.from(res).get("thirdSide").toString());
    }

    @Test(description = "get perimeter of max double", enabled = true)
    public void getPerimeterOfMaxDouble() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", Double.MAX_VALUE + ";" + Double.MAX_VALUE + ";" + Double.MAX_VALUE);

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGet("/triangle/" + id + "/perimeter");
        String side = String.valueOf(Double.MAX_VALUE * 3);

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected perimeter", side, JsonPath.from(res).get("result").toString());
    }

    @Test(description = "QUESTION TO ANALYST - get area of max double", enabled = true)
    public void getAreaOfMaxDouble() {
        Map<String, String> map = new HashMap<>();
        map.put("separator", ";");
        map.put("input", Double.MAX_VALUE + ";" + Double.MAX_VALUE + ";" + Double.MAX_VALUE);

        String res = httpClient.sendPost(map, Paths.SIMPLE, "/triangle");
        String id = JsonPath.from(res).get("id");

        res = httpClient.sendGet("/triangle/" + id + "/area");
        String side = String.valueOf(Math.sqrt((((Double.MAX_VALUE * 3 / 2) - Double.MAX_VALUE) * 3) * (Double.MAX_VALUE * 3 / 2)));

        // assertions
        httpClient.assertStatusCode(200);
        assertEquals("Unexpected perimeter", side, JsonPath.from(res).get("result").toString());
    }
}
