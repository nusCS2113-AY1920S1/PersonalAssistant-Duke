package duke.ui;

import duke.entities.Order;
import duke.parser.TimeParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SmallOrderCard extends AnchorPane {
    @FXML
    private AnchorPane innerPane;
    @FXML
    private Label id;
    @FXML
    private Label index;
    @FXML
    private Label deadline;
    @FXML
    private Label name;
    @FXML
    private Label contact;
    @FXML
    private Label remarks;

    public SmallOrderCard(Order order, int indexNumber) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/SmallOrderCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        id.setText(Long.toString(order.getId()));
        index.setText("#" + Integer.toString(indexNumber));
        deadline.setText(TimeParser.convertDateToString(order.getDeliveryDate()));
        name.setText(order.getCustomerName());
        contact.setText(order.getCustomerContact());
        remarks.setText(order.getRemarks());
    }

}
