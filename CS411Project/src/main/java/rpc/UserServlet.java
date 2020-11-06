package rpc;

import entity.User;
import entity.User.UserBuilder;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        System.out.println(newUser.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
