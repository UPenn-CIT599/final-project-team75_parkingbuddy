package Parking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.awt.image.BufferedImage;

public class ParkingInstancesTableFactory {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Scene createParkingInstancesTableScene(ArrayList<ParkingInstance> parkings) {
        Scene scene = new Scene(new Group());

        final Label label = new Label("Parking Instances Uploaded");
        label.setFont(new Font("Arial", 20));

        TableView<ParkingInstance> table = createParkingInstancesTable(parkings, true);
        table.setMinWidth(1000);
        table.setMinHeight(700);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return scene;
    }

    public static TableView<ParkingInstance> createParkingInstancesTable(
            ArrayList<ParkingInstance> parkings, boolean includeCarCols) {
        TableView<ParkingInstance> table = new TableView<ParkingInstance>();

        TableColumn<ParkingInstance, String> stateCol = null;
        TableColumn<ParkingInstance, String> licenseCol = null;

        if (includeCarCols) {
            // State
            stateCol = new TableColumn<ParkingInstance, String>("State");
            stateCol.setMinWidth(150);
            stateCol.setCellValueFactory(
                    new PropertyValueFactory<ParkingInstance, String>("state"));

            // License
            licenseCol = new TableColumn<ParkingInstance, String>("License");
            licenseCol.setMinWidth(200);
            licenseCol.setCellValueFactory(
                    new PropertyValueFactory<ParkingInstance, String>("license"));
        }

        // Date Time
        TableColumn<ParkingInstance, LocalDateTime> dateCol =
                new TableColumn<ParkingInstance, LocalDateTime>("Date Time");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<ParkingInstance, LocalDateTime>("dateTime"));
        dateCol.setCellFactory(column -> {
            TableCell<ParkingInstance, LocalDateTime> cell =
                    new TableCell<ParkingInstance, LocalDateTime>() {
                        @Override
                        protected void updateItem(LocalDateTime date, boolean empty) {
                            super.updateItem(date, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                this.setText(date.format(formatter));

                            }
                        }
                    };

            return cell;
        });

        // Thumbnail
        TableColumn<ParkingInstance, BufferedImage> photoCol =
                new TableColumn<ParkingInstance, BufferedImage>("Photo");
        photoCol.setMinWidth(400);
        photoCol.setCellValueFactory(
                new PropertyValueFactory<ParkingInstance, BufferedImage>("thumbnail"));
        photoCol.setCellFactory(column -> {
            TableCell<ParkingInstance, BufferedImage> cell =
                    new TableCell<ParkingInstance, BufferedImage>() {
                        @Override
                        protected void updateItem(BufferedImage image, boolean empty) {
                            super.updateItem(image, empty);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            ImageView imageView = new ImageView();
                            if (empty || image == null) {
                                imageView.setImage(null);
                                setText(null);
                                setGraphic(null);
                                return;
                            }

                            imageView.setImage(SwingFXUtils.toFXImage(image, null));
                            setGraphic(imageView);
                        }
                    };

            return cell;
        });


        table.setItems(FXCollections.observableArrayList(parkings));
        if (includeCarCols) {
            table.getColumns().addAll(Arrays.asList(dateCol, stateCol, licenseCol, photoCol));
        } else {
            table.getColumns().addAll(Arrays.asList(dateCol, photoCol));
        }
        table.setStyle("-fx-border-color: #42bff4;");
        return table;
    }
}
