package rpc;

import db.Neo4jConnection;
import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static db.MySQLDBUtil.*;

@WebServlet("/search_user")
public class SearchUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // two search method
        // 1. search user by position 2. search user by champion
        String position = request.getParameter("position");
        String champion = request.getParameter("fav_champ");
        Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD);
        List<User> result = isNullOrEmpty(position) ? con.getUsersByChampion(champion) : con.getUsersByPosition(position);
        con.close();
        JSONArray array = new JSONArray();
        for (User user: result) {
            array.put(user.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
