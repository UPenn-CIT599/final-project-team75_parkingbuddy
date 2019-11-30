package Parking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

public class GUI extends Application {

	// launching the application
	public void start(Stage stage) {

		try {
			// make the window into a full screen
			Screen screen = Screen.getPrimary();
			stage.setFullScreen(true);

			// set title and text for the stage
			stage.setTitle("Parking Buddy");
			TextArea textArea = new TextArea();
			textArea.setMinHeight(100);

			// create a welcome label
			Label welLabel = new Label("Welcome to Parking Buddy!");
			welLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
			welLabel.setTextFill(Color.web("#ffff"));

			// create a direction label #1
			Label label1 = new Label("Please choose from the options below.");
			label1.setFont(Font.font("Verdana", 25));
			label1.setTextFill(Color.web("#ffff"));

			// create a direction label #2
			Label label2 = new Label("Upload images");
			label2.setFont(Font.font("Verdana", 20));
			label2.setTextFill(Color.web("#ffff"));

			// create the button to upload folder using directory chooser
			Button button1 = new Button("Upload folder");
			final DirectoryChooser directoryChooser = new DirectoryChooser();
			// setting up directory chooser
			directoryChooser.setTitle("Select the folder with images.");
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			// create event handler in case of button pressed
			button1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					File dir = directoryChooser.showDialog(stage);
					if (dir != null) {
						textArea.setText(dir.getAbsolutePath());
					} else {
						textArea.setText(null);
					}
				}
			});

			// create the button to upload files using file chooser
			Button button2 = new Button("Upload file(s)");
			final FileChooser fileChooser = new FileChooser();
			// the user is defaulted to uploading jpeg files when using file chooser
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"),
					new FileChooser.ExtensionFilter("All Files", "*.*"));
			// create event handler in case of button pressed
			button2.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					textArea.clear();
					List<File> files = fileChooser.showOpenMultipleDialog(stage);
				}
			});

			// create a direction label #3
			Label label3 = new Label("Generate report");
			label3.setFont(Font.font("Verdana", 20));
			label3.setTextFill(Color.web("#ffff"));

			// set of buttons for images
			HBox imageButtons = new HBox(20, button1, button2);
			imageButtons.setAlignment(Pos.CENTER);

			// label to show the date
			Label labelStart = new Label("Choose the start date.");
			Label labelEnd = new Label("Choose the end date.");
			labelStart.setFont(Font.font("Verdana", 15));
			labelStart.setTextFill(Color.web("#ffff"));
			labelEnd.setFont(Font.font("Verdana", 15));
			labelEnd.setTextFill(Color.web("#ffff"));
			// empty labels for spacing
			Label empty1 = new Label("");
			Label empty2 = new Label("");
			Label empty3 = new Label("");

			// create a date picker
			DatePicker dateStart = new DatePicker();
			DatePicker dateEnd = new DatePicker();

			// start date picker event handler
			EventHandler<ActionEvent> eventStart = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					LocalDate startDate = dateStart.getValue();
					labelStart.setText("Start Date: " + startDate);
				}
			};
			dateStart.setShowWeekNumbers(true);
			dateStart.setOnAction(eventStart);

			// end date picker event handler
			EventHandler<ActionEvent> eventEnd = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					LocalDate endDate = dateEnd.getValue();
					labelEnd.setText("End Date: " + endDate);
				}
			};
			dateEnd.setShowWeekNumbers(true);
			dateEnd.setOnAction(eventEnd);

			// HBoxes for date titles and date pickers
			HBox dates = new HBox(50, labelStart, labelEnd);
			HBox datePickers = new HBox(40, dateStart, dateEnd);
			dates.setAlignment(Pos.CENTER);
			datePickers.setAlignment(Pos.CENTER);

			Label warning = new Label("");
			warning.setFont(Font.font("Verdana", 15));
			warning.setTextFill(Color.web("#ff0000"));

			// button to process parking incidents
			Button button3 = new Button("Process parking incidents");

			// event handler for reports button
			button3.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					textArea.clear();
					LocalDate startDate = dateStart.getValue();
					LocalDate endDate = dateEnd.getValue();
					// only process report if the dates are valid, display warning if not
					if (startDate.isBefore(endDate) && endDate.isBefore(LocalDate.now())) {
						warning.setText("");
						List<File> files = fileChooser.showOpenMultipleDialog(stage);
					} else {
						warning.setText("The dates are invalid.");
					}
				}
			});

			// create a VBox composite of all buttons and labels
			VBox vbox = new VBox(30, empty1, empty2, welLabel, label1, label2, imageButtons, label3, dates, datePickers,
					button3, warning);
			// set Alignment
			vbox.setAlignment(Pos.CENTER);

			// set background image for the window
			FileInputStream input = new FileInputStream("src/main/java/Graphics/darkParking.jpg");
			Image image = new Image(input);
			BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					new BackgroundSize(1.0, 1.0, true, true, false, false));
			Background background = new Background(backgroundimage);
			vbox.setBackground(background);
			
			// scroll bar for the window
		    ScrollPane s1 = new ScrollPane(vbox);
		    s1.setFitToHeight(true);
		    s1.setFitToWidth(true);
		    s1.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	        
		    // set up the scene and display
		    Scene scene = new Scene(s1, 400, 400);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String args[]) {
		// launch the application
		launch(args);
	}
}