package rpc;

import db.MySQLConnection;
import entity.Player;
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
import java.util.Set;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject input = RpcHelper.readJSONObject(request);
        String userName = input.getString("user_name");
        String firstName = input.getString("first_name");
        String lastName = input.getString("last_name");
        String email = input.getString("email");
        String phone = input.getString("phone");
        User newUser = new UserBuilder()
                        .setUserName(userName)
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setPhone(phone)
                        .build();
        MySQLConnection con = new MySQLConnection();
        JSONObject obj = new JSONObject();
        if (con.addUser(newUser)) {
            obj.put("status", "OK");
        } else {
            obj.put("status", "User Already Exists");
        }
        con.close();
        RpcHelper.writeJsonObject(response, obj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        MySQLConnection con = new MySQLConnection();
        Set<User> userSet = con.getUserInfo(userName);
        con.close();
        JSONArray array = new JSONArray();
        for (User user: userSet) {
            array.put(user.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        MySQLConnection con = new MySQLConnection();
        JSONObject obj = new JSONObject();
        if (con.deleteUser(userName)) {
            obj.put("status", "OK");
        } else {
            obj.put("status", "User Delete Failed");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // update user information here
        String userName = request.getParameter("user_name");
        JSONObject input = RpcHelper.readJSONObject(request);
        String firstName = input.getString("first_name");
        String lastName = input.getString("last_name");
        String email = input.getString("email");
        String phone = input.getString("phone");
        MySQLConnection con = new MySQLConnection();
        JSONObject obj = new JSONObject();
        if (con.updateUser(userName, firstName, lastName, email, phone)) {
            obj.put("status", "OK");
        } else {
            obj.put("status", "User Update Failed");
        }
        con.close();
        RpcHelper.writeJsonObject(response, obj);
    }
}
