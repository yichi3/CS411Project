package rpc;

import com.google.gson.Gson;
import entity.Game;
import entity.Game.GameBuilder;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/search_game")
public class SearchGame extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        Game game = new GameBuilder().setRedTeamName("DWG")
                                        .setBlueTeamName("SN")
                                        .setRedTeamKill(10)
                                        .setBlueTeamKill(1000).build();
        JSONObject obj = game.toJSONObject();
//        PrintWriter out = response.getWriter();
//        String test = new Gson().toJson(game);
//        out.print(test);
        RpcHelper.writeJsonObject(response, obj);
    }
}

