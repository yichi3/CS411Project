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
            Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);

            if (conn == null) {
                return;
            }

            Statement statement = conn.createStatement();
            String sql = "";

            // Drop tables in case they exist.
            sql = "DROP TABLE IF EXISTS Team";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS Player";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS Champion";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS TeamPlayer";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS SingleMatch";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS PlayerPerformence";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS TeamPerformence";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS MatchPP";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS MatchTP";
            statement.executeUpdate(sql);

            // Create new tables
            sql = "CREATE TABLE Team ("
                    + "teamID   INT PRIMARY KEY,"
                    + "name     VARCHAR(100)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Player ("
                    + "playerID     INT PRIMARY KEY,"
                    + "teamID       INT,"
                    + "firstName    VARCHAR(100),"
                    + "lastName     VARCHAR(100),"
                    + "commonName   VARCHAR(100),"
                    + "gender       VARCHAR(100),"
                    + "position     VARCHAR(100),"
                    + "birthDate    VARCHAR(100),"
                    + "nationality  VARCHAR(100)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Champion ("
                    + "championID   INT PRIMARY KEY,"
                    + "name         VARCHAR(100)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE TeamPlayer ("
                    + "teamID   INT NOT NULL,"
                    + "playerID INT NOT NULL,"
                    + "PRIMARY KEY (teamID, playerID),"
                    + "FOREIGN KEY (teamID) REFERENCES Team(teamID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (playerID) REFERENCES Player(playerID) ON DELETE CASCADE"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE SingleMatch ("
                    + "matchID  INT PRIMARY KEY,"
                    + "winningTeamID    INT,"
                    + "losingTeamID     INT,"
                    + "date     VARCHAR(100),"
                    + "duration VARCHAR(100),"
                    + "FOREIGN KEY (winningTeamID) REFERENCES Team(teamID) ON DELETE SET NULL,"
                    + "FOREIGN KEY (losingTeamID) REFERENCES Team(teamID) ON DELETE SET NULL"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE PlayerPerformance ("
                    + "playerPerformanceID  INT,"
                    + "matchID      INT,"
                    + "teamID       INT,"
                    + "playerID     INT,"
                    + "championID   INT,"
                    + "kills        INT,"
                    + "deaths       INT,"
                    + "assists      INT,"
                    + "totalDamageDealt             INT,"
                    + "totalDamageDealtToChampion   INT,"
                    + "totalDamageTaken     INT,"
                    + "towerKills           INT,"
                    + "inhibitorKills       INT,"
                    + "goldEarned           INT,"
                    + "totalMinionsKilled   INT,"
                    + "neutralMinionsKIlled INT,"
                    + "PRIMARY KEY (playerPerformanceID, matchID),"
                    + "FOREIGN KEY (matchID) REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (teamID) REFERENCES Team(teamID) ON DELETE SET NULL,"
                    + "FOREIGN KEY (playerID) REFERENCES Player(playerID) ON DELETE SET NULL,"
                    + "FOREIGN KEY (championID) REFERENCES Champion(championID)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE TeamPerformance ("
                    + "teamPerformanceID    INT PRIMARY KEY,"
                    + "matchID  INT,"
                    + "teamID   INT,"
                    + "championBanned_1 INT REFERENCES Champion(championID),"
                    + "championBanned_2 INT REFERENCES Champion(championID),"
                    + "championBanned_3 INT REFERENCES Champion(championID),"
                    + "championBanned_4 INT REFERENCES Champion(championID),"
                    + "championBanned_5 INT REFERENCES Champion(championID),"
                    + "firstBlood BOOLEAN,"
                    + "firstTower BOOLEAN,"
                    + "totalTowerKills      INT,"
                    + "totalBaronKills      INT,"
                    + "totalDragonKills     INT,"
                    + "totalRiftHeraldKills INT,"
                    + "FOREIGN KEY (matchID) REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (teamID) REFERENCES Team(teamID) ON DELETE SET NULL"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE MatchPP ("
                    + "matchID INT,"
                    + "playerPerformanceID INT,"
                    + "PRIMARY KEY (matchID, playerPerformanceID),"
                    + "FOREIGN KEY (matchID) REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (playerPerformanceID) REFERENCES PlayerPerformance(playerPerformanceID) ON DELETE CASCADE"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE MatchTP ("
                    + "matchID INT,"
                    + "teamPerformanceID INT,"
                    + "PRIMARY KEY (matchID, teamPerformanceID),"
                    + "FOREIGN KEY (matchID) REFERENCES SingleMatch(matchID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (teamPerformanceID) REFERENCES TeamPerformance(teamPerformanceID) ON DELETE CASCADE"
                    + ")";
            statement.executeUpdate(sql);

            conn.close();
            System.out.println("Import done successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
