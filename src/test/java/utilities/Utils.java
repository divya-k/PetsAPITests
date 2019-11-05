package utilities;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;

public class Utils {
    //Global Setup Variables
    public static String path;
    public static String jsonPathTerm;

    //Sets Base URI
    public static void setBaseURI() {
        RestAssured.baseURI = "https://api.weatherbit.io/v2.0/current";
    }

    //Sets base path
    public static void setBasePath(String basePathTerm) {
        RestAssured.basePath = basePathTerm;
    }

    //Reset Base URI (after test)
    public static void resetBaseURI() {
        RestAssured.baseURI = null;
    }

    //Reset base path
    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

    //Sets ContentType
    public static void setContentType(ContentType Type) {
        given().contentType(Type);
    }

    //Sets Json path term
    public static void setJsonPathTerm(String jsonPath) {
        jsonPathTerm = jsonPath;
    }

    //Created search query path with lat , lon and key
    public static void createSearchQueryPath(String lat, String lon, String key) {
        path = "?lat=" + lat + "&lon=" + lon + "&key=" + key;
    }

    public static void createSearchQueryPath1(String postcode, String key) {
        path = "?postal_code=" + postcode + "&key=" + key;
    }



    //Returns response
    public static Response getResponse() {
        return get(path);
    }

    //Returns JsonPath object
    public static JsonPath getJsonPath(Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }
}