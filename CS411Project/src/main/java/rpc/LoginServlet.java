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

import static db.MySQLDBUtil.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject input = RpcHelper.readJSONObject(request);
        String gameID = input.getString("user_name");
        String password = input.getString("password");
        Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD);
        JSONObject obj = new JSONObject();
        if (con.loginCheck(gameID, password)) {
            obj.put("status", "OK");
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
//            obj.put("status", "User Does Not Exist or Wrong Password");
        }
        con.close();
        RpcHelper.writeJsonObject(response, obj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String gameID = request.getParameter("user_name");
//        String password = request.getParameter("password");
//        JSONObject obj = new JSONObject();
//        Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD);
//        if (con.loginCheck(gameID, password)) {
//            obj.put("status", "OK");
//        } else {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
////            obj.put("status", "User Does Not Exist or Wrong Password");
//        }
//        con.close();
//        RpcHelper.writeJsonObject(response, obj);
    }
}
