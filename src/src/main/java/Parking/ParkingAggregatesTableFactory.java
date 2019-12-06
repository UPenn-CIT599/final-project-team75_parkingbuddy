package Parking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Node;

/**
 * This class creates and displays Parking Aggregate Tables to be incorporated
 * into user interface.
 *
 */
public class ParkingAggregatesTableFactory {
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * This creates the scene of parking aggregates table with a given data of parking aggregates,
     * start date and end date.
     * @param parkings (ArrayList of ParkingAggregate)
     * @param start (LocalDate)
     * @param end (LocalDate)
     * @return table scene (Scene)
     */
    public static Scene createParkingAggregatesTableScene(ArrayList<ParkingAggregate> parkings,
            LocalDate start, LocalDate end) {
        Scene scene = new Scene(new Group());

        final Label label = new Label(
                "Parking Aggregates: " + start.format(formatter) + " to " + end.format(formatter));
        label.setFont(new Font("Arial", 20));
        
        // table view for efficient and ease of reading
        TableView<ParkingAggregate> table = createParkingAggregatesTable(parkings);
        table.setMinWidth(1000);
        table.setMinHeight(700);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return scene;
    }

    public static TableView<ParkingAggregate> createParkingAggregatesTable(
            ArrayList<ParkingAggregate> parkings) {
        TableView<ParkingAggregate> table = new TableView<ParkingAggregate>();
        table.setPlaceholder(new Label("No overnight parking instances found for selected dates."));

        // State
        TableColumn<ParkingAggregate, String> state =
                new TableColumn<ParkingAggregate, String>("State");
        state.setMinWidth(250);
        state.setCellValueFactory(new PropertyValueFactory<ParkingAggregate, String>("state"));

        // License
        TableColumn<ParkingAggregate, String> license =
                new TableColumn<ParkingAggregate, String>("License");
        license.setMinWidth(250);
        license.setCellValueFactory(new PropertyValueFactory<ParkingAggregate, String>("license"));

        // Overnight Count
        TableColumn<ParkingAggregate, Integer> count =
                new TableColumn<ParkingAggregate, Integer>("Count");
        count.setMinWidth(250);
        count.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, Integer>("overnightCount"));

        table.setItems(FXCollections.observableArrayList(parkings));
        table.getColumns().addAll(Arrays.asList(state, license, count));

        table.setRowFactory(tv -> new TableRow<ParkingAggregate>() {
            Node detailsPane;
            {
                this.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        detailsPane = createInlineParkingInstancesTable(getItem());
                        this.getChildren().add(detailsPane);
                    } else {
                        this.getChildren().remove(detailsPane);
                    }
                    this.requestLayout();
                });

            }

            @Override
            protected double computePrefHeight(double width) {
                if (isSelected()) {
                    return super.computePrefHeight(width) + detailsPane.prefHeight(60);
                } else {
                    return super.computePrefHeight(width);
                }
            }

            @Override
            protected void layoutChildren() {
                super.layoutChildren();
                if (isSelected()) {
                    double width = getWidth();
                    double paneHeight = detailsPane.prefHeight(width);
                    detailsPane.resizeRelocate(0, getHeight() - paneHeight, width, paneHeight);
                }
            }
        });
        return table;
    }

    private static TableView<ParkingInstance> createInlineParkingInstancesTable(
            ParkingAggregate parkingAggregate) {
        TableView<ParkingInstance> table = ParkingInstancesTableFactory.createParkingInstancesTable(
                parkingAggregate.getParkingInstances(), false /* exclude car columns */);
        table.setPrefHeight(50 + (parkingAggregate.getParkingInstances().size() * 130));
        return table;
    }
}
