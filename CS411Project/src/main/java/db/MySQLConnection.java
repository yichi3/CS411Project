package db;
import entity.*;
import entity.Player.*;
import entity.User.UserBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        String sql = "SELECT teamName FROM Team WHERE teamName = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, team.getTeamName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("Team " + team.getTeamName() + " exists in database.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO Team (teamName) VALUES (?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, team.getTeamName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerInfo(Player player, String teamName) {
        if (con == null) {
            System.err.println("DB connection failed");
            return;
        }
        // before insert player info, need check if the player info already in the database
        String sql = "SELECT commonName FROM Player WHERE commonName = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, player.getCommonName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("Player " + player.getCommonName() + " exists in database.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO Player (commonName, teamName, fullName,"
                + "position, birthDate, nationality, imageUrl) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, player.getCommonName());
            statement.setString(2, teamName);
            statement.setString(3, player.getFullName());
            statement.setString(4, player.getPosition());
            statement.setString(5, player.getBirthDate());
            statement.setString(6, player.getNationality());
            statement.setString(7, player.getImageUrl());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO TeamPlayer(teamName, commonName) VALUES (?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, teamName);
            statement.setString(2, player.getCommonName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerInfoHard(Player player, String teamName) {
        if (con == null) {
            System.err.println("DB connection failed");
            return;
        }
        String sql = "DELETE FROM TeamPlayer WHERE commonName = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, player.getCommonName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO TeamPlayer(teamName, commonName) VALUES (?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, teamName);
            statement.setString(2, player.getCommonName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "SELECT commonName FROM Player WHERE commonName = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, player.getCommonName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                sql = "UPDATE Player SET teamName = ? WHERE commonName = ?";
                statement = con.prepareStatement(sql);
                statement.setString(1, teamName);
                statement.setString(2, player.getCommonName());
                statement.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO Player (commonName, teamName, fullName,"
                + "position, birthDate, nationality, imageUrl) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, player.getCommonName());
            statement.setString(2, teamName);
            statement.setString(3, player.getFullName());
            statement.setString(4, player.getPosition());
            statement.setString(5, player.getBirthDate());
            statement.setString(6, player.getNationality());
            statement.setString(7, player.getImageUrl());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setChampionInfo(Champion champion) {
        if (con == null) {
            System.err.println("DB connection failed");
            return;
        }
        String sql = "SELECT championName FROM Champion WHERE championName = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, champion.getChampionName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
//                System.out.println("Champion " + champion.getChampionName() + " exists in database.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO Champion (championName) VALUES (?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, champion.getChampionName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGameInfo(Game game, int matchID) {
        if (con == null) {
            System.err.println("DB connection failed");
            return;
        }

        String sql  = "INSERT INTO SingleMatch"
                    + "(matchID, winningTeamName, losingTeamName, date, duration)"
                    + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, Integer.toString(matchID));
            statement.setString(2, game.winningTeamName);
            statement.setString(3, game.losingTeamName);
            statement.setString(4, game.date);
            statement.setString(5, game.duration);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO TeamPerformance"
                + "(teamPerformanceID, matchID, teamName,"
                + "championBanned_1, championBanned_2, championBanned_3, championBanned_4, championBanned_5,"
                + "totalTowerKills, totalBaronKills, totalDragonKills, totalRiftHeraldKills)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, Integer.toString(1));
            statement.setString(2, Integer.toString(matchID));
            statement.setString(3, game.redTeamName);
            statement.setString(4, game.redTeamBans[0]);
            statement.setString(5, game.redTeamBans[1]);
            statement.setString(6, game.redTeamBans[2]);
            statement.setString(7, game.redTeamBans[3]);
            statement.setString(8, game.redTeamBans[4]);
            statement.setString(9, Integer.toString(game.redTeamTotalTowerKills));
            statement.setString(10, Integer.toString(game.redTeamTotalBaronKills));
            statement.setString(11, Integer.toString(game.redTeamTotalDragonKills));
            statement.setString(12, Integer.toString(game.redTeamTotalRiftHeraldKills));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, Integer.toString(2));
            statement.setString(2, Integer.toString(matchID));
            statement.setString(3, game.blueTeamName);
            statement.setString(4, game.blueTeamBans[0]);
            statement.setString(5, game.blueTeamBans[1]);
            statement.setString(6, game.blueTeamBans[2]);
            statement.setString(7, game.blueTeamBans[3]);
            statement.setString(8, game.blueTeamBans[4]);
            statement.setString(9, Integer.toString(game.blueTeamTotalTowerKills));
            statement.setString(10, Integer.toString(game.blueTeamTotalBaronKills));
            statement.setString(11, Integer.toString(game.blueTeamTotalDragonKills));
            statement.setString(12, Integer.toString(game.blueTeamTotalRiftHeraldKills));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO PlayerPerformance"
                + "(playerPerformanceID, matchID, teamName, commonName,"
                + "championName, kills, deaths, assists, totalDamageDealt,"
                + "totalDamageDealtToChampion, totalDamageTaken,"
                + "goldEarned, totalMinionsKilled)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        for (int i = 0; i < 5; i++) {
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, Integer.toString(i));
                statement.setString(2, Integer.toString(matchID));
                statement.setString(3, game.redTeamName);
                statement.setString(4, game.redTeamPlayerIds[i]);
                statement.setString(5, game.redTeamPicks[i]);
                statement.setString(6, Integer.toString(game.redTeamKills[i]));
                statement.setString(7, Integer.toString(game.redTeamDeaths[i]));
                statement.setString(8, Integer.toString(game.redTeamAssists[i]));
                statement.setString(9, Integer.toString(game.redTeamTotalDamageDealt[i]));
                statement.setString(10, Integer.toString(game.redTeamTotalDamageToChampion[i]));
                statement.setString(11, Integer.toString(game.redTeamTotalDamageTaken[i]));
                statement.setString(12, Integer.toString(game.redTeamGoldEarned[i]));
                statement.setString(13, Integer.toString(game.redTeamTotalMinionsKilled[i]));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 5; i++) {
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, Integer.toString(5+i));
                statement.setString(2, Integer.toString(matchID));
                statement.setString(3, game.blueTeamName);
                statement.setString(4, game.blueTeamPlayerIds[i]);
                statement.setString(5, game.blueTeamPicks[i]);
                statement.setString(6, Integer.toString(game.blueTeamKills[i]));
                statement.setString(7, Integer.toString(game.blueTeamDeaths[i]));
                statement.setString(8, Integer.toString(game.blueTeamAssists[i]));
                statement.setString(9, Integer.toString(game.blueTeamTotalDamageDealt[i]));
                statement.setString(10, Integer.toString(game.blueTeamTotalDamageToChampion[i]));
                statement.setString(11, Integer.toString(game.blueTeamTotalDamageTaken[i]));
                statement.setString(12, Integer.toString(game.blueTeamGoldEarned[i]));
                statement.setString(13, Integer.toString(game.blueTeamTotalMinionsKilled[i]));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlayerCount() {
        if (con == null) {
            System.err.println("DB connection failed");
            return 0;
        }
        String sql = "SELECT COUNT(commonName) AS playerCount from Player";
        int result = 0;
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("playerCount");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTeamCount() {
        if (con == null) {
            System.err.println("DB connection failed");
            return 0;
        }
        String sql = "SELECT COUNT(teamName) AS teamCount from Team";
        int result = 0;
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getInt("teamCount");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
                String teamName = rs.getString("teamName");
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
            st.close();
//            con.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isGameExist(int matchID) {
        if (con == null) {
            System.err.println("DB connection failed");
            return false;
        }
        String sql = "SELECT * FROM SingleMatch WHERE matchID = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, Integer.toString(matchID));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addUser(User user) {
        if (con == null) {
            System.err.println("DB connection failed");
            return false;
        }
        String sql = "insert ignore into UserTable values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getUserName());
            st.setString(2, user.getFirstName());
            st.setString(3, user.getLastName());
            st.setString(4, user.getEmail());
            st.setString(5, user.getPhone());
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateUser(String userName,
                           String firstName,
                           String lastName,
                           String email,
                           String phone) {
        if (con == null) {
            System.err.println("DB connection failed");
            return false;
        }
        try {
            PreparedStatement st1 = con.prepareStatement("SELECT * FROM UserTable WHERE userName = ? ");
            st1.setString(1, userName);
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                if(firstName.equals("")) {
                    firstName = rs.getString("firstName");
                }
                if(lastName.equals("")) {
                    lastName = rs.getString("lastName");
                }
                if(email.equals("")) {
                    email = rs.getString("email");
                }
                if(phone.equals("")) {
                    phone = rs.getString("phone");
                }
            }
            PreparedStatement st = con.prepareStatement("UPDATE UserTable SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE userName = ? ");
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, userName);
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteUser(String userName) {
        if (con == null) {
            System.err.println("DB connection failed");
            return false;
        }
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM UserTable WHERE userName = ? ");
            st.setString(1, userName);
            return st.executeUpdate() == 1;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<User> getUserInfo(String userName) {
        if (con == null) {
            System.err.println("DB connection failed");
            return new HashSet<>();
        }
        Set<User> result = new HashSet<>();
        try {
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM UserTable WHERE userName = ? ");
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                UserBuilder builder = new UserBuilder();
                builder.setUserName(userName);
                String firstName = rs.getString("firstName");
                builder.setFirstName(firstName);
                String lastName = rs.getString("lastName");
                builder.setLastName(lastName);
                String email = rs.getString("email");
                builder.setEmail(email);
                String phone = rs.getString("phone");
                builder.setPhone(phone);
                result.add(builder.build());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> playerCountByPosition(String teamName) {
        Map<String, Integer> result = new HashMap<>();
        if (con == null) {
            System.err.println("DB connection failed");
            return result;
        }
        String sql = "SELECT position, COUNT(playerID) AS count FROM Player NATURAL JOIN Team WHERE name = ? GROUP BY position";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, teamName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("position"), rs.getInt("count"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> playerCountByNationality() {
        Map<String, Integer> result = new HashMap<>();
        if (con == null) {
            System.err.println("DB connection failed");
            return result;
        }
        String sql = "SELECT position, nationality, COUNT(playerID) AS count FROM Player NATURAL JOIN Team WHERE birthDate LIKE ? GROUP BY position, nationality";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "%200%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("position") + ", " + rs.getString("nationality"), rs.getInt("count"));
            }
            statement.close();
        } catch (SQLException e) {
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