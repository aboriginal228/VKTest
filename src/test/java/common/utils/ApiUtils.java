package common.utils;

import java.io.File;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class ApiUtils {

    public static void getFromUrl(String link) {
        Unirest.get(link).asJson();
    }

    public static String getStringFromUrl(String link) {

        HttpResponse<String> jsonNodeHttpResponse = Unirest.get(link).asString();

        return jsonNodeHttpResponse.getBody();
    }

    public static JSONObject getJsonFromUrl(String link) {

        HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get(link).asJson();

        return jsonNodeHttpResponse.getBody().getObject();
    }

    public static String postFileToUrl(String link, File file) {

        HttpResponse<JsonNode> response = Unirest.post(link)
            .field("file1", file)
            .asJson();

        return response.getBody().toString();
    }
}
