package Parking;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sqlitetutorial.net took pointer code from this website
 */
public class Database {

	final DateTimeFormatter formatter =
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	Connection conn;

	/**
	 * Initialize the database 
	 */
	public Database() {
		conn = connect("jdbc:sqlite:sqlite/db/parkingBuddy.db");
		createNewDatabase();
		createTable("ParkingInstances");
	}

	/**
	 * Set up the connection to database
	 * 
	 * @return
	 */
	private Connection connect(String url) {
		// SQLite connection string
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Create a new database
	 */
	public void createNewDatabase() {
		try {
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println("The driver name is " + meta.getDriverName());
			System.out.println("A new database has been created.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Create ParkingInstances table
	 */
	public void createTable(String tableName) {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
				+ "    license TEXT NOT NULL,\n" + "    state TEXT,\n"
				+ "    datetime TEXT,\n"
				+ "    photoHash TEXT NOT NULL PRIMARY KEY, \n"
				+ "    UNIQUE(photoHash) \n" + ");";

		try {
			Statement stmt = conn.createStatement();
			// create a new table
			stmt.execute(sql);
			System.out.println(tableName + " table created");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Insert each new parkingInstance into the table
	 * 
	 * @param parkingInstance
	 */
	public void insertParkingInstance(ParkingInstance parkingInstance) {
		String sql = "INSERT OR IGNORE INTO ParkingInstances \n"
				+ "(license,state,datetime,photoHash) VALUES(?,?,?,?)";


		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parkingInstance.getCar().getLicense());
			pstmt.setString(2, parkingInstance.getCar().getState());
			
			// datetime inserts in computer's local timezone
			pstmt.setString(3, parkingInstance.getDate().format(formatter));
			pstmt.setString(4, parkingInstance.getPhotoHash());
			pstmt.executeUpdate();
			System.out.println("inserted into DB");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get parking instances filtered by user input dates.
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public void getParkingInstancesbyDate(LocalDate startDate,
			LocalDate endDate) {

		String sql = "SELECT state, license, datetime FROM parkingInstances\n"
				+ "WHERE TIME(datetime) > TIME('20:00:00') OR TIME(datetime) < TIME('06:00:00')";

		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			System.out.println("Selecting data");
			while (results.next()) {
				System.out.println(results.getString("state") + "\t"
						+ results.getString("license") + "\t"
						+ results.getString("datetime"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get parking instances filtered by user input dates and return an
	 * ArrayList of the aggregated parking instances for each car.
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public ArrayList<ParkingAggregate> getAggregatedParkingInstances(LocalDate startDate,
			LocalDate endDate) {
		String sql = "SELECT state, license, count(*) as count from\n" + "(\n"
				+ "  SELECT * from\n" + "  (\n"
				+ "	SELECT state, license, (DATE(datetime)) as date  FROM parkingInstances\n"
				+ "	WHERE time(datetime) > time('20:00:00')\n"
				+ "	GROUP BY state, license, date\n" + "	UNION ALL\n"
				+ "	SELECT state, license, DATE(JULIANDAY(DATE(datetime)) -1) as date FROM parkingInstances\n"
				+ "	WHERE time(datetime) < time('06:00:00')\n"
				+ "	GROUP BY state, license, date\n" + "	)\n"
				+ "  GROUP BY state, license, date\n" + ")\n"
				+ "GROUP BY state, license\n";
		
		ArrayList<ParkingAggregate> parkingResults = new ArrayList<ParkingAggregate>();
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			
			// ArrayList<ParkingAggregator> parkingResults = new ArrayList<ParkingAggregator>();
			while (results.next()) {
				String license = results.getString("license");
				String state = results.getString("state");
				int overnightCount = results.getInt("count");

				ParkingAggregate aggregate = new ParkingAggregate(license, state, overnightCount);
				aggregate.setParkingInstance(
					new ArrayList<>(Arrays.asList(
						new ParkingInstance(LocalDateTime.now(), new Car("7XYA125", "PA"), "test"),
						new ParkingInstance(LocalDateTime.now(), new Car("7XYA125", "PA"), "test")
						)));
				parkingResults.add(aggregate);
				System.out.println(aggregate.getLicense());
				// System.out.println(results.getString("state") + "\t"
				// 		+ results.getString("license") + "\t"
				// 		+ results.getInt("count"));
			}
			return parkingResults;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return parkingResults;
	}


	public static void main(String[] args) {
		Database database = new Database();
		// database.createNewDatabase();
		// database.createTable("ParkingInstances");
		// Car carTest = new Car("7XYA125", "PA");
		// ParkingInstance parkingInstance =
		// new ParkingInstance(LocalDateTime.of(2018, 9, 14, 03, 56, 12),
		// carTest, "hashvalues5");
		// database.insertParkingInstance(parkingInstance, "ParkingInstances");
		// database.getParkingInstancesbyDate(LocalDate.of(2017,2,11),
		// LocalDate.of(2019,6,11));
		database.getParkingInstancesbyDate(LocalDate.of(2010, 2, 11),
				LocalDate.of(2019, 6, 11));
		database.getAggregatedParkingInstances(LocalDate.of(2010, 2, 11),
				LocalDate.of(2019, 6, 11));
	}

}
