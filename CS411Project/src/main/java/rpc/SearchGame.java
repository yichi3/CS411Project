package rpc;

import com.google.gson.Gson;
import db.MySQLConnection;
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
        JSONObject input = RpcHelper.readJSONObject(request);
        String blueChamp0 = input.getString("blue_champ0");
        String blueChamp1 = input.getString("blue_champ1");
        String blueChamp2 = input.getString("blue_champ2");
        String blueChamp3 = input.getString("blue_champ3");
        String blueChamp4 = input.getString("blue_champ4");
        String redChamp0 = input.getString("red_champ0");
        String redChamp1 = input.getString("red_champ1");
        String redChamp2 = input.getString("red_champ2");
        String redChamp3 = input.getString("red_champ3");
        String redChamp4 = input.getString("red_champ4");
        String[] blueTeam = {blueChamp0, blueChamp1, blueChamp2, blueChamp3, blueChamp4};
        String[] redTeam = {redChamp0, redChamp1, redChamp2, redChamp3, redChamp4};
        MySQLConnection con = new MySQLConnection();
        double blueTeamWinRate = con.getWinRate(blueTeam, redTeam);
        con.close();
        JSONObject obj = new JSONObject();
        obj.put("blue_team_win_rate", blueTeamWinRate);
        RpcHelper.writeJsonObject(response, obj);
    }
}

