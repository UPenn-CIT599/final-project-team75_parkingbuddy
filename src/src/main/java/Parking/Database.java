package Parking;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
 
/**
 *
 * @author sqlitetutorial.net took pointer code from this website
 */
public class Database {
 
    /**
     * Create a new database
     */
    public void createNewDatabase() {

        try (
            Connection conn = this.connect();)
            {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * create ParkingInstances table
     */

    public void createNewTable() {
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS parkingInstances (\n"
                + "    license string NOT NULL,\n"
                + "    state string,\n"
                + "    datetime datetime,\n"
                + "    photoHash string NOT NULL UNIQUE \n"
                + ");";
        
        try (//Connection conn = DriverManager.getConnection(url);
            Connection conn = this.connect();
            Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connection to a database
     * @return
     */
    

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/sqlite/db/parkingBuddy.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

 

     /**
     * insertParkingInstance inserts each new parkingInstance into the table
     * @param parkingInstance
     */
    public void insertParkingInstance(ParkingInstance parkingInstance) {
        String sql = "INSERT OR IGNORE INTO parkingInstances(license,state,datetime,photoHash) VALUES(?,?,?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parkingInstance.getCar().getLicense());
            pstmt.setString(2, parkingInstance.getCar().getState());
            //datetime inserts in computer's local timezone
            pstmt.setTimestamp(3, Timestamp.valueOf(parkingInstance.getDate()));
            pstmt.setString(4, parkingInstance.getPhotoHash());
            pstmt.executeUpdate();
            System.out.println("inserted into DB");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Get parking instances filtered by user input dates
     * @param startDate
     * @param endDate
     */

    public void getParkingInstancesbyDate(LocalDate startDate, LocalDate endDate){
        long unixStart = startDate.toEpochSecond(LocalTime.parse("00:00:00"), ZoneOffset.of("Z"));
        long unixEnd = endDate.toEpochSecond(LocalTime.parse("00:00:00"), ZoneOffset.of("Z"));
        
        String sql = "SELECT license, state, datetime FROM parkingInstances \n"
                    +   
                    "WHERE datetime BETWEEN \n"
                    +
                    unixStart
                    +
                    "000"
                    +
                    " AND "
                    +
                    unixEnd
                    +
                    "000";
        
        try(Connection conn = this.connect();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sql)){
                System.out.println("Selecting data");
                System.out.println(unixEnd);
                while (results.next()){
                    
                    System.out.println(results.getString("license") + "\t" +
                                        results.getString("state") + "\t" +
                                        results.getDate("datetime")) 
                                        ;
                }
                
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database database = new Database();
        database.createNewDatabase();
        database.createNewTable();
        Car carTest = new Car("7XYA124", "PA");
        ParkingInstance parkingInstance = new ParkingInstance(LocalDateTime.of(2018, 8, 13, 15, 56, 12), carTest, "hashvalues");
        // ParkingInstance parkingInstance = new ParkingInstance("2019-10-20 20:08:41", carTest, "hashvalues");
        database.insertParkingInstance(parkingInstance);
        database.getParkingInstancesbyDate(LocalDate.of(2017,2,11), LocalDate.of(2019,6,11));
    }

    public Database() {
    }
}