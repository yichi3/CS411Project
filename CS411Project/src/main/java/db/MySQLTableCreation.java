package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLTableCreation {
    // Run this as Java application to reset the database.
    public static void main(String[] args) {
        try {
            // Step 1 Connect to MySQL.
            System.out.println("Connecting to " + MySQLDBUtil.URL);
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            Connection con = DriverManager.getConnection(MySQLDBUtil.URL);

            if (con == null) {
                return;
            }

            Statement statement = con.createStatement();
            String sql = "";

            // Drop tables in case they exist.
            sql = "DROP TABLE IF EXISTS MatchPP";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS MatchTP";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS PlayerPerformance";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS TeamPerformance";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS TeamPlayer";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS Player";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS Champion";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS SingleMatch";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS Team";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS UserTable";
            statement.executeUpdate(sql);

            // Create new tables
            sql = "CREATE TABLE UserTable ("
                    + "userName     VARCHAR(100) PRIMARY KEY,"
                    + "firstName    VARCHAR(100),"
                    + "lastName     VARCHAR(100),"
                    + "email        VARCHAR(100),"
                    + "phone        VARCHAR(100)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Team ("
                    + "teamName     VARCHAR(100) PRIMARY KEY"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Player ("
                    + "commonName   VARCHAR(100) PRIMARY KEY,"
                    + "teamName     VARCHAR(100),"
                    + "fullName     VARCHAR(100),"
                    + "position     VARCHAR(100),"
                    + "birthDate    VARCHAR(100),"
                    + "nationality  VARCHAR(100),"
                    + "imageUrl     VARCHAR(500),"
                    + "FOREIGN KEY (teamName) REFERENCES Team(teamName) ON DELETE CASCADE"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Champion ("
                    + "championName VARCHAR(100) PRIMARY KEY"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE TeamPlayer ("
                    + "teamName     VARCHAR(100) NOT NULL,"
                    + "commonName   VARCHAR(100) NOT NULL,"
                    + "PRIMARY KEY (teamName, commonName),"
                    + "FOREIGN KEY (teamName) REFERENCES Team(teamName) ON DELETE CASCADE,"
                    + "FOREIGN KEY (commonName) REFERENCES Player(commonName) ON DELETE CASCADE"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE SingleMatch ("
                    + "matchID          INT PRIMARY KEY,"
                    + "winningTeamName  VARCHAR(100),"
                    + "losingTeamName   VARCHAR(100),"
                    + "date             VARCHAR(100),"
                    + "duration         VARCHAR(100),"
                    + "FOREIGN KEY (winningTeamName) REFERENCES Team(teamName) ON DELETE SET NULL,"
                    + "FOREIGN KEY (losingTeamName)  REFERENCES Team(teamName) ON DELETE SET NULL"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE PlayerPerformance ("
                    + "playerPerformanceID  INT,"
                    + "matchID      INT,"
                    + "teamName     VARCHAR(100),"
                    + "commonName   VARCHAR(100),"
                    + "championName VARCHAR(100),"
                    + "kills        INT,"
                    + "deaths       INT,"
                    + "assists      INT,"
                    + "totalDamageDealt             INT,"
                    + "totalDamageDealtToChampion   INT,"
                    + "totalDamageTaken     INT,"
                    + "goldEarned           INT,"
                    + "totalMinionsKilled   INT,"
                    + "PRIMARY KEY (playerPerformanceID, matchID),"
                    + "FOREIGN KEY (matchID)      REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (teamName)     REFERENCES Team(teamName) ON DELETE SET NULL,"
                    + "FOREIGN KEY (commonName)   REFERENCES Player(commonName) ON DELETE SET NULL,"
                    + "FOREIGN KEY (championName) REFERENCES Champion(championName)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE TeamPerformance ("
                    + "teamPerformanceID    INT,"
                    + "matchID  INT,"
                    + "teamName VARCHAR(100),"
                    + "championBanned_1 VARCHAR(100) REFERENCES Champion(championName),"
                    + "championBanned_2 VARCHAR(100) REFERENCES Champion(championName),"
                    + "championBanned_3 VARCHAR(100) REFERENCES Champion(championName),"
                    + "championBanned_4 VARCHAR(100) REFERENCES Champion(championName),"
                    + "championBanned_5 VARCHAR(100) REFERENCES Champion(championName),"
                    + "totalTowerKills      INT,"
                    + "totalBaronKills      INT,"
                    + "totalDragonKills     INT,"
                    + "totalRiftHeraldKills INT,"
                    + "PRIMARY KEY (teamPerformanceID, matchID),"
                    + "FOREIGN KEY (matchID)  REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (teamName) REFERENCES Team(teamName) ON DELETE SET NULL"
                    + ")";
            statement.executeUpdate(sql);

//            sql = "CREATE TABLE MatchPP ("
//                    + "matchID INT,"
//                    + "playerPerformanceID INT,"
//                    + "PRIMARY KEY (matchID, playerPerformanceID),"
//                    + "FOREIGN KEY (matchID) REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
//                    + "FOREIGN KEY (playerPerformanceID) REFERENCES PlayerPerformance(playerPerformanceID) ON DELETE CASCADE"
//                    + ")";
//            statement.executeUpdate(sql);
//
//            sql = "CREATE TABLE MatchTP ("
//                    + "matchID INT,"
//                    + "teamPerformanceID INT,"
//                    + "PRIMARY KEY (matchID, teamPerformanceID),"
//                    + "FOREIGN KEY (matchID) REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
//                    + "FOREIGN KEY (teamPerformanceID) REFERENCES TeamPerformance(teamPerformanceID) ON DELETE CASCADE"
//                    + ")";
//            statement.executeUpdate(sql);

            con.close();
            System.out.println("Import done successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
