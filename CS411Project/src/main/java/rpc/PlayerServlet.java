package rpc;

import db.MySQLConnection;
import entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/search_player")
public class PlayerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teamName = request.getParameter("teamName");
        MySQLConnection con = new MySQLConnection();
        Map<String, Integer> count1 = con.playerCountByPosition(teamName);

        // now display the count result to the screen
        JSONArray array1 = new JSONArray();
        writeData(count1, array1);
        System.out.print(count1.size());

        Map<String, Integer> count2 = con.playerCountByNationality();
        // now display the count result to the screen
        JSONArray array2 = new JSONArray();
        writeData(count2, array2);
        JSONObject obj = new JSONObject();
        obj.put("First Advanced Query", array1);
        obj.put("Second Advanced Query", array2);
        RpcHelper.writeJsonObject(response, obj);
        con.close();
    }

    private void writeData(Map<String, Integer> count, JSONArray array) {
        for (Map.Entry<String, Integer> entry: count.entrySet()) {
            JSONObject obj = new JSONObject();
            obj.put(entry.getKey(), entry.getValue().toString());
            array.put(obj);
        }
    }
}
