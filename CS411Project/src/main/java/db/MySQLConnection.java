package db;
import entity.Player;
import entity.Player.*;
import entity.Team;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;



public class MySQLConnection {
    private Connection con;

    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            con = DriverManager.getConnection(MySQLDBUtil.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setTeamInfo(Team team) {
        if (con == null) {
            System.err.println("DB connection failed");
            return;
        }
        String sql = "INSERT INTO Team (teamID, name) VALUES (?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, Integer.toString(team.getTeamID()));
            statement.setString(2, team.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerInfo(Player player, int teamID) {
        if (con == null) {
            System.err.println("DB connection failed");
            return;
        }
        String sql = "INSERT INTO Player (playerID, teamID, fullName, commonName,"
                    + "position, birthDate, nationality, imageUrl) VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, Integer.toString(player.getPlayerID()));
            statement.setString(2, Integer.toString(teamID));
            statement.setString(3, player.getFullName());
            statement.setString(4, player.getCommonName());
            statement.setString(5, player.getPosition());
            statement.setString(6, player.getBirthDate());
            statement.setString(7, player.getNationality());
            statement.setString(8, player.getImageUrl());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Set<Player> getPlayerInfo(String commonName) {

        if (con == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }


        Set<Player> result = new HashSet<>();

        try {

            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM Player NATURAL JOIN Team WHERE commonName = ? ");
            st.setString(1, commonName);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                PlayerBuilder builder = new PlayerBuilder();

                String teamName = rs.getString("name");
                builder.setTeamName(teamName);

                builder.setCommonName(commonName);

                String firstName = rs.getString("fullName");
                builder.setFullName(firstName);

                String position = rs.getString("position");
                builder.setPosition(position);

                String birthDate = rs.getString("birthDate");
                builder.setBirthDate(birthDate);

                String nationality = rs.getString("nationality");
                builder.setNationality(nationality);

                String imageUrl = rs.getString("imageUrl");
                builder.setImageUrl(imageUrl);

                result.add(builder.build());

            }

//            st.executeUpdate();
            st.close();
            con.close();


        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

//    public void insertGame(String redTeamName,
//                           String blueTeamName,
//                           int redTeamKill,
//                           int blueTeamKill) {
//
//        if (con == null) {
//            System.err.println("DB connection failed");
//            return;
//        }
//        try {
//
//            PreparedStatement st = con.prepareStatement("insert into Game values(?, ?, ?, ?)");
//
//            st.setString(1, redTeamName);
//            st.setString(2, blueTeamName);
//            st.setInt(3, redTeamKill);
//            st.setInt(4, blueTeamKill);
//
//            st.executeUpdate();
//            st.close();
//            con.close();
//
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void updateGame(String redTeamName,
//                           String blueTeamName,
//                           int redTeamKill,
//                           int blueTeamKill) {
//
//        if (con == null) {
//            System.err.println("DB connection failed");
//            return;
//        }
//        try {
//            PreparedStatement st = con.prepareStatement("UPDATE Game SET  WHERE");
//
//            st.setString(1, redTeamName);
//            st.setString(2, blueTeamName);
//            st.setInt(3, redTeamKill);
//            st.setInt(4, blueTeamKill);
//            st.executeUpdate();
//            st.close();
//            con.close();
//
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void deleteGame(String redTeamName,
//                           String blueTeamName,
//                           int redTeamKill,
//                           int blueTeamKill) {
//
//        if (con == null) {
//            System.err.println("DB connection failed");
//            return;
//        }
//
//        try {
//            PreparedStatement st = con.prepareStatement("DELETE FROM Game WHERE ");
//
//            st.setString(1, redTeamName);
//            st.setString(2, blueTeamName);
//            st.setInt(3, redTeamKill);
//            st.setInt(4, blueTeamKill);
//
//            st.executeUpdate();
//            st.close();
//            con.close();
//
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
}