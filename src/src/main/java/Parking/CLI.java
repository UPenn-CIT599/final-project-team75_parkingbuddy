package Parking;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Frame;
import java.awt.Window;

/**
 * Command Line Interface (CLI) that enables users to take the following actions: 
 * (1) Upload photos (can be in a folder) 
 * (2) Generate report of violations within a certain time frame (user must input the start and end date)
 */

import java.util.*;

public class CLI {

	public void createInterface() {

		// setting canvas to an appropriate size (3 x 2)
		PennDraw.setCanvasSize(1200, 800);

		// background picture
		PennDraw.picture(0.5, 0.5, "/Users/minschoi/Documents/GitHub/" + "final-project-team75_parkingbuddy/src/src/"
				+ "main/java/Graphics/parkinglines.jpg", 1200, 800);

		// initial options given
		PennDraw.setPenColor(PennDraw.WHITE);
		PennDraw.filledRectangle(0.5, 0.9, 0.2, 0.02);
		PennDraw.setPenColor(PennDraw.BLACK);
		PennDraw.text(0.5, 0.9, "What would you like to do? Choose below.");

		PennDraw.setPenColor(200, 200, 200);
		PennDraw.filledRectangle(0.4, 0.8, 0.08, 0.05);
		PennDraw.setPenColor(PennDraw.BLACK);
		PennDraw.text(0.4, 0.8, "Process Reports");

		PennDraw.setPenColor(200, 200, 200);
		PennDraw.filledRectangle(0.6, 0.8, 0.08, 0.05);
		PennDraw.setPenColor(PennDraw.BLACK);
		PennDraw.text(0.6, 0.8, "Upload Photos");

		String filePath = "";

		boolean isUpload = isUploadFolder();

		while (true) {

			boolean clickedLastTime = false; // created to prevent excessive clicking

			if (isUpload) {
				filePath =  chooseFolder();
			} else {

				PennDraw.enableAnimation(30);

				Date startDate = new Date();

				Calendar startCal = Calendar.getInstance(); // creates calendar
				startCal.setTime(startDate); // sets calendar time/date
				startCal.set(Calendar.HOUR_OF_DAY, 0);
				startCal.set(Calendar.MINUTE, 0);
				startCal.set(Calendar.SECOND, 0);
				startCal.set(Calendar.MILLISECOND, 0);
				startDate = startCal.getTime();
				int startMonth = startCal.get(Calendar.MONTH) + 1;
				int startYear = startCal.get(Calendar.YEAR);
				int startDay = startCal.get(Calendar.DAY_OF_MONTH);

				Date endDate = new Date();
				Calendar endCal = Calendar.getInstance(); // creates calendar
				endCal.setTime(endDate); // sets calendar time/date
				endCal.set(Calendar.HOUR_OF_DAY, 0);
				endCal.set(Calendar.MINUTE, 0);
				endCal.set(Calendar.SECOND, 0);
				endCal.set(Calendar.MILLISECOND, 0);
				endDate = endCal.getTime();
				int endMonth = endCal.get(Calendar.MONTH) + 1;
				int endYear = endCal.get(Calendar.YEAR);
				int endDay = endCal.get(Calendar.DAY_OF_MONTH);


				PennDraw.setPenColor(PennDraw.WHITE);
				PennDraw.filledRectangle(0.5, 0.7, 0.2, 0.02);
				PennDraw.setPenColor(PennDraw.BLACK);
				PennDraw.text(0.5, 0.7, "Choose the start date of violation");

				PennDraw.setPenColor(PennDraw.WHITE);

				PennDraw.filledRectangle(0.3, 0.6, 0.1, 0.02);
				PennDraw.setPenColor(PennDraw.BLACK);

				PennDraw.text(0.3, 0.6, "Start Date: " + " " + startYear + "/" + startMonth + "/" + startDay);

				PennDraw.setPenColor(PennDraw.WHITE);
				PennDraw.filledRectangle(0.5, 0.5, 0.2, 0.02);
				PennDraw.setPenColor(PennDraw.BLACK);
				PennDraw.text(0.5, 0.5, "Choose the end date of violation");

				PennDraw.setPenColor(220, 220, 220);
				PennDraw.filledRectangle(0.5, 0.3, 0.05, 0.02);
				PennDraw.setPenColor(PennDraw.BLACK);
				PennDraw.text(0.5, 0.3, "Done");

				PennDraw.setPenColor(PennDraw.WHITE);

				PennDraw.filledRectangle(0.3, 0.4, 0.1, 0.02);
				PennDraw.setPenColor(PennDraw.BLACK);

				PennDraw.text(0.3, 0.4, "End Date: " + " " + endYear + "/" + endMonth + "/" + endDay);


				e: while (true) {

					PennDraw.setPenColor(PennDraw.WHITE);
					PennDraw.filledRectangle(0.3, 0.6, 0.1, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.3, 0.6, "Start Date: " + " " + startYear + "/" + startMonth + "/" + startDay);

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.45, 0.6, 0.03, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.45, 0.6, "Year");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.5, 0.63, 0.015, 0.02);
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.5, 0.57, 0.015, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.5, 0.63, "+1");
					PennDraw.text(0.5, 0.57, "-1");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.55, 0.6, 0.03, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.55, 0.6, "Month");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.6, 0.63, 0.015, 0.02);
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.6, 0.57, 0.015, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.6, 0.63, "+1");
					PennDraw.text(0.6, 0.57, "-1");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.65, 0.6, 0.03, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.65, 0.6, "Day");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.7, 0.63, 0.015, 0.02);
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.7, 0.57, 0.015, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.7, 0.63, "+1");
					PennDraw.text(0.7, 0.57, "-1");

					PennDraw.setPenColor(PennDraw.WHITE);
					PennDraw.filledRectangle(0.3, 0.4, 0.1, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.3, 0.4, "End Date: " + " " + endYear + "/" + endMonth + "/" + endDay);

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.45, 0.4, 0.03, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.45, 0.4, "Year");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.5, 0.43, 0.015, 0.02);
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.5, 0.37, 0.015, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.5, 0.43, "+1");
					PennDraw.text(0.5, 0.37, "-1");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.55, 0.4, 0.03, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.55, 0.4, "Month");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.6, 0.43, 0.015, 0.02);
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.6, 0.37, 0.015, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.6, 0.43, "+1");
					PennDraw.text(0.6, 0.37, "-1");
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.65, 0.4, 0.03, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.65, 0.4, "Day");

					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.7, 0.43, 0.015, 0.02);
					PennDraw.setPenColor(220, 220, 220);
					PennDraw.filledRectangle(0.7, 0.37, 0.015, 0.02);
					PennDraw.setPenColor(PennDraw.BLACK);
					PennDraw.text(0.7, 0.43, "+1");
					PennDraw.text(0.7, 0.37, "-1");

					double x = PennDraw.mouseX();
					double y = PennDraw.mouseY();

					if (PennDraw.mousePressed() && x >= 0.485 && x <= 0.515 && y >= 0.61 && y <= 0.65
							&& !clickedLastTime) {
						startCal.add(Calendar.YEAR, 1);
						startYear = startCal.get(Calendar.YEAR);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.485 && x <= 0.515 && y >= 0.55 && y <= 0.59
							&& !clickedLastTime) {
						startCal.add(Calendar.YEAR, -1);
						startYear = startCal.get(Calendar.YEAR);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.585 && x <= 0.615 && y >= 0.61 && y <= 0.65
							&& !clickedLastTime) {
						startCal.add(Calendar.MONTH, 1);
						startMonth = startCal.get(Calendar.MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.585 && x <= 0.615 && y >= 0.55 && y <= 0.59
							&& !clickedLastTime) {
						startCal.add(Calendar.MONTH, -1);
						startMonth = startCal.get(Calendar.MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.685 && x <= 0.715 && y >= 0.61 && y <= 0.65
							&& !clickedLastTime) {
						startCal.add(Calendar.DAY_OF_MONTH, 1);
						startDay = startCal.get(Calendar.DAY_OF_MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.685 && x <= 0.715 && y >= 0.55 && y <= 0.59
							&& !clickedLastTime) {
						startCal.add(Calendar.DAY_OF_MONTH, -1);
						startDay = startCal.get(Calendar.DAY_OF_MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.485 && x <= 0.515 && y >= 0.41 && y <= 0.45
							&& !clickedLastTime) {
						endCal.add(Calendar.YEAR, 1);
						endYear = endCal.get(Calendar.YEAR);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.485 && x <= 0.515 && y >= 0.35 && y <= 0.39
							&& !clickedLastTime) {
						endCal.add(Calendar.YEAR, -1);
						endYear = endCal.get(Calendar.YEAR);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.585 && x <= 0.615 && y >= 0.41 && y <= 0.45
							&& !clickedLastTime) {
						endCal.add(Calendar.MONTH, 1);
						endMonth = endCal.get(Calendar.MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.585 && x <= 0.615 && y >= 0.35 && y <= 0.39
							&& !clickedLastTime) {
						endCal.add(Calendar.MONTH, -1);
						endMonth = endCal.get(Calendar.MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.685 && x <= 0.715 && y >= 0.41 && y <= 0.45
							&& !clickedLastTime) {
						endCal.add(Calendar.DAY_OF_MONTH, 1);
						endDay = endCal.get(Calendar.DAY_OF_MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.685 && x <= 0.715 && y >= 0.35 && y <= 0.39
							&& !clickedLastTime) {
						endCal.add(Calendar.DAY_OF_MONTH, -1);
						endDay = endCal.get(Calendar.DAY_OF_MONTH);
						clickedLastTime = true;
					} else if (PennDraw.mousePressed() && x >= 0.45 && x <= 0.65 && y >= 0.28 && y <= 0.32
							&& !clickedLastTime) {
						startDate = startCal.getTime();
						endDate = endCal.getTime();

						if (!(startDate.before(endDate))) {
							PennDraw.setPenColor(PennDraw.WHITE);
							PennDraw.filledRectangle(0.5, 0.2, 0.1, 0.02);
							PennDraw.setPenColor(PennDraw.RED);
							PennDraw.text(0.5, 0.2, "Invalid Dates");
							continue e;
						}
						break;
					}

					try {
						Thread.sleep(150);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					clickedLastTime = false;
					PennDraw.advance();
				}
				PennDraw.setPenColor(PennDraw.WHITE);
				PennDraw.filledRectangle(0.5, 0.2, 0.3, 0.02);
				PennDraw.setPenColor(PennDraw.BLACK);
				PennDraw.text(0.5, 0.2, "Dates: " + startYear + "/" + startMonth + "/" + startDay + " to " + endYear
						+ "/" + endMonth + "/" + endDay);
				PennDraw.advance();
			}
		}
	}

	public static String chooseFolder() {

		// using JFileChooser for browsing the folders
		JButton open = new JButton();
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File(""));
		fc.setDialogTitle("Select the folder with images.");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// open the browsing window
		while (true) {
			int value = fc.showOpenDialog(open);
			if (value == JFileChooser.APPROVE_OPTION) {
				return  fc.getSelectedFile().getAbsolutePath();
			} else {
				continue;
			}
		}
	}

	/**
	 * method to ask users if they want to upload photos or pull violation ticket.
	 * If return True, users want to upload photos. If false, users want to input
	 * voolation dates.
	 * 
	 * @return
	 */
	public static boolean isUploadFolder() {
		// choosing between two options: processing reports or uploading photo	
		while (true) {

			double x = PennDraw.mouseX();
			double y = PennDraw.mouseY();

			if (PennDraw.mousePressed() && x >= 0.32 && x <= 0.48 && y >= 0.75 && y <= 0.85) {
				return false;
			} else if (PennDraw.mousePressed() && x >= 0.52 && x <= 0.68 && y >= 0.75 && y <= 0.85) {
				return true;
			} 
		}

	}

	/**
	 * returns user inputted start date
	 * 
	 * @return
	 */
	public Date violationStartDate() {
		// TODO change dates to read user input
		Date startDate = new GregorianCalendar(2004, 6, 10, 13, 45).getTime();
		return startDate;
	}

	/**
	 * returns user inputted end date
	 * 
	 * @return
	 */
	public Date violationEndDate() {
		// TODO change dates to read user input
		Date endDate = new GregorianCalendar(2006, 6, 10, 13, 45).getTime();

		return endDate;
	}

	public static void main(String[] args) {
		CLI cli = new CLI();
		cli.createInterface();

		// ParkingInstanceProcessor parkingInstanceProcessor = new
		// ParkingInstanceProcessor();
		// ParkingTicketProcessor parkingTicketProcessor = new ParkingTicketProcessor();
		// ParkingInstanceDatabase parkingInstanceDatabase = new
		// ParkingInstanceDatabase();

	}

}
