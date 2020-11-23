package db;
import entity.Player;
import entity.Player.*;
import entity.User;
import entity.User.*;

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
        String sql = "SELECT teamID FROM Team WHERE name = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, team.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("Team " + team.getName() + " exists in database.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "INSERT INTO Team (teamID, name) VALUES (?, ?)";
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
        // before insert player info, need check if the player info already in the database
        String sql = "SELECT playerID FROM Player WHERE commonName = ?";
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
        sql = "INSERT INTO Player (playerID, teamID, fullName, commonName,"
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

    public int getPlayerCount() {
        if (con == null) {
            System.err.println("DB connection failed");
            return 0;
        }
        String sql = "SELECT COUNT(playerID) AS playerCount from Player";
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
        String sql = "SELECT COUNT(teamID) AS teamCount from Team";
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
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
        } catch (SQLException e) {
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
                if (firstName.equals("")) {
                    firstName = rs.getString("firstName");
                }
                if (lastName.equals("")) {
                    lastName = rs.getString("lastName");
                }
                if (email.equals("")) {
                    email = rs.getString("email");
                }
                if (phone.equals("")) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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

    // argument is two lists of champion name, each contains 5 names
    // return the win rate of 1st chamlist
    public double getWinRate(String[] chamList1, String[] chamList1) {

        if (con == null) {
            System.err.println("DB connection failed");
            return -1;
        }
        double result = -1;
        try {
            //query returns average value of every spec of champion (kills, deaths and assists is calculated as KDA)
            PreparedStatement st =
                    con.prepareStatement("SELECT championID, AVG((kills + assists) / deaths) AS KDA"
//                            + ", AVG(totalDamageDealt) AS totalDamageDealt, "
//                            + "AVG(totalDamageDealtToChampion) AS totalDamageDealtToChampion, "
//                            + "AVG(totalDamageTaken) AS totalDamageTaken, "
//                            + "AVG(towerKills) AS towerKills, "
//                            + "AVG(inhibitorKills) AS inhibitorKills, "
//                            + "AVG(goldEarned) AS goldEarned, "
//                            + "AVG(totalMinionsKilled) AS totalMinionsKilled, "
//                            + "AVG(neutralMinionsKIlled) AS neutralMinionsKIlled"
                            + "FROM PlayerPerformance"
                            + "WHERE championID IN (SELECT championID FROM Champion WHERE name IN (?, ?, ?, ?, ?)) "
                            + "GROUP BY championID");


            int i = 0;
            for (i = 0; i < chamList1.length; i++) {
                st.setString(i + 1, chamList1[i]);
            }

            ResultSet rs = st.executeQuery();

            double[] chamKDA1 = new double[chamList1];

            i = 0;
            while (rs.next()) {
                chamKDA1[i] = rs.getDouble("KDA");
                i++;
            }

            for (i = 0; i < chamList2.length; i++) {
                st.setString(i + 1 + chamList1.length, chamList2[i]);
            }

            rs = st.executeQuery();

            double[] chamKDA2 = new double[chamList2];

            i = 0;
            while (rs.next()) {
                chamKDA2[i] = rs.getDouble("KDA");
                i++;
            }

            result = calculateWinRate(chamKDA1, chamKDA2);

            st.executeUpdate();
            st.close();
            con.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // return the win rate of 1st team in percentage
    public double calculateWinRate(double[] list1, double[] list2) {

        double temp1 = 0, temp2 = 0;
        for(double e: list1) {
            temp1 += e;
        }

        for(double e: list2) {
            temp2 += e;
        }

        return (temp1 / (temp1 + temp2)) * 100 ;
    }

}
