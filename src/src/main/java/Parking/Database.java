package Parking;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import java.util.Arrays;

/**
 *
 * @author sqlitetutorial.net took pointer code from this website
 */
public class Database {
	final static DateTimeFormatter formatter =
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	Connection conn;

	/**
	 * Initialize the database
	 */
	public Database() {
		conn = connect("jdbc:sqlite:sqlite/db/parkingBuddy.db");
		createNewDatabase();
		createParkingInstanceTable();
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
	public void createParkingInstanceTable() {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS ParkingInstances (\n"
				+ "    state TEXT NOT NULL,\n" // line break
				+ "    license TEXT NOT NULL,\n"
				+ "    datetime TEXT NOT NULL,\n"
				+ "    photoHash TEXT NOT NULL PRIMARY KEY, \n"
				+ "    photoPath TEXT NOT NULL, \n"
				+ "    photoImage BLOB NOT NULL, \n"
				+ "    UNIQUE(photoHash) \n" // line break
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
	 * Insert each new parkingInstance into the table
	 * 
	 * @param parkingInstance
	 */
	public void insertParkingInstance(ParkingInstance parkingInstance) {
		String sql = "INSERT OR IGNORE INTO ParkingInstances \n"
				+ "(state,license,datetime,photoHash,photoPath,photoImage) VALUES(?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, parkingInstance.getCar().getState());
			pstmt.setString(2, parkingInstance.getCar().getLicense());


			// datetime inserts in computer's local timezone
			pstmt.setString(3, parkingInstance.getDateTime().format(formatter));
			pstmt.setString(4, parkingInstance.getPhotoMd5Hash());
			pstmt.setString(5, parkingInstance.getPhoto().getPath());
			pstmt.setBytes(6, parkingInstance.getPhoto().toJpegBytes());
			pstmt.executeUpdate();
			System.out.println("inserted into DB");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get parking instances filtered by user input dates.
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public ArrayList<ParkingInstance> getParkingInstancesbyDate(Car car,
			LocalDate startDate, LocalDate endDate) {
		ArrayList<ParkingInstance> parkings = new ArrayList<ParkingInstance>();

		String sql =
				"SELECT state, license, datetime, photoHash, photoPath, photoImage FROM parkingInstances\n"
						+ "WHERE (TIME(datetime) > TIME('20:00:00') OR TIME(datetime) < TIME('06:00:00'))\n"
						+ "AND state = ? AND license = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car.getState());
			pstmt.setString(2, car.getLicense());
			ResultSet results = pstmt.executeQuery();
			System.out.println("Selecting data");
			while (results.next()) {
				try {
					Car myCar = new Car(results.getString("state"),
							results.getString("license"));
					Photo photo = new Photo(results.getBytes("photoImage"),
							LocalDateTime.parse(results.getString("datetime"),
									formatter),
							results.getString("photoHash"),
							results.getString("photoPath"));
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
	 * Get parking instances filtered by user input dates and return an
	 * ArrayList of the aggregated parking instances for each car.
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public ArrayList<ParkingAggregate> getAggregatedParkingInstances(
			LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT state, license, count(*) as count from\n" + "(\n"
				+ "  SELECT * from\n" // line break
				+ "  (\n"
				+ "	   SELECT state, license, (DATE(datetime)) as date FROM parkingInstances\n"
				+ "	   WHERE time(datetime) > time('20:00:00')\n"
				+ "	   GROUP BY state, license, date\n" // line break
				+ "	   UNION ALL\n"
				+ "	   SELECT state, license, DATE(JULIANDAY(DATE(datetime)) -1) as date FROM parkingInstances\n"
				+ "	   WHERE time(datetime) < time('06:00:00')\n"
				+ "	   GROUP BY state, license, date\n" // line break
				+ "	 )\n" // line break
				+ "  GROUP BY state, license, date\n" // line break
				+ ")\n" // line break
				+ "GROUP BY state, license\n";

		ArrayList<ParkingAggregate> parkingResults =
				new ArrayList<ParkingAggregate>();
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);

			// ArrayList<ParkingAggregator> parkingResults = new
			// ArrayList<ParkingAggregator>();
			while (results.next()) {
				String license = results.getString("license");
				String state = results.getString("state");
				Car car = new Car(state, license);
				int overnightCount = results.getInt("count");

				ParkingAggregate aggregate =
						new ParkingAggregate(car, overnightCount);
				ArrayList<ParkingInstance> parkings = getParkingInstancesbyDate(car, startDate, endDate);
				aggregate.setParkingInstance(parkings);
				parkingResults.add(aggregate);
				System.out.println(aggregate);
				// System.out.println(results.getString("state") + "\t"
				// + results.getString("license") + "\t"
				// + results.getInt("count"));
			}
			return parkingResults;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return parkingResults;
	}


	public static void main(String[] args) {
		Database database = new Database();

		try {
			Path filePath = Paths.get("src/photos/photo.jpg");
			Photo photo = PhotoFactory.createPhoto(filePath.toFile());
			ParkingInstance parking1 =
					new ParkingInstance(new Car("PA", "7XYA124"),
							new Photo(photo.getImage(),
									LocalDateTime.of(2018, 8, 13, 20, 56, 12),
									"A", "/path/to/photo"));

			ParkingInstance parking2 =
					new ParkingInstance(new Car("PA", "7XYA125"),
							new Photo(photo.getImage(),
									LocalDateTime.of(2018, 8, 13, 21, 56, 12),
									"B", "/path/to/photo"));

			ParkingInstance parking3 =
					new ParkingInstance(new Car("PA", "7XYA125"),
							new Photo(photo.getImage(),
									LocalDateTime.of(2018, 9, 13, 21, 57, 12),
									"C", "/path/to/photo"));

			ParkingInstance parking4 =
					new ParkingInstance(new Car("PA", "7XYA125"),
							new Photo(photo.getImage(),
									LocalDateTime.of(2018, 9, 14, 03, 56, 12),
									"D", "/path/to/photo"));

			database.insertParkingInstance(parking1);
			database.insertParkingInstance(parking2);
			database.insertParkingInstance(parking3);
			database.insertParkingInstance(parking4);

			database.getParkingInstancesbyDate(new Car("PA", "7XYA125"),
					LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11));
			database.getAggregatedParkingInstances(LocalDate.of(2010, 2, 11),
					LocalDate.of(2019, 6, 11));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
