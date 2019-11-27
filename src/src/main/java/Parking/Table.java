package Parking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javafx.application.Application;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label label = new Label("Parking Aggregates");
        label.setFont(new Font("Arial", 20));

        constructTable();
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private void constructTable() {
        TableColumn<ParkingAggregate, String> state =
                new TableColumn<ParkingAggregate, String>("State");
        state.setMinWidth(100);
        state.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, String>("state"));

        TableColumn<ParkingAggregate, String> license =
                new TableColumn<ParkingAggregate, String>("License");
        license.setMinWidth(100);
        license.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, String>("license"));

        TableColumn<ParkingAggregate, Integer> count =
                new TableColumn<ParkingAggregate, Integer>("Count");
        count.setMinWidth(200);
        count.setCellValueFactory(
                new PropertyValueFactory<ParkingAggregate, Integer>(
                        "overnightCount"));

        table.setItems(getData());
        table.getColumns().addAll(state, license, count);

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

        List<ParkingInstance> parkingInstances =
                parkingAggregate.getParkingInstances();
        System.out.println(parkingInstances.get(0));

        TableView<ParkingInstance> subTable = new TableView<ParkingInstance>();
        TableColumn<ParkingInstance, LocalDateTime> dateCol =
                new TableColumn<ParkingInstance, LocalDateTime>("Date Time");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<ParkingInstance, LocalDateTime>(
                        "date"));
        dateCol.setCellFactory(column -> {
            TableCell<ParkingInstance, LocalDateTime> cell =
                    new TableCell<ParkingInstance, LocalDateTime>() {
                        final DateTimeFormatter formatter =
                          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

        subTable.setItems(FXCollections.observableArrayList(parkingInstances));
        subTable.getColumns().addAll(dateCol);
        subTable.setPrefHeight(30 + (parkingInstances.size() * 30));
        subTable.setStyle("-fx-border-color: #42bff4;");
        return subTable;
    }


    private ObservableList<ParkingAggregate> getData() {
        return FXCollections.observableArrayList(
                parkingController.pullViolationReport(LocalDate.of(2010, 2, 11),
                        LocalDate.of(2019, 6, 11)));
    }
}
