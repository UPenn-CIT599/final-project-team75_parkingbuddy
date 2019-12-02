package Parking;

import java.util.ArrayList;
import javafx.scene.control.TableView;

public class TableViewFactory {
    public TableView<ParkingAggregate> createParkingAggretatesTable(ArrayList<ParkingAggregate> aggreates) {
        return new TableView<ParkingAggregate>();
    }

    public TableView<ParkingInstance> createParkingInstancesTable(ArrayList<ParkingInstance> parkings) {
        return new TableView<ParkingInstance>();
    }
}