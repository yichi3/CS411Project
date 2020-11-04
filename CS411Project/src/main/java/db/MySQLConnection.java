package db;
import entity.Player;
import entity.Player.*;


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

                String firstName = rs.getString("firstName");
                builder.setFirstName(firstName);

                String lastName = rs.getString("lastName");
                builder.setLastName(lastName);

                String gender = rs.getString("gender");
                builder.setGender(gender);

                String position = rs.getString("position");
                builder.setPosition(position);

                String birthDate = rs.getString("birthDate");
                builder.setBirthDate(birthDate);

                String nationality = rs.getString("nationality");
                builder.setNationality(nationality);

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