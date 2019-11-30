package Parking;

import javafx.application.Application;
import javafx.scene.Scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.*;
import javafx.stage.DirectoryChooser;

import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

public class GUI extends Application {

// launch the application 
    public void start(Stage stage) {

	try {

	    Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();

	    stage.setFullScreen(true);

	    // set title for the stage
	    stage.setTitle("Parking Buddy");

	    TextArea textArea = new TextArea();
	    textArea.setMinHeight(70);

	    // create a welcome label
	    Label label1 = new Label("Welcome to Parking Buddy!");
	    label1.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
	    label1.setTextFill(Color.web("#ffff"));

	    // create a direction label
	    Label label2 = new Label("Please choose from the options below.");
	    label2.setFont(Font.font("Verdana", 25));
	    label2.setTextFill(Color.web("#ffff"));

	    // create a direction label
	    Label label3 = new Label("Upload images");
	    label3.setFont(Font.font("Verdana", 20));
	    label3.setTextFill(Color.web("#ffff"));

	    // create a Button
	    Button button1 = new Button("Upload folder");

	    // create an Event Handler
	    final DirectoryChooser directoryChooser = new DirectoryChooser();
	    final FileChooser fileChooser = new FileChooser();

	    fileChooser.getExtensionFilters().addAll(//
		    new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"),
		    new FileChooser.ExtensionFilter("All Files", "*.*"));

	    // Set title for DirectoryChooser
	    directoryChooser.setTitle("Select the folder with images.");
	    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

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

	    Button button2 = new Button("Upload file(s)");

	    button2.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
		    textArea.clear();
		    List<File> files = fileChooser.showOpenMultipleDialog(stage);
		}
	    });
	    
	    Button button3 = new Button("Process parking incidents");

	    button2.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
		    textArea.clear();
		    List<File> files = fileChooser.showOpenMultipleDialog(stage);
		}
	    });

	    // create a direction label
	    Label empty1= new Label("");
	    Label empty2= new Label("");
	    Label empty3= new Label("");
	    Label files = new Label("Upload images");
	    Label reports = new Label("Generate reports");
	    files.setFont(Font.font("Verdana", 20));
	    files.setTextFill(Color.web("#ffff"));
	    reports.setFont(Font.font("Verdana", 20));
	    reports.setTextFill(Color.web("#ffff"));

	    // create a VBox
	    VBox vbox = new VBox(30, empty1, empty2, label1, label2, files, button1, button2, reports, button3, empty3);

	    // set Alignment
	    vbox.setAlignment(Pos.CENTER);

	    

	    // label to show the date
	    Label labelStart = new Label("Choose the start date.");
	    Label labelEnd = new Label("Choose the end date.");
	    labelStart.setFont(Font.font("Verdana", 15));
	    labelStart.setTextFill(Color.web("#ffff"));
	    labelEnd.setFont(Font.font("Verdana", 15));
	    labelEnd.setTextFill(Color.web("#ffff"));

	    // create a date picker
	    DatePicker dateStart = new DatePicker();
	    DatePicker dateEnd = new DatePicker();

	    // action event
	    EventHandler<ActionEvent> eventStart = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
		    // get the date picker value
		    LocalDate i = dateStart.getValue();

		    // get the selected date
		    labelStart.setText("Start Date: " + i);
		}
	    };
	    
	    // show week numbers
	    dateStart.setShowWeekNumbers(true);

	    // when datePicker is pressed
	    dateStart.setOnAction(eventStart);
	    
	    // action event
	    EventHandler<ActionEvent> eventEnd = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
		    // get the date picker value
		    LocalDate i = dateEnd.getValue();

		    // get the selected date
		    labelEnd.setText("End Date: " + i);
		}
	    };
	    
	    // show week numbers
	    dateEnd.setShowWeekNumbers(true);

	    // when datePicker is pressed
	    dateEnd.setOnAction(eventEnd);
	    
	    // create a tile pane
	    HBox tile = new HBox(20, labelStart, dateStart, labelEnd, dateEnd);
	    tile.setAlignment(Pos.TOP_CENTER);

	    // create a input stream
	    FileInputStream input = new FileInputStream("src/main/java/Graphics/darkParking.jpg");

	    // create a image
	    Image image = new Image(input);

	    // create a background image
	    BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
		    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		    new BackgroundSize(1.0, 1.0, true, true, false, false));

	    // create Background
	    Background background = new Background(backgroundimage);

	    BorderPane root = new BorderPane();

	    Button unorganizedButton = new Button("Press me");

	    root.setTop(vbox);
	    root.setCenter(tile);
	    root.setBackground(background);

	    // create a scene
	    Scene scene = new Scene(root, 800, 500);

	    // set the scene
	    stage.setScene(scene);

	    stage.show();
	}

	catch (Exception e) {

	    System.out.println(e.getMessage());
	}
    }

// Main Method 
    public static void main(String args[]) {

	// launch the application
	launch(args);
    }
}