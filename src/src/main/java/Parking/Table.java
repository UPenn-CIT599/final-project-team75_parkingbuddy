package Parking;

import java.time.LocalDate;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Table extends Application {
    private ParkingController parkingController = new ParkingController();
    private TableView<ParkingAggregate> table =
            new TableView<ParkingAggregate>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Parking Aggregates");
        stage.setWidth(1024);
        stage.setHeight(768);

        stage.setScene(tableScene());
        stage.show();
    }

    public Scene tableScene() {
        Scene scene = new Scene(new Group());

        final Label label = new Label("Parking Aggregates");
        label.setFont(new Font("Arial", 20));

        constructTable();
        table.setMinWidth(1000);
        table.setMinHeight(700);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return scene;
    }

    private void constructTable() {
        TableColumn<ParkingAggregate, String> state =
                new TableColumn<ParkingAggregate, String>("State");

        state.setMinWidth(250);
        state.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, String>("state"));

        TableColumn<ParkingAggregate, String> license =
                new TableColumn<ParkingAggregate, String>("License");
        license.setMinWidth(250);
        license.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, String>("license"));

        TableColumn<ParkingAggregate, Integer> count =
                new TableColumn<ParkingAggregate, Integer>("Count");
        count.setMinWidth(250);
        count.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, Integer>(
                        "overnightCount"));

        table.setItems(getData());
        table.getColumns().addAll(Arrays.asList(state, license, count));

        table.setRowFactory(tv -> new TableRow<ParkingAggregate>() {
            Node detailsPane;
            {
                this.selectedProperty()
                        .addListener((obs, wasSelected, isNowSelected) -> {
                            if (isNowSelected) {
                                detailsPane = constructSubTable(getItem());
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
                    return super.computePrefHeight(width)
                            + detailsPane.prefHeight(60);
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
                    detailsPane.resizeRelocate(0, getHeight() - paneHeight,
                            width, paneHeight);
                }
            }
        });
    }

    private TableView<ParkingInstance> constructSubTable(
            ParkingAggregate parkingAggregate) {
        TableView<ParkingInstance> table =
                ParkingInstancesTableViewFactory.createParkingInstancesTable(
                        parkingAggregate.getParkingInstances(),
                        false /* exclude car columns */);
        table.setPrefHeight(
                50 + (parkingAggregate.getParkingInstances().size() * 130));
        return table;
    }


    private ObservableList<ParkingAggregate> getData() {
        return FXCollections
                .observableArrayList(parkingController.getParkingAggregates(
                        LocalDate.of(2010, 2, 11), LocalDate.of(2019, 6, 11)));
    }
}
