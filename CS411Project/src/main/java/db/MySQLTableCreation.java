package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
//a line of comment to test if i can push. delete later.
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

            // Step 2 Drop tables in case they exist.
            Statement statement = conn.createStatement();
            String sql = "DROP TABLE IF EXISTS Test";
            statement.executeUpdate(sql);

            // Step 3 Create new tables
            sql = "CREATE TABLE Test ("
                    + "test_id INT NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255),"
                    + "PRIMARY KEY (test_id)"
                    + ")";
            statement.executeUpdate(sql);

            // Step 4: insert fake user for test
            sql = "INSERT INTO Test VALUES(1, 'Yichi', 'Zhang')";
            statement.executeUpdate(sql);

            sql = "INSERT INTO Test VALUES(2, '123', '456')";
            statement.executeUpdate(sql);

            conn.close();
            System.out.println("Import done successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
