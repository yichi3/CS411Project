package rpc;

import db.MySQLConnection;
import entity.Player;
import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/player")
public class SearchPlayer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // assume that frontend will give a gameID of a player
        // we need to give the full gamer information back to frontend
        String commonName = request.getParameter("game_id");

        MySQLConnection con = new MySQLConnection();
        Set<Player> playerSet = con.getPlayerInfo(commonName);
        con.close();
        JSONArray array = new JSONArray();
        for (Player player: playerSet) {
            array.put(player.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }
}
