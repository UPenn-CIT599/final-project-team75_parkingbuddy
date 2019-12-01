package Parking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;


public class Table extends Application {
    private ParkingController parkingController = new ParkingController();
    private TableView<ParkingAggregate> table =
            new TableView<ParkingAggregate>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Table View Sample");
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
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
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
        table.setMinWidth(1000);
        table.setMinHeight(700);

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
                        "dateTime"));
        dateCol.setCellFactory(column -> {
            TableCell<ParkingInstance, LocalDateTime> cell =
                    new TableCell<ParkingInstance, LocalDateTime>() {
                        final DateTimeFormatter formatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss");

                        @Override
                        protected void updateItem(LocalDateTime date,
                                boolean empty) {
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
        TableColumn<ParkingInstance, BufferedImage> photoCol =
                new TableColumn<ParkingInstance, BufferedImage>("Photo");
        photoCol.setMinWidth(400);
        photoCol.setCellValueFactory(
                new PropertyValueFactory<ParkingInstance, BufferedImage>(
                        "thumbnail"));
        photoCol.setCellFactory(column -> {
            TableCell<ParkingInstance, BufferedImage> cell =
                    new TableCell<ParkingInstance, BufferedImage>() {
                        @Override
                        protected void updateItem(BufferedImage image,
                                boolean empty) {
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


        subTable.setItems(FXCollections.observableArrayList(parkingInstances));
        subTable.getColumns().addAll(Arrays.asList(dateCol, photoCol));
        subTable.setPrefHeight(50 + (parkingInstances.size() * 270));
        subTable.setStyle("-fx-border-color: #42bff4;");
        return subTable;
    }


    private ObservableList<ParkingAggregate> getData() {
        return FXCollections.observableArrayList(
                parkingController.pullViolationReport(LocalDate.of(2010, 2, 11),
                        LocalDate.of(2019, 6, 11)));
    }
}
