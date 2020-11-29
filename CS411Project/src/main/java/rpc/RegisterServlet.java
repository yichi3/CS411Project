package rpc;

import db.Neo4jConnection;
import entity.User;
import entity.User.UserBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static db.MySQLDBUtil.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject input = RpcHelper.readJSONObject(request);
        String gameID = input.getString("user_name");
        String email = input.getString("email");
        String phone = input.getString("phone");
        String password = input.getString("password");
        String position = input.getString("position");
        String champion = input.getString("fav_champ");
        User newUser = new UserBuilder()
                        .setGameID(gameID)
                        .setEmail(email)
                        .setPhone(phone)
                        .setPassword(password)
                        .setFavoritePosition(position)
                        .setFavoriteChampion(champion)
                        .build();
        Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD);
        JSONObject obj = new JSONObject();
        if (con.addUser(newUser)) {
            con.userBindPosition(newUser);
            con.userBindChampion(newUser);
            obj.put("status", "OK");
        } else {
            obj.put("status", "User Already Exists");
        }
        con.close();
        RpcHelper.writeJsonObject(response, obj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameID = request.getParameter("user_name");
        Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD);
        User user = con.getUserInfo(gameID);
        con.close();
        JSONArray array = new JSONArray();
        array.put(user.toJSONObject());
        RpcHelper.writeJsonArray(response, array);
    }

//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String userName = request.getParameter("user_name");
//        MySQLConnection con = new MySQLConnection();
//        JSONObject obj = new JSONObject();
//        if (con.deleteUser(userName)) {
//            obj.put("status", "OK");
//        } else {
//            obj.put("status", "User Delete Failed");
//        }
//    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // update user information here
        String gameID = request.getParameter("user_name");
        JSONObject input = RpcHelper.readJSONObject(request);
        String email = input.getString("email");
        String phone = input.getString("phone");
        String position = input.getString("position");
        String champion = input.getString("fav_champ");
        Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD);
        JSONObject obj = new JSONObject();
        if (con.updateUser(gameID, email, phone, position, champion)) {
            obj.put("status", "OK");
        } else {
            obj.put("status", "User Update Failed");
        }
        con.close();
        RpcHelper.writeJsonObject(response, obj);
    }
}
