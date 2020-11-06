package rpc;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class RpcHelper {
    // Writes a JSONArray to http response.
    public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(array);
    }

    // Writes a JSONObject to http response.
    public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(obj);
    }

    public static JSONObject readJSONObject(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(request.getReader());
        StringBuilder requestBody = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return new JSONObject(requestBody.toString());
    }

}
