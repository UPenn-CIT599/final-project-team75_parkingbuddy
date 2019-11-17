package Parking;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
 
/**
 *
 * @author sqlitetutorial.net took pointer code from this website
 */
public class DatabaseTest {
 
    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:src/sqlite/db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/sqlite/db/test.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS cars (\n"
                + "    license string NOT NULL,\n"
                + "    state string,\n"
                + "    datetime datetime,\n"
                + "    photoHash string NOT NULL \n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String license, String state, LocalDateTime datetime, String photoHash) {
        String sql = "INSERT INTO cars(license,state,datetime,photoHash) VALUES(?,?,?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, license);
            pstmt.setString(2, state);
            //datetime inserts in computer's local timezone
            pstmt.setTimestamp(3, Timestamp.valueOf(datetime));
            pstmt.setString(4, photoHash);
            pstmt.executeUpdate();
            System.out.println("Inserted data into table");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatabaseTest database = new DatabaseTest();
        createNewDatabase("test.db");
        createNewTable();
        database.insert("8XYA123", "PA", LocalDateTime.of(2017, 2, 13, 15, 56, 12), "hashvalues");
    }

    public DatabaseTest() {
    }
}