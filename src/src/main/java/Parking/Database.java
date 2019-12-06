package Parking;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

/**
 * This class models a database for parking instances and its details for the
 * user to store and retrieve data. This class implements SQLite to streamline
 * data management and sorting.
 */
public class Database {
    // date and time to follow this format
	final static DateTimeFormatter dateTimeFormatter =
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	Connection conn;

	/**
	 * Constructor for the Database class
	 * 
	 * @throws ParkingException
	 */
	public Database() throws ParkingException {
		try {
			File file = new File("parkingBuddy.db");
			if (!file.exists()) {
				file.createNewFile();
			}
			init(Paths.get("parkingBuddy.db"));
		} catch (IOException e) {
			throw new ParkingException("Unable to load database: " + e.getMessage());
		}
	}
	
	/**
	 * Constructor for the database class with the path parameter given
	 * 
	 * @param path (Path)
	 * @throws ParkingException
	 */
	public Database(Path path) throws ParkingException {
		init(path);
	}
	
	/**
	 * This method sets up using the JDBC driver for SQLite and calls other
	 * to create new database and parking instance table.
	 * 
	 * @param path (Path)
	 * @throws ParkingException
	 */
	private void init(Path path) throws ParkingException {
		conn = connect("jdbc:sqlite:" + path.toString());
		createNewDatabase();
		createParkingInstanceTable();
	}

	/**
	 * This method sets up connection to database
	 * 
	 * @param url (String)
	 * @return Connection
	 * @throws ParkingException
	 */
	private Connection connect(String url) throws ParkingException {
		// SQLite connection string
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new ParkingException("Unable to connect to database: " + e.getMessage());
		}
		return conn;
	}

	/**
	 * This method creates a new database
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
	 * This method creates ParkingInstances table
	 */
	public void createParkingInstanceTable() {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS ParkingInstances (\n" // break
				+ "    state TEXT NOT NULL,\n" // break
				+ "    license TEXT NOT NULL,\n" // break
				+ "    datetime TEXT NOT NULL,\n" // break
				+ "    photoHash TEXT NOT NULL PRIMARY KEY, \n" // break
				+ "    photoPath TEXT NOT NULL, \n"// break
				+ "    photoImage BLOB NOT NULL, \n" // break
				+ "    UNIQUE(photoHash) \n" // break
				+ ");";

		try {
			Statement stmt = conn.createStatement();
			// create a new table
			stmt.execute(sql);
			System.out.println("Parking Instance table created");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method inserts each new parkingInstance into the table
	 * 
	 * @param parkingInstance (ParkingInstance)
	 */
	public void insertParkingInstance(ParkingInstance parkingInstance) throws ParkingException {
		
	    // parking instance with empty fields cannot be added
	    if (parkingInstance.getCar().getState().isEmpty()
				|| parkingInstance.getCar().getLicense().isEmpty()
				|| parkingInstance.getPhoto().getMd5Hash().isEmpty()) {
			throw new ParkingException(
					"Unable to insert parkingInstance to DB; parkingInstance contains empty field(s)");
		}
	    
	    // SQL command string
		String sql = "INSERT OR IGNORE INTO ParkingInstances \n"
				+ "(state,license,datetime,photoHash,photoPath,photoImage) VALUES(?,?,?,?,?,?)";
		
		// setting values for the parking instance
		try {
			PreparedStatement prepStatement = conn.prepareStatement(sql);
			prepStatement.setString(1, parkingInstance.getCar().getState());
			prepStatement.setString(2, parkingInstance.getCar().getLicense());

			// date time inserts in computer's local timezone
			prepStatement.setString(3, parkingInstance.getDateTime().format(dateTimeFormatter));
			prepStatement.setString(4, parkingInstance.getPhotoMd5Hash());
			prepStatement.setString(5, parkingInstance.getPhoto().getPath());
			prepStatement.setBytes(6, parkingInstance.getPhoto().toJpegBytes());
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get parking instances filtered by user input start and end dates.
	 * 
	 * @param car (Car)
	 * @param startDate (LocalDate)
	 * @param endDate (LocalDate)
	 * @return ArrayList of parking instances
	 */
	public ArrayList<ParkingInstance> getParkingInstancesbyDate(Car car, LocalDate startDate,
			LocalDate endDate) {
	  
	    // ArrayList to store parking instances
		ArrayList<ParkingInstance> parkings = new ArrayList<ParkingInstance>();
		
		// SQL code lines
		String sql =
				"SELECT state, license, datetime, photoHash, photoPath, photoImage FROM parkingInstances\n"
						+ "WHERE state = ? AND license = ?\n" + "AND (\n"
						+ "  (TIME(datetime) > TIME('20:00:00') AND DATE(datetime) >= ? and DATE(datetime) <= ?)\n"
						+ "  OR \n"
						+ "  (TIME(datetime) < TIME('06:00:00') AND DATE(datetime) >= ? and DATE(datetime) <= ?)"
						+ ")";
		
		// setting up each data value to be queried and executing query
		try {
			PreparedStatement prepStatement = conn.prepareStatement(sql);
			prepStatement.setString(1, car.getState());
			prepStatement.setString(2, car.getLicense());
			prepStatement.setString(3, startDate.format(dateFormatter));
			prepStatement.setString(4, endDate.format(dateFormatter));
			prepStatement.setString(5, startDate.plusDays(1).format(dateFormatter));
			prepStatement.setString(6, endDate.plusDays(1).format(dateFormatter));
			ResultSet results = prepStatement.executeQuery();
			System.out.println("Selecting data");
			
			// after getting the values storing it into Parkings ArrayList
			while (results.next()) {
				try {
					Car myCar = new Car(results.getString("state"), results.getString("license"));
					Photo photo = new Photo(results.getBytes("photoImage"),
							LocalDateTime.parse(results.getString("datetime"), dateTimeFormatter),
							results.getString("photoHash"), results.getString("photoPath"));
					ParkingInstance parking = new ParkingInstance(myCar, photo);
					System.out.println(parking);
					parkings.add(parking);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return parkings;
	}

	/**
	 * Get parking instances filtered by user input dates and return an ArrayList of the aggregated
	 * parking instances for each car.
	 * 
	 * @param startDate (LocalDate)
	 * @param endDate (LocalDate)
	 */
	public ArrayList<ParkingAggregate> getParkingAggregates(LocalDate startDate,
			LocalDate endDate) {
	  
	    // SQL code
		String sql = "SELECT state, license, count(*) as count from\n" // break
				+ "(\n" // break
				+ "  SELECT * from\n" // break
				+ "  (\n" // break
				+ "	   SELECT state, license, (DATE(datetime)) as date FROM parkingInstances\n" // break
				+ "	   WHERE time(datetime) > time('20:00:00')\n" // break
				+ "	   GROUP BY state, license, date\n" // break
				+ "	   UNION ALL\n" // break
				+ "	   SELECT state, license, DATE(JULIANDAY(DATE(datetime)) -1) as date FROM parkingInstances\n"
				+ "	   WHERE time(datetime) < time('06:00:00')\n" // break
				+ "	   GROUP BY state, license, date\n" // break
				+ "	 )\n" // break
				+ "  WHERE date >= ? and date <= ?\n" // break
				+ "  GROUP BY state, license, date\n" // break
				+ ")\n" // break
				+ "GROUP BY state, license\n" + "ORDER BY count DESC, state ASC, license ASC";
		
		// ArrayList to store aggregate results of parking
		ArrayList<ParkingAggregate> parkingResults = new ArrayList<ParkingAggregate>();
		
		try {
		    // initialize variables for aggregate with start and end dates
			PreparedStatement prepStatement = conn.prepareStatement(sql);
			prepStatement.setString(1, startDate.format(dateFormatter));
			prepStatement.setString(2, endDate.format(dateFormatter));
			ResultSet results = prepStatement.executeQuery();
			
			// for every instance, store each instance separately but in the same aggregate
			while (results.next()) {
				String license = results.getString("license");
				String state = results.getString("state");
				Car car = new Car(state, license);
				int overnightCount = results.getInt("count");

				ParkingAggregate aggregate = new ParkingAggregate(car, overnightCount);
				ArrayList<ParkingInstance> parkings =
						getParkingInstancesbyDate(car, startDate, endDate);
				aggregate.setParkingInstance(parkings);
				parkingResults.add(aggregate);
			}
			return parkingResults;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parkingResults;
	}

	public static void main(String[] args) {
		try {
			Database database = new Database();
			Path filePath = Paths.get("src/photos/photo.jpg");
			Photo photo = PhotoFactory.createPhoto(filePath.toFile());
			ParkingInstance parking1 =
					new ParkingInstance(new Car("PA", "7XYA124"), new Photo(photo.getImage(),
							LocalDateTime.of(2018, 8, 13, 20, 56, 12), "A", "/path/to/photo"));

			ParkingInstance parking2 =
					new ParkingInstance(new Car("PA", "7XYA125"), new Photo(photo.getImage(),
							LocalDateTime.of(2018, 8, 13, 21, 56, 12), "B", "/path/to/photo"));

			ParkingInstance parking3 =
					new ParkingInstance(new Car("PA", "7XYA125"), new Photo(photo.getImage(),
							LocalDateTime.of(2018, 9, 13, 21, 57, 12), "C", "/path/to/photo"));

			ParkingInstance parking4 =
					new ParkingInstance(new Car("PA", "7XYA125"), new Photo(photo.getImage(),
							LocalDateTime.of(2018, 9, 14, 03, 56, 12), "D", "/path/to/photo"));

			database.insertParkingInstance(parking1);
			database.insertParkingInstance(parking2);
			database.insertParkingInstance(parking3);
			database.insertParkingInstance(parking4);

			database.getParkingInstancesbyDate(new Car("PA", "7XYA125"), LocalDate.of(2018, 2, 11),
					LocalDate.of(2019, 6, 11));
			database.getParkingAggregates(LocalDate.of(2019, 5, 01), LocalDate.of(2019, 5, 31));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
